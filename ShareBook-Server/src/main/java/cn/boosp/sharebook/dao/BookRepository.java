package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findBookByIsbn13(String isbn13);
    Boolean existsBookByIsbn13(String isbn13);
}
