package androidx.appcompat.widget;

import androidx.core.util.Pools;
import androidx.core.util.Pools.Pool;
import androidx.core.util.Pools.SimplePool;
import android.support.v7.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
class AdapterHelper implements android.support.v7.widget.OpReorderer.Callback {
    static final int POSITION_TYPE_INVISIBLE = 0;
    static final int POSITION_TYPE_NEW_OR_LAID_OUT = 1;
    private static final boolean DEBUG = false;
    private static final String TAG = "AHT";
    private Pool<UpdateOp> mUpdateOpPool;
    final ArrayList<UpdateOp> mPendingUpdates;
    final ArrayList<AdapterHelper.UpdateOp> mPostponedList;
    final AdapterHelper.Callback mCallback;
    Runnable mOnItemProcessedCallback;
    final boolean mDisableRecycler;
    final OpReorderer mOpReorderer;
    private int mExistingUpdateTypes;

    AdapterHelper(AdapterHelper.Callback callback) {
        this(callback, false);
    }

    AdapterHelper(AdapterHelper.Callback callback, boolean disableRecycler) {
        this.mUpdateOpPool = new Pools.SimplePool(30);
        this.mPendingUpdates = new ArrayList();
        this.mPostponedList = new ArrayList();
        this.mExistingUpdateTypes = 0;
        this.mCallback = callback;
        this.mDisableRecycler = disableRecycler;
        this.mOpReorderer = new OpReorderer(this);
    }

    AdapterHelper addUpdateOp(AdapterHelper.UpdateOp... ops) {
        Collections.addAll(this.mPendingUpdates, ops);
        return this;
    }

    void reset() {
        this.recycleUpdateOpsAndClearList(this.mPendingUpdates);
        this.recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    void preProcess() {
        this.mOpReorderer.reorderOps(this.mPendingUpdates);
        int count = this.mPendingUpdates.size();

        for(int i = 0; i < count; ++i) {
            AdapterHelper.UpdateOp op = (AdapterHelper.UpdateOp)this.mPendingUpdates.get(i);
            switch(op.cmd) {
                case 1:
                    this.applyAdd(op);
                    break;
                case 2:
                    this.applyRemove(op);
                case 3:
                case 5:
                case 6:
                case 7:
                default:
                    break;
                case 4:
                    this.applyUpdate(op);
                    break;
                case 8:
                    this.applyMove(op);
            }

            if (this.mOnItemProcessedCallback != null) {
                this.mOnItemProcessedCallback.run();
            }
        }

        this.mPendingUpdates.clear();
    }

    void consumePostponedUpdates() {
        int count = this.mPostponedList.size();

        for(int i = 0; i < count; ++i) {
            this.mCallback.onDispatchSecondPass((AdapterHelper.UpdateOp)this.mPostponedList.get(i));
        }

        this.recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    private void applyMove(AdapterHelper.UpdateOp op) {
        this.postponeAndUpdateViewHolders(op);
    }

    private void applyRemove(AdapterHelper.UpdateOp op) {
        int tmpStart = op.positionStart;
        int tmpCount = 0;
        int tmpEnd = op.positionStart + op.itemCount;
        int type = -1;

        for(int position = op.positionStart; position < tmpEnd; ++position) {
            boolean typeChanged = false;
            ViewHolder vh = this.mCallback.findViewHolder(position);
            AdapterHelper.UpdateOp newOp;
            if (vh == null && !this.canFindInPreLayout(position)) {
                if (type == 1) {
                    newOp = this.obtainUpdateOp(2, tmpStart, tmpCount, (Object)null);
                    this.postponeAndUpdateViewHolders(newOp);
                    typeChanged = true;
                }

                type = 0;
            } else {
                if (type == 0) {
                    newOp = this.obtainUpdateOp(2, tmpStart, tmpCount, (Object)null);
                    this.dispatchAndUpdateViewHolders(newOp);
                    typeChanged = true;
                }

                type = 1;
            }

            if (typeChanged) {
                position -= tmpCount;
                tmpEnd -= tmpCount;
                tmpCount = 1;
            } else {
                ++tmpCount;
            }
        }

        if (tmpCount != op.itemCount) {
            this.recycleUpdateOp(op);
            op = this.obtainUpdateOp(2, tmpStart, tmpCount, (Object)null);
        }

        if (type == 0) {
            this.dispatchAndUpdateViewHolders(op);
        } else {
            this.postponeAndUpdateViewHolders(op);
        }

    }

    private void applyUpdate(AdapterHelper.UpdateOp op) {
        int tmpStart = op.positionStart;
        int tmpCount = 0;
        int tmpEnd = op.positionStart + op.itemCount;
        int type = -1;

        for(int position = op.positionStart; position < tmpEnd; ++position) {
            ViewHolder vh = this.mCallback.findViewHolder(position);
            AdapterHelper.UpdateOp newOp;
            if (vh == null && !this.canFindInPreLayout(position)) {
                if (type == 1) {
                    newOp = this.obtainUpdateOp(4, tmpStart, tmpCount, op.payload);
                    this.postponeAndUpdateViewHolders(newOp);
                    tmpCount = 0;
                    tmpStart = position;
                }

                type = 0;
            } else {
                if (type == 0) {
                    newOp = this.obtainUpdateOp(4, tmpStart, tmpCount, op.payload);
                    this.dispatchAndUpdateViewHolders(newOp);
                    tmpCount = 0;
                    tmpStart = position;
                }

                type = 1;
            }

            ++tmpCount;
        }

        if (tmpCount != op.itemCount) {
            Object payload = op.payload;
            this.recycleUpdateOp(op);
            op = this.obtainUpdateOp(4, tmpStart, tmpCount, payload);
        }

        if (type == 0) {
            this.dispatchAndUpdateViewHolders(op);
        } else {
            this.postponeAndUpdateViewHolders(op);
        }

    }

    private void dispatchAndUpdateViewHolders(AdapterHelper.UpdateOp op) {
        if (op.cmd != 1 && op.cmd != 8) {
            int tmpStart = this.updatePositionWithPostponed(op.positionStart, op.cmd);
            int tmpCnt = 1;
            int offsetPositionForPartial = op.positionStart;
            byte positionMultiplier;
            switch(op.cmd) {
                case 2:
                    positionMultiplier = 0;
                    break;
                case 4:
                    positionMultiplier = 1;
                    break;
                default:
                    throw new IllegalArgumentException("op should be remove or update." + op);
            }

            for(int p = 1; p < op.itemCount; ++p) {
                int pos = op.positionStart + positionMultiplier * p;
                int updatedPos = this.updatePositionWithPostponed(pos, op.cmd);
                boolean continuous = false;
                switch(op.cmd) {
                    case 2:
                        continuous = updatedPos == tmpStart;
                        break;
                    case 4:
                        continuous = updatedPos == tmpStart + 1;
                }

                if (continuous) {
                    ++tmpCnt;
                } else {
                    AdapterHelper.UpdateOp tmp = this.obtainUpdateOp(op.cmd, tmpStart, tmpCnt, op.payload);
                    this.dispatchFirstPassAndUpdateViewHolders(tmp, offsetPositionForPartial);
                    this.recycleUpdateOp(tmp);
                    if (op.cmd == 4) {
                        offsetPositionForPartial += tmpCnt;
                    }

                    tmpStart = updatedPos;
                    tmpCnt = 1;
                }
            }

            Object payload = op.payload;
            this.recycleUpdateOp(op);
            if (tmpCnt > 0) {
                AdapterHelper.UpdateOp tmp = this.obtainUpdateOp(op.cmd, tmpStart, tmpCnt, payload);
                this.dispatchFirstPassAndUpdateViewHolders(tmp, offsetPositionForPartial);
                this.recycleUpdateOp(tmp);
            }

        } else {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
    }

    void dispatchFirstPassAndUpdateViewHolders(AdapterHelper.UpdateOp op, int offsetStart) {
        this.mCallback.onDispatchFirstPass(op);
        switch(op.cmd) {
            case 2:
                this.mCallback.offsetPositionsForRemovingInvisible(offsetStart, op.itemCount);
                break;
            case 4:
                this.mCallback.markViewHoldersUpdated(offsetStart, op.itemCount, op.payload);
                break;
            default:
                throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        }

    }

    private int updatePositionWithPostponed(int pos, int cmd) {
        int count = this.mPostponedList.size();

        int i;
        AdapterHelper.UpdateOp postponed;
        for(i = count - 1; i >= 0; --i) {
            postponed = (AdapterHelper.UpdateOp)this.mPostponedList.get(i);
            if (postponed.cmd == 8) {
                int start;
                int end;
                if (postponed.positionStart < postponed.itemCount) {
                    start = postponed.positionStart;
                    end = postponed.itemCount;
                } else {
                    start = postponed.itemCount;
                    end = postponed.positionStart;
                }

                if (pos >= start && pos <= end) {
                    if (start == postponed.positionStart) {
                        if (cmd == 1) {
                            ++postponed.itemCount;
                        } else if (cmd == 2) {
                            --postponed.itemCount;
                        }

                        ++pos;
                    } else {
                        if (cmd == 1) {
                            ++postponed.positionStart;
                        } else if (cmd == 2) {
                            --postponed.positionStart;
                        }

                        --pos;
                    }
                } else if (pos < postponed.positionStart) {
                    if (cmd == 1) {
                        ++postponed.positionStart;
                        ++postponed.itemCount;
                    } else if (cmd == 2) {
                        --postponed.positionStart;
                        --postponed.itemCount;
                    }
                }
            } else if (postponed.positionStart <= pos) {
                if (postponed.cmd == 1) {
                    pos -= postponed.itemCount;
                } else if (postponed.cmd == 2) {
                    pos += postponed.itemCount;
                }
            } else if (cmd == 1) {
                ++postponed.positionStart;
            } else if (cmd == 2) {
                --postponed.positionStart;
            }
        }

        for(i = this.mPostponedList.size() - 1; i >= 0; --i) {
            postponed = (AdapterHelper.UpdateOp)this.mPostponedList.get(i);
            if (postponed.cmd == 8) {
                if (postponed.itemCount == postponed.positionStart || postponed.itemCount < 0) {
                    this.mPostponedList.remove(i);
                    this.recycleUpdateOp(postponed);
                }
            } else if (postponed.itemCount <= 0) {
                this.mPostponedList.remove(i);
                this.recycleUpdateOp(postponed);
            }
        }

        return pos;
    }

    private boolean canFindInPreLayout(int position) {
        int count = this.mPostponedList.size();

        for(int i = 0; i < count; ++i) {
            AdapterHelper.UpdateOp op = (AdapterHelper.UpdateOp)this.mPostponedList.get(i);
            if (op.cmd == 8) {
                if (this.findPositionOffset(op.itemCount, i + 1) == position) {
                    return true;
                }
            } else if (op.cmd == 1) {
                int end = op.positionStart + op.itemCount;

                for(int pos = op.positionStart; pos < end; ++pos) {
                    if (this.findPositionOffset(pos, i + 1) == position) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void applyAdd(AdapterHelper.UpdateOp op) {
        this.postponeAndUpdateViewHolders(op);
    }

    private void postponeAndUpdateViewHolders(AdapterHelper.UpdateOp op) {
        this.mPostponedList.add(op);
        switch(op.cmd) {
            case 1:
                this.mCallback.offsetPositionsForAdd(op.positionStart, op.itemCount);
                break;
            case 2:
                this.mCallback.offsetPositionsForRemovingLaidOutOrNewView(op.positionStart, op.itemCount);
                break;
            case 3:
            case 5:
            case 6:
            case 7:
            default:
                throw new IllegalArgumentException("Unknown update op type for " + op);
            case 4:
                this.mCallback.markViewHoldersUpdated(op.positionStart, op.itemCount, op.payload);
                break;
            case 8:
                this.mCallback.offsetPositionsForMove(op.positionStart, op.itemCount);
        }

    }

    boolean hasPendingUpdates() {
        return this.mPendingUpdates.size() > 0;
    }

    boolean hasAnyUpdateTypes(int updateTypes) {
        return (this.mExistingUpdateTypes & updateTypes) != 0;
    }

    int findPositionOffset(int position) {
        return this.findPositionOffset(position, 0);
    }

    int findPositionOffset(int position, int firstPostponedItem) {
        int count = this.mPostponedList.size();

        for(int i = firstPostponedItem; i < count; ++i) {
            AdapterHelper.UpdateOp op = (AdapterHelper.UpdateOp)this.mPostponedList.get(i);
            if (op.cmd == 8) {
                if (op.positionStart == position) {
                    position = op.itemCount;
                } else {
                    if (op.positionStart < position) {
                        --position;
                    }

                    if (op.itemCount <= position) {
                        ++position;
                    }
                }
            } else if (op.positionStart <= position) {
                if (op.cmd == 2) {
                    if (position < op.positionStart + op.itemCount) {
                        return -1;
                    }

                    position -= op.itemCount;
                } else if (op.cmd == 1) {
                    position += op.itemCount;
                }
            }
        }

        return position;
    }

    boolean onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        if (itemCount < 1) {
            return false;
        } else {
            this.mPendingUpdates.add(this.obtainUpdateOp(4, positionStart, itemCount, payload));
            this.mExistingUpdateTypes |= 4;
            return this.mPendingUpdates.size() == 1;
        }
    }

    boolean onItemRangeInserted(int positionStart, int itemCount) {
        if (itemCount < 1) {
            return false;
        } else {
            this.mPendingUpdates.add(this.obtainUpdateOp(1, positionStart, itemCount, (Object)null));
            this.mExistingUpdateTypes |= 1;
            return this.mPendingUpdates.size() == 1;
        }
    }

    boolean onItemRangeRemoved(int positionStart, int itemCount) {
        if (itemCount < 1) {
            return false;
        } else {
            this.mPendingUpdates.add(this.obtainUpdateOp(2, positionStart, itemCount, (Object)null));
            this.mExistingUpdateTypes |= 2;
            return this.mPendingUpdates.size() == 1;
        }
    }

    boolean onItemRangeMoved(int from, int to, int itemCount) {
        if (from == to) {
            return false;
        } else if (itemCount != 1) {
            throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
        } else {
            this.mPendingUpdates.add(this.obtainUpdateOp(8, from, to, (Object)null));
            this.mExistingUpdateTypes |= 8;
            return this.mPendingUpdates.size() == 1;
        }
    }

    void consumeUpdatesInOnePass() {
        this.consumePostponedUpdates();
        int count = this.mPendingUpdates.size();

        for(int i = 0; i < count; ++i) {
            AdapterHelper.UpdateOp op = (AdapterHelper.UpdateOp)this.mPendingUpdates.get(i);
            switch(op.cmd) {
                case 1:
                    this.mCallback.onDispatchSecondPass(op);
                    this.mCallback.offsetPositionsForAdd(op.positionStart, op.itemCount);
                    break;
                case 2:
                    this.mCallback.onDispatchSecondPass(op);
                    this.mCallback.offsetPositionsForRemovingInvisible(op.positionStart, op.itemCount);
                case 3:
                case 5:
                case 6:
                case 7:
                default:
                    break;
                case 4:
                    this.mCallback.onDispatchSecondPass(op);
                    this.mCallback.markViewHoldersUpdated(op.positionStart, op.itemCount, op.payload);
                    break;
                case 8:
                    this.mCallback.onDispatchSecondPass(op);
                    this.mCallback.offsetPositionsForMove(op.positionStart, op.itemCount);
            }

            if (this.mOnItemProcessedCallback != null) {
                this.mOnItemProcessedCallback.run();
            }
        }

        this.recycleUpdateOpsAndClearList(this.mPendingUpdates);
        this.mExistingUpdateTypes = 0;
    }

    public int applyPendingUpdatesToPosition(int position) {
        int size = this.mPendingUpdates.size();

        for(int i = 0; i < size; ++i) {
            AdapterHelper.UpdateOp op = (AdapterHelper.UpdateOp)this.mPendingUpdates.get(i);
            switch(op.cmd) {
                case 1:
                    if (op.positionStart <= position) {
                        position += op.itemCount;
                    }
                    break;
                case 2:
                    if (op.positionStart <= position) {
                        int end = op.positionStart + op.itemCount;
                        if (end > position) {
                            return -1;
                        }

                        position -= op.itemCount;
                    }
                    break;
                case 8:
                    if (op.positionStart == position) {
                        position = op.itemCount;
                    } else {
                        if (op.positionStart < position) {
                            --position;
                        }

                        if (op.itemCount <= position) {
                            ++position;
                        }
                    }
            }
        }

        return position;
    }

    boolean hasUpdates() {
        return !this.mPostponedList.isEmpty() && !this.mPendingUpdates.isEmpty();
    }

    public AdapterHelper.UpdateOp obtainUpdateOp(int cmd, int positionStart, int itemCount, Object payload) {
        AdapterHelper.UpdateOp op = (AdapterHelper.UpdateOp)this.mUpdateOpPool.acquire();
        if (op == null) {
            op = new AdapterHelper.UpdateOp(cmd, positionStart, itemCount, payload);
        } else {
            op.cmd = cmd;
            op.positionStart = positionStart;
            op.itemCount = itemCount;
            op.payload = payload;
        }

        return op;
    }

    public void recycleUpdateOp(AdapterHelper.UpdateOp op) {
        if (!this.mDisableRecycler) {
            op.payload = null;
            this.mUpdateOpPool.release(op);
        }

    }

    void recycleUpdateOpsAndClearList(List<AdapterHelper.UpdateOp> ops) {
        int count = ops.size();

        for(int i = 0; i < count; ++i) {
            this.recycleUpdateOp((AdapterHelper.UpdateOp)ops.get(i));
        }

        ops.clear();
    }

    interface Callback {
        ViewHolder findViewHolder(int var1);

        void offsetPositionsForRemovingInvisible(int var1, int var2);

        void offsetPositionsForRemovingLaidOutOrNewView(int var1, int var2);

        void markViewHoldersUpdated(int var1, int var2, Object var3);

        void onDispatchFirstPass(AdapterHelper.UpdateOp var1);

        void onDispatchSecondPass(AdapterHelper.UpdateOp var1);

        void offsetPositionsForAdd(int var1, int var2);

        void offsetPositionsForMove(int var1, int var2);
    }

    static class UpdateOp {
        static final int ADD = 1;
        static final int REMOVE = 2;
        static final int UPDATE = 4;
        static final int MOVE = 8;
        static final int POOL_SIZE = 30;
        int cmd;
        int positionStart;
        Object payload;
        int itemCount;

        UpdateOp(int cmd, int positionStart, int itemCount, Object payload) {
            this.cmd = cmd;
            this.positionStart = positionStart;
            this.itemCount = itemCount;
            this.payload = payload;
        }

        String cmdToString() {
            switch(this.cmd) {
                case 1:
                    return "add";
                case 2:
                    return "rm";
                case 3:
                case 5:
                case 6:
                case 7:
                default:
                    return "??";
                case 4:
                    return "up";
                case 8:
                    return "mv";
            }
        }

        public String toString() {
            return Integer.toHexString(System.identityHashCode(this)) + "[" + this.cmdToString() + ",s:" + this.positionStart + "c:" + this.itemCount + ",p:" + this.payload + "]";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o != null && this.getClass() == o.getClass()) {
                AdapterHelper.UpdateOp op = (AdapterHelper.UpdateOp)o;
                if (this.cmd != op.cmd) {
                    return false;
                } else if (this.cmd == 8 && Math.abs(this.itemCount - this.positionStart) == 1 && this.itemCount == op.positionStart && this.positionStart == op.itemCount) {
                    return true;
                } else if (this.itemCount != op.itemCount) {
                    return false;
                } else if (this.positionStart != op.positionStart) {
                    return false;
                } else {
                    if (this.payload != null) {
                        if (!this.payload.equals(op.payload)) {
                            return false;
                        }
                    } else if (op.payload != null) {
                        return false;
                    }

                    return true;
                }
            } else {
                return false;
            }
        }

        public int hashCode() {
            int result = this.cmd;
            result = 31 * result + this.positionStart;
            result = 31 * result + this.itemCount;
            return result;
        }
    }
}

