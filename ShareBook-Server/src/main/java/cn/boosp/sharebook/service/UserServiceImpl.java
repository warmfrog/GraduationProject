package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.exception.*;
import cn.boosp.sharebook.common.pojo.*;
import cn.boosp.sharebook.common.pojo.Order.OrderStatus;
import cn.boosp.sharebook.dao.*;
import cn.boosp.sharebook.easechat.api.IMUserAPI;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.UserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReleaseRepository releaseRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UbookRepository ubookRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    BankService bankService;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    IMUserAPI userAPI;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) throws AccountException {
        if (!exists(user)) {
            Role role_user = roleRepository.findByName("ROLE_USER");
            if (role_user == null) {
                role_user = new Role("ROLE_USER");
                roleRepository.save(role_user);
            }
            List<Role> roles = new ArrayList<>();
            return getUser(user, role_user, roles);
        }
        return null;
    }

    @Override
    public User addAdmin(User user) throws AccountException {
        if (!exists(user)) {

            Role role_user = roleRepository.findByName("ROLE_USER");
            if (role_user == null) {
                role_user = new Role("ROLE_USER");
                roleRepository.save(role_user);
            }
            Role role_admin = roleRepository.findByName("ROLE_ADMIN");
            if (role_admin == null) {
                role_admin = new Role("ROLE_ADMIN");
                roleRepository.save(role_admin);
            }

            List<Role> roles = new ArrayList<>();
            roles.add(role_admin);
            return getUser(user, role_user, roles);
        }
        return null;
    }

    private User getUser(User user, Role role_user, List<Role> roles) {
        roles.add(role_user);
        user.setRoles(roles);
        Wallet wallet = new Wallet();
        user.setWallet(wallet);
        bankService.add(wallet.getBalance());
        User save = userRepository.save(user);

        io.swagger.client.model.User user1 = new io.swagger.client.model.User();
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getUsername());
        RegisterUsers userData = new RegisterUsers();
        userData.add(user1);
        userAPI.createNewIMUserSingle(userData);
        return save;
    }

    private Map<String, String> getUserData(User save) {
        Map<String,String> userData = new HashMap<>();
        userData.put("username", save.getUsername());
        userData.put("passwrd", save.getUsername());
        userData.put("nickname", save.getUsername());
        return userData;
    }

    @Override
    public User updateUser(Integer id, User user) throws UserNotFoundException {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException
                        ("Not user found with id=" + id));
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Integer id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException
                        ("Not user found with id=" + id));
    }

    @Override
    public User findUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(
                () -> new UserNotFoundException("用户名不存在")
        );
    }

    @Override
    public Integer getIdByUsername(String username) throws UserNotFoundException {
        User user = findUserByUsername(username);
        return user.getId();
    }

    @Override
    public void deleteUser(Integer id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException
                        ("Not user found with id=" + id));
        userRepository.deleteById(user.getId());
    }

    @Override
    public void release(String username, Ubook ubook) throws UserNotFoundException {
        User user = userRepository.findUserByUsername(username).get();
        if (user == null) {
            throw new UserNotFoundException();
        }
        ubook.setUser(user);
        ubookRepository.save(ubook);
    }

    @Override
    public Boolean exist(String account) {
        if (userRepository.existsByUsername(account)) {
            return true;
        } else if (userRepository.existsByPhone(account)) {
            return true;
        } else return userRepository.existsByEmail(account);
    }

    @Override
    public boolean pay(User customer, Order order){

        BigDecimal money = order.getPrice();
        Wallet wallet = customer.getWallet();
        BigDecimal balance = wallet.getBalance();

        if (balance.compareTo(money) >= 0) {
            wallet.reduce(money);
            walletRepository.save(wallet);
            order.setStatus(OrderStatus.paid);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    @Override
    /**
     * 用户签收，将钱从中间账户打到书籍原主人账户。
     */
    public void sign(Order order) {
        User owner = order.getUbook().getUser();
        Wallet wallet = owner.getWallet();
        wallet.add(order.getPrice());
        walletRepository.save(wallet);
        order.setStatus(OrderStatus.finished);
        orderRepository.save(order);
    }

    @Override
    public boolean addBalance(User user,BigDecimal amount) {
        Wallet wallet = user.getWallet();
        wallet.add(amount);
        walletRepository.save(wallet);
        return true;
    }

    @Override
    public boolean addBalance(String username, BigDecimal amount) {
        try {
            User user = findUserByUsername(username);
            addBalance(user, amount);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addContact(Contact contact) {
        contactRepository.save(contact);
        return true;
    }

    private Boolean exists(User user) throws AccountException {
        String email = StringUtils.trimAllWhitespace(user.getEmail());
        String username = StringUtils.trimAllWhitespace(user.getUsername());
        String phone = StringUtils.trimAllWhitespace(user.getPhone());
        if (username.isEmpty()) {
            throw new UsernameIsNullException();
        }
        if (email == null && phone == null) {
            throw new EmailOrPhoneIsNullException();
        }
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException();
        }
        if (email != null && userRepository.existsByEmail(email) || phone != null && userRepository.existsByPhone(phone)) {
            throw new UserAlreadyExistException();
        }
        return false;
    }
}
