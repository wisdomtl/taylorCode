
package androidx.appcompat.widget;

import android.support.v7.widget.AdapterHelper.UpdateOp;
import java.util.List;

class OpReorderer {
    final OpReorderer.Callback mCallback;

    OpReorderer(OpReorderer.Callback callback) {
        this.mCallback = callback;
    }

    void reorderOps(List<UpdateOp> ops) {
        int badMove;
        while((badMove = this.getLastMoveOutOfOrder(ops)) != -1) {
            this.swapMoveOp(ops, badMove, badMove + 1);
        }

    }

    private void swapMoveOp(List<UpdateOp> list, int badMove, int next) {
        UpdateOp moveOp = (UpdateOp)list.get(badMove);
        UpdateOp nextOp = (UpdateOp)list.get(next);
        switch(nextOp.cmd) {
            case 1:
                this.swapMoveAdd(list, badMove, moveOp, next, nextOp);
                break;
            case 2:
                this.swapMoveRemove(list, badMove, moveOp, next, nextOp);
            case 3:
            default:
                break;
            case 4:
                this.swapMoveUpdate(list, badMove, moveOp, next, nextOp);
        }

    }

    void swapMoveRemove(List<UpdateOp> list, int movePos, UpdateOp moveOp, int removePos, UpdateOp removeOp) {
        UpdateOp extraRm = null;
        boolean revertedMove = false;
        boolean moveIsBackwards;
        if (moveOp.positionStart < moveOp.itemCount) {
            moveIsBackwards = false;
            if (removeOp.positionStart == moveOp.positionStart && removeOp.itemCount == moveOp.itemCount - moveOp.positionStart) {
                revertedMove = true;
            }
        } else {
            moveIsBackwards = true;
            if (removeOp.positionStart == moveOp.itemCount + 1 && removeOp.itemCount == moveOp.positionStart - moveOp.itemCount) {
                revertedMove = true;
            }
        }

        if (moveOp.itemCount < removeOp.positionStart) {
            --removeOp.positionStart;
        } else if (moveOp.itemCount < removeOp.positionStart + removeOp.itemCount) {
            --removeOp.itemCount;
            moveOp.cmd = 2;
            moveOp.itemCount = 1;
            if (removeOp.itemCount == 0) {
                list.remove(removePos);
                this.mCallback.recycleUpdateOp(removeOp);
            }

            return;
        }

        if (moveOp.positionStart <= removeOp.positionStart) {
            ++removeOp.positionStart;
        } else if (moveOp.positionStart < removeOp.positionStart + removeOp.itemCount) {
            int remaining = removeOp.positionStart + removeOp.itemCount - moveOp.positionStart;
            extraRm = this.mCallback.obtainUpdateOp(2, moveOp.positionStart + 1, remaining, (Object)null);
            removeOp.itemCount = moveOp.positionStart - removeOp.positionStart;
        }

        if (revertedMove) {
            list.set(movePos, removeOp);
            list.remove(removePos);
            this.mCallback.recycleUpdateOp(moveOp);
        } else {
            if (moveIsBackwards) {
                if (extraRm != null) {
                    if (moveOp.positionStart > extraRm.positionStart) {
                        moveOp.positionStart -= extraRm.itemCount;
                    }

                    if (moveOp.itemCount > extraRm.positionStart) {
                        moveOp.itemCount -= extraRm.itemCount;
                    }
                }

                if (moveOp.positionStart > removeOp.positionStart) {
                    moveOp.positionStart -= removeOp.itemCount;
                }

                if (moveOp.itemCount > removeOp.positionStart) {
                    moveOp.itemCount -= removeOp.itemCount;
                }
            } else {
                if (extraRm != null) {
                    if (moveOp.positionStart >= extraRm.positionStart) {
                        moveOp.positionStart -= extraRm.itemCount;
                    }

                    if (moveOp.itemCount >= extraRm.positionStart) {
                        moveOp.itemCount -= extraRm.itemCount;
                    }
                }

                if (moveOp.positionStart >= removeOp.positionStart) {
                    moveOp.positionStart -= removeOp.itemCount;
                }

                if (moveOp.itemCount >= removeOp.positionStart) {
                    moveOp.itemCount -= removeOp.itemCount;
                }
            }

            list.set(movePos, removeOp);
            if (moveOp.positionStart != moveOp.itemCount) {
                list.set(removePos, moveOp);
            } else {
                list.remove(removePos);
            }

            if (extraRm != null) {
                list.add(movePos, extraRm);
            }

        }
    }

    private void swapMoveAdd(List<UpdateOp> list, int move, UpdateOp moveOp, int add, UpdateOp addOp) {
        int offset = 0;
        if (moveOp.itemCount < addOp.positionStart) {
            --offset;
        }

        if (moveOp.positionStart < addOp.positionStart) {
            ++offset;
        }

        if (addOp.positionStart <= moveOp.positionStart) {
            moveOp.positionStart += addOp.itemCount;
        }

        if (addOp.positionStart <= moveOp.itemCount) {
            moveOp.itemCount += addOp.itemCount;
        }

        addOp.positionStart += offset;
        list.set(move, addOp);
        list.set(add, moveOp);
    }

    void swapMoveUpdate(List<UpdateOp> list, int move, UpdateOp moveOp, int update, UpdateOp updateOp) {
        UpdateOp extraUp1 = null;
        UpdateOp extraUp2 = null;
        if (moveOp.itemCount < updateOp.positionStart) {
            --updateOp.positionStart;
        } else if (moveOp.itemCount < updateOp.positionStart + updateOp.itemCount) {
            --updateOp.itemCount;
            extraUp1 = this.mCallback.obtainUpdateOp(4, moveOp.positionStart, 1, updateOp.payload);
        }

        if (moveOp.positionStart <= updateOp.positionStart) {
            ++updateOp.positionStart;
        } else if (moveOp.positionStart < updateOp.positionStart + updateOp.itemCount) {
            int remaining = updateOp.positionStart + updateOp.itemCount - moveOp.positionStart;
            extraUp2 = this.mCallback.obtainUpdateOp(4, moveOp.positionStart + 1, remaining, updateOp.payload);
            updateOp.itemCount -= remaining;
        }

        list.set(update, moveOp);
        if (updateOp.itemCount > 0) {
            list.set(move, updateOp);
        } else {
            list.remove(move);
            this.mCallback.recycleUpdateOp(updateOp);
        }

        if (extraUp1 != null) {
            list.add(move, extraUp1);
        }

        if (extraUp2 != null) {
            list.add(move, extraUp2);
        }

    }

    private int getLastMoveOutOfOrder(List<UpdateOp> list) {
        boolean foundNonMove = false;

        for(int i = list.size() - 1; i >= 0; --i) {
            UpdateOp op1 = (UpdateOp)list.get(i);
            if (op1.cmd == 8) {
                if (foundNonMove) {
                    return i;
                }
            } else {
                foundNonMove = true;
            }
        }

        return -1;
    }

    interface Callback {
        UpdateOp obtainUpdateOp(int var1, int var2, int var3, Object var4);

        void recycleUpdateOp(UpdateOp var1);
    }
}

