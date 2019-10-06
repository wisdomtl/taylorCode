package test.taylor.com.taylorcode.ui.navigation;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * a Fragment hold the sate of it's view
 * the state is saved in {@link #onSaveState(Bundle)} and restored in {@link #onRestoreState(Bundle)}
 */
public abstract class StateFragment extends Fragment {

    private static final String KEY_STATE = "KEY_STATE";

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        restoreStateToArguments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveStateToArguments();
    }

    private void restoreStateToArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            Bundle state = arguments.getBundle(KEY_STATE);
            if (state != null) {
                onRestoreState(state);
            }
        }
    }

    private void saveStateToArguments() {
        Bundle state = new Bundle();
        onSaveState(state);
        Bundle arguments = getArguments();
        if (arguments != null) {
            arguments.putBundle(KEY_STATE, state);
        }
    }

    protected abstract void onSaveState(Bundle state);

    protected abstract void onRestoreState(Bundle state);
}
