package androidx.appcompat.widget;

import androidx.annotation.Nullable;
import androidx.core.os.TraceCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class GapWorker implements Runnable {
    static final ThreadLocal<GapWorker> sGapWorker = new ThreadLocal();
    ArrayList<RecyclerView> mRecyclerViews = new ArrayList();
    long mPostTimeNs;
    long mFrameIntervalNs;
    private ArrayList<GapWorker.Task> mTasks = new ArrayList();
    static Comparator<Task> sTaskComparator = new Comparator<GapWorker.Task>() {
        public int compare(GapWorker.Task lhs, GapWorker.Task rhs) {
            if (lhs.view == null != (rhs.view == null)) {
                return lhs.view == null ? 1 : -1;
            } else if (lhs.immediate != rhs.immediate) {
                return lhs.immediate ? -1 : 1;
            } else {
                int deltaViewVelocity = rhs.viewVelocity - lhs.viewVelocity;
                if (deltaViewVelocity != 0) {
                    return deltaViewVelocity;
                } else {
                    int deltaDistanceToItem = lhs.distanceToItem - rhs.distanceToItem;
                    return deltaDistanceToItem != 0 ? deltaDistanceToItem : 0;
                }
            }
        }
    };

    GapWorker() {
    }

    public void add(RecyclerView recyclerView) {
        this.mRecyclerViews.add(recyclerView);
    }

    public void remove(RecyclerView recyclerView) {
        this.mRecyclerViews.remove(recyclerView);
    }

    void postFromTraversal(RecyclerView recyclerView, int prefetchDx, int prefetchDy) {
        if (recyclerView.isAttachedToWindow() && this.mPostTimeNs == 0L) {
            this.mPostTimeNs = recyclerView.getNanoTime();
            recyclerView.post(this);
        }

        recyclerView.mPrefetchRegistry.setPrefetchVector(prefetchDx, prefetchDy);
    }

    private void buildTaskList() {
        int viewCount = this.mRecyclerViews.size();
        int totalTaskCount = 0;

        int totalTaskIndex;
        for(totalTaskIndex = 0; totalTaskIndex < viewCount; ++totalTaskIndex) {
            RecyclerView view = (RecyclerView)this.mRecyclerViews.get(totalTaskIndex);
            if (view.getWindowVisibility() == 0) {
                view.mPrefetchRegistry.collectPrefetchPositionsFromView(view, false);
                totalTaskCount += view.mPrefetchRegistry.mCount;
            }
        }

        this.mTasks.ensureCapacity(totalTaskCount);
        totalTaskIndex = 0;

        for(int i = 0; i < viewCount; ++i) {
            RecyclerView view = (RecyclerView)this.mRecyclerViews.get(i);
            if (view.getWindowVisibility() == 0) {
                GapWorker.LayoutPrefetchRegistryImpl prefetchRegistry = view.mPrefetchRegistry;
                int viewVelocity = Math.abs(prefetchRegistry.mPrefetchDx) + Math.abs(prefetchRegistry.mPrefetchDy);

                for(int j = 0; j < prefetchRegistry.mCount * 2; j += 2) {
                    GapWorker.Task task;
                    if (totalTaskIndex >= this.mTasks.size()) {
                        task = new GapWorker.Task();
                        this.mTasks.add(task);
                    } else {
                        task = (GapWorker.Task)this.mTasks.get(totalTaskIndex);
                    }

                    int distanceToItem = prefetchRegistry.mPrefetchArray[j + 1];
                    task.immediate = distanceToItem <= viewVelocity;
                    task.viewVelocity = viewVelocity;
                    task.distanceToItem = distanceToItem;
                    task.view = view;
                    task.position = prefetchRegistry.mPrefetchArray[j];
                    ++totalTaskIndex;
                }
            }
        }

        Collections.sort(this.mTasks, sTaskComparator);
    }

    static boolean isPrefetchPositionAttached(RecyclerView view, int position) {
        int childCount = view.mChildHelper.getUnfilteredChildCount();

        for(int i = 0; i < childCount; ++i) {
            View attachedView = view.mChildHelper.getUnfilteredChildAt(i);
            RecyclerView.ViewHolder holder = RecyclerView.getChildViewHolderInt(attachedView);
            if (holder.mPosition == position && !holder.isInvalid()) {
                return true;
            }
        }

        return false;
    }

    private RecyclerView.ViewHolder prefetchPositionWithDeadline(RecyclerView view, int position, long deadlineNs) {
        if (isPrefetchPositionAttached(view, position)) {
            return null;
        } else {
            RecyclerView.Recycler recycler = view.mRecycler;

            RecyclerView.ViewHolder holder;
            try {
                view.onEnterLayoutOrScroll();
                holder = recycler.tryGetViewHolderForPositionByDeadline(position, false, deadlineNs);
                if (holder != null) {
                    if (holder.isBound() && !holder.isInvalid()) {
                        recycler.recycleView(holder.itemView);
                    } else {
                        recycler.addViewHolderToRecycledViewPool(holder, false);
                    }
                }
            } finally {
                view.onExitLayoutOrScroll(false);
            }

            return holder;
        }
    }

    private void prefetchInnerRecyclerViewWithDeadline(@Nullable RecyclerView innerView, long deadlineNs) {
        if (innerView != null) {
            if (innerView.mDataSetHasChangedAfterLayout && innerView.mChildHelper.getUnfilteredChildCount() != 0) {
                innerView.removeAndRecycleViews();
            }

            GapWorker.LayoutPrefetchRegistryImpl innerPrefetchRegistry = innerView.mPrefetchRegistry;
            innerPrefetchRegistry.collectPrefetchPositionsFromView(innerView, true);
            if (innerPrefetchRegistry.mCount != 0) {
                try {
                    TraceCompat.beginSection("RV Nested Prefetch");
                    innerView.mState.prepareForNestedPrefetch(innerView.mAdapter);

                    for(int i = 0; i < innerPrefetchRegistry.mCount * 2; i += 2) {
                        int innerPosition = innerPrefetchRegistry.mPrefetchArray[i];
                        this.prefetchPositionWithDeadline(innerView, innerPosition, deadlineNs);
                    }
                } finally {
                    TraceCompat.endSection();
                }
            }

        }
    }

    private void flushTaskWithDeadline(GapWorker.Task task, long deadlineNs) {
        long taskDeadlineNs = task.immediate ? 9223372036854775807L : deadlineNs;
        RecyclerView.ViewHolder holder = this.prefetchPositionWithDeadline(task.view, task.position, taskDeadlineNs);
        if (holder != null && holder.mNestedRecyclerView != null && holder.isBound() && !holder.isInvalid()) {
            this.prefetchInnerRecyclerViewWithDeadline((RecyclerView)holder.mNestedRecyclerView.get(), deadlineNs);
        }

    }

    private void flushTasksWithDeadline(long deadlineNs) {
        for(int i = 0; i < this.mTasks.size(); ++i) {
            GapWorker.Task task = (GapWorker.Task)this.mTasks.get(i);
            if (task.view == null) {
                break;
            }

            this.flushTaskWithDeadline(task, deadlineNs);
            task.clear();
        }

    }

    void prefetch(long deadlineNs) {
        this.buildTaskList();
        this.flushTasksWithDeadline(deadlineNs);
    }

    public void run() {
        try {
            TraceCompat.beginSection("RV Prefetch");
            if (!this.mRecyclerViews.isEmpty()) {
                int size = this.mRecyclerViews.size();
                long latestFrameVsyncMs = 0L;

                for(int i = 0; i < size; ++i) {
                    RecyclerView view = (RecyclerView)this.mRecyclerViews.get(i);
                    if (view.getWindowVisibility() == 0) {
                        latestFrameVsyncMs = Math.max(view.getDrawingTime(), latestFrameVsyncMs);
                    }
                }

                if (latestFrameVsyncMs == 0L) {
                    return;
                }

                long nextFrameNs = TimeUnit.MILLISECONDS.toNanos(latestFrameVsyncMs) + this.mFrameIntervalNs;
                this.prefetch(nextFrameNs);
                return;
            }
        } finally {
            this.mPostTimeNs = 0L;
            TraceCompat.endSection();
        }

    }

    static class LayoutPrefetchRegistryImpl implements RecyclerView.LayoutManager.LayoutPrefetchRegistry {
        int mPrefetchDx;
        int mPrefetchDy;
        int[] mPrefetchArray;
        int mCount;

        LayoutPrefetchRegistryImpl() {
        }

        void setPrefetchVector(int dx, int dy) {
            this.mPrefetchDx = dx;
            this.mPrefetchDy = dy;
        }

        void collectPrefetchPositionsFromView(RecyclerView view, boolean nested) {
            this.mCount = 0;
            if (this.mPrefetchArray != null) {
                Arrays.fill(this.mPrefetchArray, -1);
            }

            RecyclerView.LayoutManager layout = view.mLayout;
            if (view.mAdapter != null && layout != null && layout.isItemPrefetchEnabled()) {
                if (nested) {
                    if (!view.mAdapterHelper.hasPendingUpdates()) {
                        layout.collectInitialPrefetchPositions(view.mAdapter.getItemCount(), this);
                    }
                } else if (!view.hasPendingAdapterUpdates()) {
                    layout.collectAdjacentPrefetchPositions(this.mPrefetchDx, this.mPrefetchDy, view.mState, this);
                }

                if (this.mCount > layout.mPrefetchMaxCountObserved) {
                    layout.mPrefetchMaxCountObserved = this.mCount;
                    layout.mPrefetchMaxObservedInInitialPrefetch = nested;
                    view.mRecycler.updateViewCacheSize();
                }
            }

        }

        public void addPosition(int layoutPosition, int pixelDistance) {
            if (layoutPosition < 0) {
                throw new IllegalArgumentException("Layout positions must be non-negative");
            } else if (pixelDistance < 0) {
                throw new IllegalArgumentException("Pixel distance must be non-negative");
            } else {
                int storagePosition = this.mCount * 2;
                if (this.mPrefetchArray == null) {
                    this.mPrefetchArray = new int[4];
                    Arrays.fill(this.mPrefetchArray, -1);
                } else if (storagePosition >= this.mPrefetchArray.length) {
                    int[] oldArray = this.mPrefetchArray;
                    this.mPrefetchArray = new int[storagePosition * 2];
                    System.arraycopy(oldArray, 0, this.mPrefetchArray, 0, oldArray.length);
                }

                this.mPrefetchArray[storagePosition] = layoutPosition;
                this.mPrefetchArray[storagePosition + 1] = pixelDistance;
                ++this.mCount;
            }
        }

        boolean lastPrefetchIncludedPosition(int position) {
            if (this.mPrefetchArray != null) {
                int count = this.mCount * 2;

                for(int i = 0; i < count; i += 2) {
                    if (this.mPrefetchArray[i] == position) {
                        return true;
                    }
                }
            }

            return false;
        }

        void clearPrefetchPositions() {
            if (this.mPrefetchArray != null) {
                Arrays.fill(this.mPrefetchArray, -1);
            }

            this.mCount = 0;
        }
    }

    static class Task {
        public boolean immediate;
        public int viewVelocity;
        public int distanceToItem;
        public RecyclerView view;
        public int position;

        Task() {
        }

        public void clear() {
            this.immediate = false;
            this.viewVelocity = 0;
            this.distanceToItem = 0;
            this.view = null;
            this.position = 0;
        }


    }}
