package cn.booksp.sharebook.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.repository.UbookRepository;

/**
 * Created by warmfrog on 2019/2/25.
 */

public class UbookViewModel extends ViewModel {
    public LiveData<PagedList<Ubook>> ubooks;
    private UbookRepository ubookRepository = UbookRepository.getInstance();

    public UbookViewModel() {
        ubooks = ubookRepository.getUbooks();
    }
}
