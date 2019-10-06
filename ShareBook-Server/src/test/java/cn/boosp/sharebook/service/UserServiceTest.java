package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.exception.UserNotFoundException;
import cn.boosp.sharebook.common.pojo.Book;
import cn.boosp.sharebook.common.pojo.Ubook;
import cn.boosp.sharebook.common.pojo.Ubook.Status;
import cn.boosp.sharebook.common.pojo.User;
import cn.boosp.sharebook.dao.UbookRepository;
import cn.boosp.sharebook.dao.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @Autowired
    UbookRepository ubookRepository;
    @Autowired
    UserRepository userRepository;



    @Test
    public void testReleaseBook() throws UserNotFoundException {
        Book book = bookService.get("9787115351708");
        User user = userService.findUserById(6);
        Ubook ubook = new Ubook();
        ubook.setBook(book);
        ubook.setRentPrice(BigDecimal.valueOf(2.5));
        ubook.setStatus(Status.normal);
        ubook.setBookIntro("一本好书");
        ubook.setUser(user);
        ubookRepository.save(ubook);
    }

    @Test
    public void testReleaseBook2() throws UserNotFoundException {
        Book book = bookService.get("9787111467045");
        Ubook ubook = new Ubook();
        ubook.setBook(book);
        ubook.setRentPrice(BigDecimal.valueOf(2.5));
        ubook.setStatus(Status.normal);
        ubook.setBookIntro("一本好书");
        userService.release("warmfrog", ubook);
    }
}
