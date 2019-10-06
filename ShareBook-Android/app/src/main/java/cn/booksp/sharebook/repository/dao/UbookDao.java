package cn.booksp.sharebook.repository.dao;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import cn.booksp.sharebook.domain.Ubook;
import retrofit2.http.DELETE;

/**
 * Created by warmfrog on 2019/2/25.
 */

@Dao
public interface UbookDao {
    @Query("SELECT * FROM ubook")
    DataSource.Factory<Integer, Ubook> getAll();

    @Query("select * from ubook where username = :username")
    List<Ubook> getAll(String username);

    @Query("select isbn13 from ubook")
    List<String> getBookIsbns();

    @Query("select * from ubook where id = :ubookId")
    Ubook getUbook(Integer ubookId);

    @Query("select isbn13 from ubook where username = :username")
    List<String> getUbookIsbns(String username);

    @Query("select count(id) from ubook where username = :username")
    Integer count(String username);

    @Query("SELECT MAX(id) + 1 from ubook")
    Integer getNextIndex();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Ubook... ubooks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Ubook> ubooks);

    @Delete
    void delete(Ubook ubook);

    @Query("delete from ubook where id = :id")
    void delete(Integer id);

    @Query("delete  from ubook")
    void deleteAll();

    @Query("select * from ubook")
    DataSource.Factory<Integer, Ubook> getUbooks();
}
