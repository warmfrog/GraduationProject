package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Integer> {
}
