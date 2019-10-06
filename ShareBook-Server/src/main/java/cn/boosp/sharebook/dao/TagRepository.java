package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepository extends PagingAndSortingRepository<Book.Tag, Integer> {
}
