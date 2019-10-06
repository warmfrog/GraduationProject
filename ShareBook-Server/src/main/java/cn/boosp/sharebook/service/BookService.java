package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.exception.BookException;
import cn.boosp.sharebook.common.exception.BookNotFoundException;
import cn.boosp.sharebook.common.pojo.Book;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface BookService {
    Book addBook(String isbn13);

    Boolean delete(Integer id) throws BookException;

    List<Book> findAll();

    Book get(String isbn13);

    Book getBookById(Integer id) throws BookNotFoundException;

    Page<Book> listBooks(Integer pageIndex, Integer pageSize);
}
