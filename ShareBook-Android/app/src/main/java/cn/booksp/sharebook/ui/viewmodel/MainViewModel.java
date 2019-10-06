package cn.booksp.sharebook.ui.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    MutableLiveData<String> username;

    public MutableLiveData<String> getUsername() {
        if(username == null){
            username = new MutableLiveData<>();
        }
        return username;
    }
}
