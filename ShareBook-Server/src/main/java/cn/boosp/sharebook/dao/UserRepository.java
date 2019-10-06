package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String account);
    Optional<User> findUserByPhone(String phone);
    Optional<User> findUserByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
}
