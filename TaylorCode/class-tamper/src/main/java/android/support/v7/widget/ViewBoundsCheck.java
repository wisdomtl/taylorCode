package android.support.v7.widget;

import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

class ViewBoundsCheck {
    static final int GT = 1;
    static final int EQ = 2;
    static final int LT = 4;
    static final int CVS_PVS_POS = 0;
    static final int FLAG_CVS_GT_PVS = 1;
    static final int FLAG_CVS_EQ_PVS = 2;
    static final int FLAG_CVS_LT_PVS = 4;
    static final int CVS_PVE_POS = 4;
    static final int FLAG_CVS_GT_PVE = 16;
    static final int FLAG_CVS_EQ_PVE = 32;
    static final int FLAG_CVS_LT_PVE = 64;
    static final int CVE_PVS_POS = 8;
    static final int FLAG_CVE_GT_PVS = 256;
    static final int FLAG_CVE_EQ_PVS = 512;
    static final int FLAG_CVE_LT_PVS = 1024;
    static final int CVE_PVE_POS = 12;
    static final int FLAG_CVE_GT_PVE = 4096;
    static final int FLAG_CVE_EQ_PVE = 8192;
    static final int FLAG_CVE_LT_PVE = 16384;
    static final int MASK = 7;
    final ViewBoundsCheck.Callback mCallback;
    ViewBoundsCheck.BoundFlags mBoundFlags;

    ViewBoundsCheck(ViewBoundsCheck.Callback callback) {
        this.mCallback = callback;
        this.mBoundFlags = new ViewBoundsCheck.BoundFlags();
    }

    View findOneViewWithinBoundFlags(int fromIndex, int toIndex, int preferredBoundFlags, int acceptableBoundFlags) {
        int start = this.mCallback.getParentStart();
        int end = this.mCallback.getParentEnd();
        int next = toIndex > fromIndex ? 1 : -1;
        View acceptableMatch = null;

        for(int i = fromIndex; i != toIndex; i += next) {
            View child = this.mCallback.getChildAt(i);
            int childStart = this.mCallback.getChildStart(child);
            int childEnd = this.mCallback.getChildEnd(child);
            this.mBoundFlags.setBounds(start, end, childStart, childEnd);
            if (preferredBoundFlags != 0) {
                this.mBoundFlags.resetFlags();
                this.mBoundFlags.addFlags(preferredBoundFlags);
                if (this.mBoundFlags.boundsMatch()) {
                    return child;
                }
            }

            if (acceptableBoundFlags != 0) {
                this.mBoundFlags.resetFlags();
                this.mBoundFlags.addFlags(acceptableBoundFlags);
                if (this.mBoundFlags.boundsMatch()) {
                    acceptableMatch = child;
                }
            }
        }

        return acceptableMatch;
    }

    boolean isViewWithinBoundFlags(View child, int boundsFlags) {
        this.mBoundFlags.setBounds(this.mCallback.getParentStart(), this.mCallback.getParentEnd(), this.mCallback.getChildStart(child), this.mCallback.getChildEnd(child));
        if (boundsFlags != 0) {
            this.mBoundFlags.resetFlags();
            this.mBoundFlags.addFlags(boundsFlags);
            return this.mBoundFlags.boundsMatch();
        } else {
            return false;
        }
    }

    interface Callback {
        int getChildCount();

        View getParent();

        View getChildAt(int var1);

        int getParentStart();

        int getParentEnd();

        int getChildStart(View var1);

        int getChildEnd(View var1);
    }

    static class BoundFlags {
        int mBoundFlags = 0;
        int mRvStart;
        int mRvEnd;
        int mChildStart;
        int mChildEnd;

        BoundFlags() {
        }

        void setBounds(int rvStart, int rvEnd, int childStart, int childEnd) {
            this.mRvStart = rvStart;
            this.mRvEnd = rvEnd;
            this.mChildStart = childStart;
            this.mChildEnd = childEnd;
        }

        void setFlags(int flags, int mask) {
            this.mBoundFlags = this.mBoundFlags & ~mask | flags & mask;
        }

        void addFlags(int flags) {
            this.mBoundFlags |= flags;
        }

        void resetFlags() {
            this.mBoundFlags = 0;
        }

        int compare(int x, int y) {
            if (x > y) {
                return 1;
            } else {
                return x == y ? 2 : 4;
            }
        }

        boolean boundsMatch() {
            if ((this.mBoundFlags & 7) != 0 && (this.mBoundFlags & this.compare(this.mChildStart, this.mRvStart) << 0) == 0) {
                return false;
            } else if ((this.mBoundFlags & 112) != 0 && (this.mBoundFlags & this.compare(this.mChildStart, this.mRvEnd) << 4) == 0) {
                return false;
            } else if ((this.mBoundFlags & 1792) != 0 && (this.mBoundFlags & this.compare(this.mChildEnd, this.mRvStart) << 8) == 0) {
                return false;
            } else {
                return (this.mBoundFlags & 28672) == 0 || (this.mBoundFlags & this.compare(this.mChildEnd, this.mRvEnd) << 12) != 0;
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewBounds {
    }
}

