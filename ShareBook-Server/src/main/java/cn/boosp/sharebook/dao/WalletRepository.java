package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,String> {
}
