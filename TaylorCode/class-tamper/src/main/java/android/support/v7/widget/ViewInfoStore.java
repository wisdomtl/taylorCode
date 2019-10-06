
package androidx.appcompat.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import androidx.core.util.Pools.Pool;
import androidx.core.util.Pools.SimplePool;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.ItemAnimator.ItemHolderInfo;

class ViewInfoStore {
    private static final boolean DEBUG = false;
    @VisibleForTesting
    final ArrayMap<ViewHolder, InfoRecord> mLayoutHolderMap = new ArrayMap();
    @VisibleForTesting
    final LongSparseArray<ViewHolder> mOldChangedHolders = new LongSparseArray();

    ViewInfoStore() {
    }

    void clear() {
        this.mLayoutHolderMap.clear();
        this.mOldChangedHolders.clear();
    }

    void addToPreLayout(ViewHolder holder, ItemHolderInfo info) {
        ViewInfoStore.InfoRecord record = (ViewInfoStore.InfoRecord)this.mLayoutHolderMap.get(holder);
        if (record == null) {
            record = ViewInfoStore.InfoRecord.obtain();
            this.mLayoutHolderMap.put(holder, record);
        }

        record.preInfo = info;
        record.flags |= 4;
    }

    boolean isDisappearing(ViewHolder holder) {
        ViewInfoStore.InfoRecord record = (ViewInfoStore.InfoRecord)this.mLayoutHolderMap.get(holder);
        return record != null && (record.flags & 1) != 0;
    }

    @Nullable
    ItemHolderInfo popFromPreLayout(ViewHolder vh) {
        return this.popFromLayoutStep(vh, 4);
    }

    @Nullable
    ItemHolderInfo popFromPostLayout(ViewHolder vh) {
        return this.popFromLayoutStep(vh, 8);
    }

    private ItemHolderInfo popFromLayoutStep(ViewHolder vh, int flag) {
        int index = this.mLayoutHolderMap.indexOfKey(vh);
        if (index < 0) {
            return null;
        } else {
            ViewInfoStore.InfoRecord record = (ViewInfoStore.InfoRecord)this.mLayoutHolderMap.valueAt(index);
            if (record != null && (record.flags & flag) != 0) {
                record.flags &= ~flag;
                ItemHolderInfo info;
                if (flag == 4) {
                    info = record.preInfo;
                } else {
                    if (flag != 8) {
                        throw new IllegalArgumentException("Must provide flag PRE or POST");
                    }

                    info = record.postInfo;
                }

                if ((record.flags & 12) == 0) {
                    this.mLayoutHolderMap.removeAt(index);
                    ViewInfoStore.InfoRecord.recycle(record);
                }

                return info;
            } else {
                return null;
            }
        }
    }

    void addToOldChangeHolders(long key, ViewHolder holder) {
        this.mOldChangedHolders.put(key, holder);
    }

    void addToAppearedInPreLayoutHolders(ViewHolder holder, ItemHolderInfo info) {
        ViewInfoStore.InfoRecord record = (ViewInfoStore.InfoRecord)this.mLayoutHolderMap.get(holder);
        if (record == null) {
            record = ViewInfoStore.InfoRecord.obtain();
            this.mLayoutHolderMap.put(holder, record);
        }

        record.flags |= 2;
        record.preInfo = info;
    }

    boolean isInPreLayout(ViewHolder viewHolder) {
        ViewInfoStore.InfoRecord record = (ViewInfoStore.InfoRecord)this.mLayoutHolderMap.get(viewHolder);
        return record != null && (record.flags & 4) != 0;
    }

    ViewHolder getFromOldChangeHolders(long key) {
        return (ViewHolder)this.mOldChangedHolders.get(key);
    }

    void addToPostLayout(ViewHolder holder, ItemHolderInfo info) {
        ViewInfoStore.InfoRecord record = (ViewInfoStore.InfoRecord)this.mLayoutHolderMap.get(holder);
        if (record == null) {
            record = ViewInfoStore.InfoRecord.obtain();
            this.mLayoutHolderMap.put(holder, record);
        }

        record.postInfo = info;
        record.flags |= 8;
    }

    void addToDisappearedInLayout(ViewHolder holder) {
        ViewInfoStore.InfoRecord record = (ViewInfoStore.InfoRecord)this.mLayoutHolderMap.get(holder);
        if (record == null) {
            record = ViewInfoStore.InfoRecord.obtain();
            this.mLayoutHolderMap.put(holder, record);
        }

        record.flags |= 1;
    }

    void removeFromDisappearedInLayout(ViewHolder holder) {
        ViewInfoStore.InfoRecord record = (ViewInfoStore.InfoRecord)this.mLayoutHolderMap.get(holder);
        if (record != null) {
            record.flags &= -2;
        }
    }

    void process(ViewInfoStore.ProcessCallback callback) {
        for(int index = this.mLayoutHolderMap.size() - 1; index >= 0; --index) {
            ViewHolder viewHolder = (ViewHolder)this.mLayoutHolderMap.keyAt(index);
            ViewInfoStore.InfoRecord record = (ViewInfoStore.InfoRecord)this.mLayoutHolderMap.removeAt(index);
            if ((record.flags & 3) == 3) {
                callback.unused(viewHolder);
            } else if ((record.flags & 1) != 0) {
                if (record.preInfo == null) {
                    callback.unused(viewHolder);
                } else {
                    callback.processDisappeared(viewHolder, record.preInfo, record.postInfo);
                }
            } else if ((record.flags & 14) == 14) {
                callback.processAppeared(viewHolder, record.preInfo, record.postInfo);
            } else if ((record.flags & 12) == 12) {
                callback.processPersistent(viewHolder, record.preInfo, record.postInfo);
            } else if ((record.flags & 4) != 0) {
                callback.processDisappeared(viewHolder, record.preInfo, (ItemHolderInfo)null);
            } else if ((record.flags & 8) != 0) {
                callback.processAppeared(viewHolder, record.preInfo, record.postInfo);
            } else if ((record.flags & 2) != 0) {
                ;
            }

            ViewInfoStore.InfoRecord.recycle(record);
        }

    }

    void removeViewHolder(ViewHolder holder) {
        for(int i = this.mOldChangedHolders.size() - 1; i >= 0; --i) {
            if (holder == this.mOldChangedHolders.valueAt(i)) {
                this.mOldChangedHolders.removeAt(i);
                break;
            }
        }

        ViewInfoStore.InfoRecord info = (ViewInfoStore.InfoRecord)this.mLayoutHolderMap.remove(holder);
        if (info != null) {
            ViewInfoStore.InfoRecord.recycle(info);
        }

    }

    void onDetach() {
        ViewInfoStore.InfoRecord.drainCache();
    }

    public void onViewDetached(ViewHolder viewHolder) {
        this.removeFromDisappearedInLayout(viewHolder);
    }

    static class InfoRecord {
        static final int FLAG_DISAPPEARED = 1;
        static final int FLAG_APPEAR = 2;
        static final int FLAG_PRE = 4;
        static final int FLAG_POST = 8;
        static final int FLAG_APPEAR_AND_DISAPPEAR = 3;
        static final int FLAG_PRE_AND_POST = 12;
        static final int FLAG_APPEAR_PRE_AND_POST = 14;
        int flags;
        @Nullable
        ItemHolderInfo preInfo;
        @Nullable
        ItemHolderInfo postInfo;
        static Pool<ViewInfoStore.InfoRecord> sPool = new SimplePool(20);

        private InfoRecord() {
        }

        static ViewInfoStore.InfoRecord obtain() {
            ViewInfoStore.InfoRecord record = (ViewInfoStore.InfoRecord)sPool.acquire();
            return record == null ? new ViewInfoStore.InfoRecord() : record;
        }

        static void recycle(ViewInfoStore.InfoRecord record) {
            record.flags = 0;
            record.preInfo = null;
            record.postInfo = null;
            sPool.release(record);
        }

        static void drainCache() {
            while(sPool.acquire() != null) {
                ;
            }

        }
    }

    interface ProcessCallback {
        void processDisappeared(ViewHolder var1, @NonNull ItemHolderInfo var2, @Nullable ItemHolderInfo var3);

        void processAppeared(ViewHolder var1, @Nullable ItemHolderInfo var2, ItemHolderInfo var3);

        void processPersistent(ViewHolder var1, @NonNull ItemHolderInfo var2, @NonNull ItemHolderInfo var3);

        void unused(ViewHolder var1);
    }
}

