package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.pojo.User;
import cn.boosp.sharebook.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserDeatilsImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles().stream().map((role) -> role.getName())
                .toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);

        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Optional<User> users;
        User user;
        users = userRepository.findUserByUsername(account);
        if (!users.isPresent()) {
            users = userRepository.findUserByEmail(account);
            if (!users.isPresent()) {
                users = userRepository.findUserByPhone(account);
            }
        }
        if (users.isPresent()) {
            user = users.get();
        } else {
            throw new UsernameNotFoundException("用户名没有找到");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user)
        );
    }
}
