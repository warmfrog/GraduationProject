package cn.booksp.sharebook.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import cn.booksp.sharebook.domain.Book;

/**
 * Created by warmfrog on 2019/2/25.
 */
@Dao
public interface BookDao {
    @Query("select * from book")
    List<Book> getAll();

    @Query("select * from book where id = :bookId")
    Book findBookById(Integer bookId);

    @Query("select * from book where id in (:bookIds)")
    List<Book> loadAllByIds(int [] bookIds);

    @Query("select origin_title from book where id in (:bookIds)")
    List<String> getBookNames(int [] bookIds);

    @Query("select * from book where isbn13 = :isbn13")
    Book findByISBN13(String isbn13);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Book... books);

    @Delete
    void delete(Book book);

    @Query("select isbn13 from book where isbn13 = :isbn13")
    String find(String isbn13);
    @Query("delete from book")
    void deleteAll();
}
