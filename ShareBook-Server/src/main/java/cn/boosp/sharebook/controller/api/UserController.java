package cn.boosp.sharebook.controller.api;

import cn.boosp.sharebook.common.ApiResponse;
import cn.boosp.sharebook.common.dto.OrderDTO;
import cn.boosp.sharebook.common.dto.UserDTO;
import cn.boosp.sharebook.common.exception.*;
import cn.boosp.sharebook.common.pojo.*;
import cn.boosp.sharebook.service.OrderService;
import cn.boosp.sharebook.service.UbookService;
import cn.boosp.sharebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.boosp.sharebook.common.ApiResponse.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UbookService ubookService;
    @Autowired
    OrderService orderService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("")
    public List<User> list() {
        return userService.findAll();
    }

    @PostMapping("")
    public ApiResponse add(@RequestBody UserDTO userDTO) {
        User user =new User(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User save = null;
        try {
            save = userService.addUser(user);
        } catch (AccountException e) {
           return new ApiResponse(Status.error, null, new ApiError(400, e.getMessage()));
        }
        return new ApiResponse(Status.ok, new UserDTO(save));
    }

    @GetMapping("/check/{username}")
    public ApiResponse check(@PathVariable("username") String username) {
        if (username.length() < 6) {
            return new ApiResponse(Status.error, null, new ApiError(400, "用户名不合法"));
        } else if (userService.exist(username)) {
            return new ApiResponse(Status.error, null, new ApiError(400, "用户名已存在"));
        } else {
            return new ApiResponse(Status.ok, null);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse get(@PathVariable("id") Integer id) {
        User user = null;
        try {
            user = userService.findUserById(id);
            return new ApiResponse(Status.ok, new UserDTO(user));
        } catch (UserNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiError(400,e.getMessage()));
        }

    }

    @PutMapping("/{id}")
    public ApiResponse update(@PathVariable("id") Integer id, @RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        try {
            userService.updateUser(id,user);
            return new ApiResponse(Status.ok, null);
        } catch (UserNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiError(400,e.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable("id") Integer id) {
        try {
            userService.deleteUser(id);
            return new ApiResponse(Status.ok, null);
        } catch (UserNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiError(400,e.getMessage()));
        }

    }

    @PostMapping("/{username}/release")
    public ApiResponse release(@PathVariable String username,@RequestBody Ubook ubook) {
        try {
            userService.release(username, ubook);
            return new ApiResponse(Status.ok, null);
        } catch (UserNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiError(400,e.getMessage()));
        }

    }

    @GetMapping("/{username}/contact")
    public ApiResponse getContact(@PathVariable String username){
        try {
            User client = userService.findUserByUsername(username);
            List<Contact> contacts = client.getContacts();
            return new ApiResponse(Status.ok,contacts);
        } catch (UserNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiError(400,e.getMessage()));
        }
    }

    @PostMapping("/{username}/contact")
    public ApiResponse addContact(@PathVariable String username, @RequestBody Contact contact){
        try {
            User user = userService.findUserByUsername(username);
            contact.setUser(user);
            userService.addContact(contact);
            return new ApiResponse(Status.ok,null);
        } catch (UserNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiError(400,e.getMessage()));
        }
    }

    @GetMapping("/{username}/balance")
    public ApiResponse balance(@PathVariable String username){
        User user;
        try {
            user = userService.findUserByUsername(username);
        } catch (UserNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiError(400,e.getMessage()));
        }
        BigDecimal balance = user.getWallet().getBalance();
        return new ApiResponse(Status.ok, balance);
    }

    @PostMapping("/{username}/order/{ubookId}")
    public ApiResponse order(@PathVariable String username, @PathVariable Integer ubookId,String type, BigDecimal price){
        try {
            User user = userService.findUserByUsername(username);
            Ubook ubook = ubookService.findUbookById(ubookId);
            Order order = new Order(user, ubook, price, type);
            Order info = orderService.create(order);
            return new ApiResponse(Status.ok, new OrderDTO(info));
        } catch (Exception e) {
            return new ApiResponse(Status.error, null, new ApiError(400,e.getMessage()));
        }
    }

    @PostMapping("/{username}/cancel/{orderId}")
    public ApiResponse cancel(@PathVariable String username, @PathVariable String orderId){
        orderService.close(orderId);
        return new ApiResponse(Status.ok, "订单已取消");
    }

    @GetMapping("/{username}/myorder")
    public ApiResponse myorder(@PathVariable String username){
        try {
            User user = userService.findUserByUsername(username);
            List<Order> orders = user.getOrders();
            List<OrderDTO> orderDTOS = getOrderDTOS(orders);
            return new ApiResponse(Status.ok, orderDTOS);
        } catch (UserNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiError(400,e.getMessage()));
        }
    }

    private List<OrderDTO> getOrderDTOS(List<Order> orders) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders){
            orderDTOS.add(new OrderDTO(order));
        }
        return orderDTOS;
    }

    @PostMapping("/{username}/pay/{orderId}")
    public ApiResponse pay(@PathVariable String username, @PathVariable String orderId){
        try {
            User user = userService.findUserByUsername(username);
            Order order = orderService.getOrder(orderId);
            boolean success = userService.pay(user, order);
            if(success){
                return new ApiResponse(Status.ok,"恭喜您！支付成功");
            }else {
                return new ApiResponse(Status.error, "余额不足！支付失败。");
            }
        } catch (Exception e) {
            return new ApiResponse(Status.error, null, new ApiError(400,e.getMessage()));
        }
    }

    @PostMapping("/{username}/sign/{orderId}")
    public ApiResponse sign(@PathVariable String username, @PathVariable String orderId){
        Order order = orderService.getOrder(orderId);
        userService.sign(order);
        return new ApiResponse(Status.ok, "订单已签收");
    }

    @DeleteMapping("/{username}/delete/{orderId}")
    public ApiResponse delete(@PathVariable String username, @PathVariable String orderId){
        orderService.remove(orderId);
        return new ApiResponse(Status.ok, "订单已删除");
    }
}
