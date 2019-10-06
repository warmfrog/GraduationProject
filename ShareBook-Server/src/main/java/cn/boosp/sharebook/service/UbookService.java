package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.exception.UbookNotFoundException;
import cn.boosp.sharebook.common.exception.UserNotFoundException;
import cn.boosp.sharebook.common.pojo.Ubook;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface UbookService {
    List<Ubook> findAll();

    List<Ubook> findAllByUsername(String username) throws UserNotFoundException;

    List<Ubook> findAllByUserId(Integer userId) throws UserNotFoundException;

    Set<String> findUbookIsbns(String username) throws UserNotFoundException;

    Integer getCountByUsername(String username) throws UserNotFoundException;

    Ubook findUbookById(Integer id) throws UbookNotFoundException;

    Boolean deleteUbookById(Integer id);

    Boolean deleteUbookByIsbn13(String isbn13);

    Integer addUbook(Ubook ubook);

    Ubook updateUbook(Ubook ubook) throws UbookNotFoundException;

    Page<Ubook> listUbook(Integer pageIndex, Integer pageSize);

    List<Ubook> findAll(List<String> isbns);

    List<Ubook> getBefore(Integer id, Integer limit);

    List<Ubook> getAfter(Integer id, Integer limit);

    Page<Ubook> getTop(Integer limit);

}
