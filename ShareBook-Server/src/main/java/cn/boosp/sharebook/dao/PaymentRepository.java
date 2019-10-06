package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Payment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentRepository extends PagingAndSortingRepository<Payment, Integer> {
}
