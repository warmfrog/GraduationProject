package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, String> {
}
