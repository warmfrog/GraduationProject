package cn.boosp.sharebook;

import cn.boosp.sharebook.common.exception.AccountException;
import cn.boosp.sharebook.common.exception.UserNotFoundException;
import cn.boosp.sharebook.common.pojo.Role;
import cn.boosp.sharebook.common.pojo.User;
import cn.boosp.sharebook.common.pojo.Wallet;
import cn.boosp.sharebook.dao.RoleRepository;
import cn.boosp.sharebook.dao.UserRepository;
import cn.boosp.sharebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SharebookApplication {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {

        SpringApplication.run(SharebookApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

