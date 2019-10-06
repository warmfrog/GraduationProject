package cn.booksp.sharebook.domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class Contact {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "recent_time")
    private Date recentTime;
    @ColumnInfo(name = "first_message")
    private String firstMessage;

    public Contact() {
    }

    public Contact(@NonNull String name, Date recentTime, String firstMessage) {
        this.name = name;
        this.recentTime = recentTime;
        this.firstMessage = firstMessage;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Date getRecentTime() {
        return recentTime;
    }

    public void setRecentTime(Date recentTime) {
        this.recentTime = recentTime;
    }

    public String getFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(String firstMessage) {
        this.firstMessage = firstMessage;
    }
}
