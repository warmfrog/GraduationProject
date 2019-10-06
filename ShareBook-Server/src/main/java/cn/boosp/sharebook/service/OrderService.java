package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.pojo.Order;

public interface OrderService {
    Order create(Order order);

    void close(String orderId);

    void close(Order order);

    void remove(String orderId);

    void remove(Order order);

    Order getOrder(String orderId);

}
