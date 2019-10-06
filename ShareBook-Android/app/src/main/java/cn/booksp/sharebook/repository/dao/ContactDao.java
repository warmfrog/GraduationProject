package cn.booksp.sharebook.repository.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import cn.booksp.sharebook.domain.Contact;

@Dao
public interface ContactDao {

    @Query("select * from contact order by recent_time")
    LiveData<List<Contact>> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    void insert(Contact contact);

    @Query("select * from contact where `name` = :name")
    Contact get(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    void insertAll(List<Contact> contacts);

    @Query("delete from contact")
    void deleteAll();
}
