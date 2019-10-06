package cn.boosp.sharebook;

import cn.boosp.sharebook.common.pojo.Book;
import cn.boosp.sharebook.common.pojo.Release;
import cn.boosp.sharebook.common.pojo.Ubook;
import cn.boosp.sharebook.common.pojo.User;
import cn.boosp.sharebook.dao.BookRepository;
import cn.boosp.sharebook.dao.ReleaseRepository;
import cn.boosp.sharebook.dao.UbookRepository;
import cn.boosp.sharebook.dao.UserRepository;
import cn.boosp.sharebook.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SharebookApplicationTests {

    private static final String URL = "http://localhost:8080";
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    ReleaseRepository releaseRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UbookRepository ubookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    public void testGetAllUsers(){
        ResponseEntity<User[]> responseEntity =
                restTemplate.getForEntity(URL + "/user", User[].class);
        List<User> users = Arrays.asList(responseEntity.getBody());
        assertNotNull(users);
    }

    @Test
    public void testLogin(){
        User user = new User();
        user.setUsername("warmfrog");
        user.setPassword("159491");
        restTemplate.postForLocation(URL + "/login", user);
    }

    @Test
    public void testRelease(){
        Release release = new Release();
        Book book = new Book("9787115330550");
//        bookRepository.save(book);

        Ubook ubook = new Ubook();
        ubook.setBook(book);
        ubook.setRentPrice(BigDecimal.valueOf(2.5));

//        ubook.setType(Ubook.DealType.onrent);
//        ubookRepository.save(ubook);

        List<Ubook> ubooks = new ArrayList<>();
        ubooks.add(ubook);
        release.setUbooks(ubooks);

//        userService.release(release);
//        releaseRepository.save(release);

//        User user = new User("warmfrog","159491");
//        List<Release> releases = new ArrayList<>();
//        releases.add(release);
//        user.setReleases(releases);

//        userRepository.save(user);
    }

    @Test
    public void testGetUserById(){
        User user = restTemplate.getForObject(URL + "/user/1", User.class);
        assertNotNull(user);
    }

    @Test
    public void testCreateUser(){
        User user = new User();
        user.setEmail("create@qq.com");
        user.setPassword("create_password");
        user.setCreateDate(new Date());

        ResponseEntity<User> userResponse =
                restTemplate.postForEntity(URL + "/user", user, User.class);
        assertNotNull(userResponse);
        assertNotNull(userResponse.getBody());
    }

    @Test
    public void testUpdateUser(){
        int id =1;
        User user = restTemplate.getForObject(URL + "/user/" + id, User.class);
        user.setPhone("520");

        restTemplate.put(URL + "/user/" + id,user);

        User updateUser = restTemplate.getForObject(URL + "/user/" + id, User.class);
        assertNotNull(updateUser);
    }

    @Test
    public void testDeleteUser(){
        int id = 2;
        User user = restTemplate.getForObject(URL + "/user/" + id, User.class);
        assertNotNull(user);

        restTemplate.delete(URL + "/user/" + id);
        try{
            user = restTemplate.getForObject(URL + "/user/" + id, User.class);
        }catch (final HttpClientErrorException e){
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void contextLoads() {
    }

}

