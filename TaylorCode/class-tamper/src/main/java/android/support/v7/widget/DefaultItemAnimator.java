package androidx.appcompat.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.support.v7.widget.RecyclerView.*;

public class DefaultItemAnimator extends ItemAnimator {
    @Override
    public boolean animateDisappearance(@NonNull ViewHolder var1, @NonNull ItemHolderInfo var2, @Nullable ItemHolderInfo var3) {
        return false;
    }

    @Override
    public boolean animateAppearance(@NonNull ViewHolder var1, @Nullable ItemHolderInfo var2, @NonNull ItemHolderInfo var3) {
        return false;
    }

    @Override
    public boolean animatePersistence(@NonNull ViewHolder var1, @NonNull ItemHolderInfo var2, @NonNull ItemHolderInfo var3) {
        return false;
    }

    @Override
    public boolean animateChange(@NonNull ViewHolder var1, @NonNull ViewHolder var2, @NonNull ItemHolderInfo var3, @NonNull ItemHolderInfo var4) {
        return false;
    }

    @Override
    public void runPendingAnimations() {

    }

    @Override
    public void endAnimation(ViewHolder var1) {

    }

    @Override
    public void endAnimations() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
