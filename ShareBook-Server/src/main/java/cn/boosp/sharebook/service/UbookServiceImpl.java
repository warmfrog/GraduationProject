package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.exception.AccountException;
import cn.boosp.sharebook.common.exception.UbookNotFoundException;
import cn.boosp.sharebook.common.exception.UserNotFoundException;
import cn.boosp.sharebook.common.pojo.Ubook;
import cn.boosp.sharebook.common.pojo.User;
import cn.boosp.sharebook.common.pojo.Wallet;
import cn.boosp.sharebook.dao.UbookRepository;
import cn.boosp.sharebook.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static cn.boosp.sharebook.common.pojo.Ubook.*;

@Service
public class UbookServiceImpl implements UbookService {
    @Autowired
    UbookRepository ubookRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Ubook> findAll() {
        return ubookRepository.findAll();
    }

    @Override
    public List<Ubook> findAllByUsername(String username) throws UserNotFoundException {
        User user = userService.findUserByUsername(username);
        return user.getUbooks();
    }

    @Override
    public List<Ubook> findAllByUserId(Integer userId) throws UserNotFoundException {
        User user = userService.findUserById(userId);
        return user.getUbooks();
    }

    @Override
    public Set<String> findUbookIsbns(String username) throws UserNotFoundException {
        User user = userService.findUserByUsername(username);
        List<Ubook> ubooks = user.getUbooks();
        Set<String> isbns = new HashSet<>();
        for(Ubook ubook : ubooks){
            isbns.add(ubook.getBook().getIsbn13());
        }
        return isbns;
    }

    @Override
    public Integer getCountByUsername(String username) throws UserNotFoundException {
        User user = userService.findUserByUsername(username);
        return user.getUbooks().size();
    }


    @Override
    public Ubook findUbookById(Integer id) throws UbookNotFoundException {
        return ubookRepository.findById(id).orElseThrow(
                () -> new UbookNotFoundException("该书不存在")

        );
    }

    @Override
    public Boolean deleteUbookById(Integer id) {
        ubookRepository.deleteById(id);
        return true;
    }

    @Override
    public Boolean deleteUbookByIsbn13(String isbn13) {
        return ubookRepository.deleteByIsbn13(isbn13) > 0;
    }

    @Override
    public Integer addUbook(Ubook ubook) {
        Ubook save = ubookRepository.save(ubook);
        userService.addBalance(ubook.getUser(), BigDecimal.valueOf(1));
        return save.getId();
    }

    @Override
    public Ubook updateUbook(Ubook ubook) throws UbookNotFoundException {
        ubookRepository.findById(ubook.getId()).orElseThrow(
                () -> new UbookNotFoundException("该书不存在")

        );
        Ubook save = ubookRepository.save(ubook);
        return save;
    }

    @Override
    public Page<Ubook> listUbook(Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return ubookRepository.findAll(pageable);
    }

    @Override
    public List<Ubook> findAll(List<String> isbns) {
        return ubookRepository.findAllByIsbn13In(isbns);
    }

    @Override
    public List<Ubook> getBefore(Integer id, Integer limit) {
        return ubookRepository.findTop10ByIdBeforeAndStatusEquals(id, Status.normal);
    }

    @Override
    public List<Ubook> getAfter(Integer id, Integer limit) {
        return ubookRepository.findByStatusEqualsAndIdAfter(Status.normal,id );
    }

    @Override
    public Page<Ubook> getTop(Integer limit) {
        Pageable pageable = PageRequest.of(0,10);
        return ubookRepository.findAllByStatusEquals(pageable, Status.normal);
    }
}
