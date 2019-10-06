package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SeriesRepository extends PagingAndSortingRepository<Book.Series,Integer> {
}
