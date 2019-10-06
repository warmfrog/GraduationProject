package cn.booksp.sharebook.ui.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import cn.booksp.sharebook.domain.Ubook;

public class MyUbookViewModel extends ViewModel {
    private List<Ubook> ubooks;

    public List<Ubook> getUbooks() {
        return ubooks;
    }

    public void setUbooks(List<Ubook> ubooks) {
        this.ubooks = ubooks;
    }
}
