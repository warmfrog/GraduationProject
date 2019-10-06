package cn.booksp.sharebook.repository.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import cn.booksp.sharebook.domain.Message;



@Dao
public interface MessageDao {
    @Query("SELECT * FROM message where id = :id")
    LiveData<Message> getMessageById(Integer id);

    @Query("SELECT * FROM message where id = :id")
    Message find(Integer id);

    @Query("select * from message where `from` = :from and `to` = :to or `from`=:to and `to` = :from order by send_time")
//    @Query("select * from message where `from`=:from or `from`=:to")
    LiveData<List<Message>> getRecords(String from,String to);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Message message);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Message> messages);

    @Query("delete from message")
    void deleteAll();
}
