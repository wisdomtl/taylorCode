package android.support.v7.widget;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ChildHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "ChildrenHelper";
    final ChildHelper.Callback mCallback;
    final ChildHelper.Bucket mBucket;
    final List<View> mHiddenViews;

    ChildHelper(ChildHelper.Callback callback) {
        this.mCallback = callback;
        this.mBucket = new ChildHelper.Bucket();
        this.mHiddenViews = new ArrayList();
    }

    private void hideViewInternal(View child) {
        this.mHiddenViews.add(child);
        this.mCallback.onEnteredHiddenState(child);
    }

    private boolean unhideViewInternal(View child) {
        if (this.mHiddenViews.remove(child)) {
            this.mCallback.onLeftHiddenState(child);
            return true;
        } else {
            return false;
        }
    }

    void addView(View child, boolean hidden) {
        this.addView(child, -1, hidden);
    }

    void addView(View child, int index, boolean hidden) {
        int offset;
        if (index < 0) {
            offset = this.mCallback.getChildCount();
        } else {
            offset = this.getOffset(index);
        }

        this.mBucket.insert(offset, hidden);
        if (hidden) {
            this.hideViewInternal(child);
        }

        this.mCallback.addView(child, offset);
    }

    private int getOffset(int index) {
        if (index < 0) {
            return -1;
        } else {
            int limit = this.mCallback.getChildCount();

            int diff;
            for(int offset = index; offset < limit; offset += diff) {
                int removedBefore = this.mBucket.countOnesBefore(offset);
                diff = index - (offset - removedBefore);
                if (diff == 0) {
                    while(this.mBucket.get(offset)) {
                        ++offset;
                    }

                    return offset;
                }
            }

            return -1;
        }
    }

    void removeView(View view) {
        int index = this.mCallback.indexOfChild(view);
        if (index >= 0) {
            if (this.mBucket.remove(index)) {
                this.unhideViewInternal(view);
            }

            this.mCallback.removeViewAt(index);
        }
    }

    void removeViewAt(int index) {
        int offset = this.getOffset(index);
        View view = this.mCallback.getChildAt(offset);
        if (view != null) {
            if (this.mBucket.remove(offset)) {
                this.unhideViewInternal(view);
            }

            this.mCallback.removeViewAt(offset);
        }
    }

    View getChildAt(int index) {
        int offset = this.getOffset(index);
        return this.mCallback.getChildAt(offset);
    }

    void removeAllViewsUnfiltered() {
        this.mBucket.reset();

        for(int i = this.mHiddenViews.size() - 1; i >= 0; --i) {
            this.mCallback.onLeftHiddenState((View)this.mHiddenViews.get(i));
            this.mHiddenViews.remove(i);
        }

        this.mCallback.removeAllViews();
    }

    View findHiddenNonRemovedView(int position) {
        int count = this.mHiddenViews.size();

        for(int i = 0; i < count; ++i) {
            View view = (View)this.mHiddenViews.get(i);
            RecyclerView.ViewHolder holder = this.mCallback.getChildViewHolder(view);
            if (holder.getLayoutPosition() == position && !holder.isInvalid() && !holder.isRemoved()) {
                return view;
            }
        }

        return null;
    }

    void attachViewToParent(View child, int index, ViewGroup.LayoutParams layoutParams, boolean hidden) {
        int offset;
        if (index < 0) {
            offset = this.mCallback.getChildCount();
        } else {
            offset = this.getOffset(index);
        }

        this.mBucket.insert(offset, hidden);
        if (hidden) {
            this.hideViewInternal(child);
        }

        this.mCallback.attachViewToParent(child, offset, layoutParams);
    }

    int getChildCount() {
        return this.mCallback.getChildCount() - this.mHiddenViews.size();
    }

    int getUnfilteredChildCount() {
        return this.mCallback.getChildCount();
    }

    View getUnfilteredChildAt(int index) {
        return this.mCallback.getChildAt(index);
    }

    void detachViewFromParent(int index) {
        int offset = this.getOffset(index);
        this.mBucket.remove(offset);
        this.mCallback.detachViewFromParent(offset);
    }

    int indexOfChild(View child) {
        int index = this.mCallback.indexOfChild(child);
        if (index == -1) {
            return -1;
        } else {
            return this.mBucket.get(index) ? -1 : index - this.mBucket.countOnesBefore(index);
        }
    }

    boolean isHidden(View view) {
        return this.mHiddenViews.contains(view);
    }

    void hide(View view) {
        int offset = this.mCallback.indexOfChild(view);
        if (offset < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        } else {
            this.mBucket.set(offset);
            this.hideViewInternal(view);
        }
    }

    void unhide(View view) {
        int offset = this.mCallback.indexOfChild(view);
        if (offset < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        } else if (!this.mBucket.get(offset)) {
            throw new RuntimeException("trying to unhide a view that was not hidden" + view);
        } else {
            this.mBucket.clear(offset);
            this.unhideViewInternal(view);
        }
    }

    public String toString() {
        return this.mBucket.toString() + ", hidden list:" + this.mHiddenViews.size();
    }

    boolean removeViewIfHidden(View view) {
        int index = this.mCallback.indexOfChild(view);
        if (index == -1) {
            if (this.unhideViewInternal(view)) {
                ;
            }

            return true;
        } else if (this.mBucket.get(index)) {
            this.mBucket.remove(index);
            if (!this.unhideViewInternal(view)) {
                ;
            }

            this.mCallback.removeViewAt(index);
            return true;
        } else {
            return false;
        }
    }

    interface Callback {
        int getChildCount();

        void addView(View var1, int var2);

        int indexOfChild(View var1);

        void removeViewAt(int var1);

        View getChildAt(int var1);

        void removeAllViews();

        RecyclerView.ViewHolder getChildViewHolder(View var1);

        void attachViewToParent(View var1, int var2, ViewGroup.LayoutParams var3);

        void detachViewFromParent(int var1);

        void onEnteredHiddenState(View var1);

        void onLeftHiddenState(View var1);
    }

    static class Bucket {
        static final int BITS_PER_WORD = 64;
        static final long LAST_BIT = -9223372036854775808L;
        long mData = 0L;
        ChildHelper.Bucket mNext;

        Bucket() {
        }

        void set(int index) {
            if (index >= 64) {
                this.ensureNext();
                this.mNext.set(index - 64);
            } else {
                this.mData |= 1L << index;
            }

        }

        private void ensureNext() {
            if (this.mNext == null) {
                this.mNext = new ChildHelper.Bucket();
            }

        }

        void clear(int index) {
            if (index >= 64) {
                if (this.mNext != null) {
                    this.mNext.clear(index - 64);
                }
            } else {
                this.mData &= ~(1L << index);
            }

        }

        boolean get(int index) {
            if (index >= 64) {
                this.ensureNext();
                return this.mNext.get(index - 64);
            } else {
                return (this.mData & 1L << index) != 0L;
            }
        }

        void reset() {
            this.mData = 0L;
            if (this.mNext != null) {
                this.mNext.reset();
            }

        }

        void insert(int index, boolean value) {
            if (index >= 64) {
                this.ensureNext();
                this.mNext.insert(index - 64, value);
            } else {
                boolean lastBit = (this.mData & -9223372036854775808L) != 0L;
                long mask = (1L << index) - 1L;
                long before = this.mData & mask;
                long after = (this.mData & ~mask) << 1;
                this.mData = before | after;
                if (value) {
                    this.set(index);
                } else {
                    this.clear(index);
                }

                if (lastBit || this.mNext != null) {
                    this.ensureNext();
                    this.mNext.insert(0, lastBit);
                }
            }

        }

        boolean remove(int index) {
            if (index >= 64) {
                this.ensureNext();
                return this.mNext.remove(index - 64);
            } else {
                long mask = 1L << index;
                boolean value = (this.mData & mask) != 0L;
                this.mData &= ~mask;
                --mask;
                long before = this.mData & mask;
                long after = Long.rotateRight(this.mData & ~mask, 1);
                this.mData = before | after;
                if (this.mNext != null) {
                    if (this.mNext.get(0)) {
                        this.set(63);
                    }

                    this.mNext.remove(0);
                }

                return value;
            }
        }

        int countOnesBefore(int index) {
            if (this.mNext == null) {
                return index >= 64 ? Long.bitCount(this.mData) : Long.bitCount(this.mData & (1L << index) - 1L);
            } else {
                return index < 64 ? Long.bitCount(this.mData & (1L << index) - 1L) : this.mNext.countOnesBefore(index - 64) + Long.bitCount(this.mData);
            }
        }

        public String toString() {
            return this.mNext == null ? Long.toBinaryString(this.mData) : this.mNext.toString() + "xx" + Long.toBinaryString(this.mData);
        }

    }}
