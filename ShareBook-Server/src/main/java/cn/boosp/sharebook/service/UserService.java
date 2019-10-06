package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.exception.AccountException;
import cn.boosp.sharebook.common.exception.BalanceNotEnoughException;
import cn.boosp.sharebook.common.exception.UserAlreadyExistException;
import cn.boosp.sharebook.common.exception.UserNotFoundException;
import cn.boosp.sharebook.common.pojo.Contact;
import cn.boosp.sharebook.common.pojo.Order;
import cn.boosp.sharebook.common.pojo.Ubook;
import cn.boosp.sharebook.common.pojo.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User addUser(User user) throws AccountException;

    User addAdmin(User user) throws AccountException;

    User updateUser(Integer id, User user) throws UserNotFoundException;

    User findUserById(Integer id) throws UserNotFoundException;

    User findUserByUsername(String username) throws UserNotFoundException;

    Integer getIdByUsername(String username) throws UserNotFoundException;

    void deleteUser(Integer id) throws UserNotFoundException;

    void release(String username, Ubook ubook) throws UserNotFoundException;

    Boolean exist(String username);

    boolean pay(User customer,Order order) throws BalanceNotEnoughException;

    void sign(Order order);

    boolean addBalance(User user, BigDecimal amount);

    boolean addBalance(String username, BigDecimal amount);

    boolean addContact(Contact contact);

}
