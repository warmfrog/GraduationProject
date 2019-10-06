package cn.booksp.sharebook.repository.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import cn.booksp.sharebook.domain.User;

/**
 * Created by warmfrog on 2019/2/25.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user where id = :id")
    LiveData<User> getUserById(Integer id);

    @Query("SELECT * FROM user where username = :username")
    LiveData<User> getUserByUsername(String username);

    @Delete
    void delete(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM user where id = :userId")
    LiveData<User> get(int userId);

}
