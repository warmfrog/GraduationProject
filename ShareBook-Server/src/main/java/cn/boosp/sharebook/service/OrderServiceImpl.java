package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.pojo.Order;
import cn.boosp.sharebook.common.pojo.Order.OrderStatus;
import cn.boosp.sharebook.common.pojo.Ubook;
import cn.boosp.sharebook.dao.OrderRepository;
import cn.boosp.sharebook.dao.UbookRepository;
import cn.boosp.sharebook.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UbookRepository ubookRepository;

    @Override
    public Order create(Order order) {
        Ubook ubook = order.getUbook();
        ubook.setStatus(Ubook.Status.onorder);
        ubookRepository.save(ubook);

        Order save = orderRepository.save(order);
        return save;
    }

    @Override
    public void close(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(OrderStatus.closed);
            orderRepository.save(order);
        }
    }

    @Override
    public void close(Order order) {
        order.setStatus(OrderStatus.closed);
        orderRepository.save(order);
    }

    @Override
    public void remove(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public void remove(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public Order getOrder(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } else {
            return null;
        }
    }
}
