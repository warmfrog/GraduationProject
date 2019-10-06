package cn.booksp.sharebook.ui.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import cn.booksp.sharebook.domain.Order;
import cn.booksp.sharebook.domain.Ubook;

public class OrderViewModel extends ViewModel {
    List<Order> orderList;
    Map<Integer, Ubook> ubookMap;

    public Map<Integer, Ubook> getUbookMap() {
        return ubookMap;
    }

    public void setUbookMap(Map<Integer, Ubook> ubookMap) {
        this.ubookMap = ubookMap;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
