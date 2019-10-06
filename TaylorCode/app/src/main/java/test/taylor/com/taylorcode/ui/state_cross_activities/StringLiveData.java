package test.taylor.com.taylorcode.ui.state_cross_activities;


import androidx.lifecycle.MutableLiveData;

public class StringLiveData extends MutableLiveData<String> {

    public static class Holder {
        public static final StringLiveData INSTANCE = new StringLiveData();
    }

    public static StringLiveData getInstance() {
        return Holder.INSTANCE;
    }

    private StringLiveData() {

    }

}
