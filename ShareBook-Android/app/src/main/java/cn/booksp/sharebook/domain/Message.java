package cn.booksp.sharebook.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.easemob.chat.EMMessage;

import java.util.Date;
import java.util.UUID;

@Entity
public class Message {
    Date send_time;
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;
    private String from;
    private String to;
    private String message;
    public Message(Date send_time, String from, String to, String message) {
        this.id = UUID.randomUUID().toString();
        this.send_time = send_time;
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public Message(EMMessage emMessage) {
        this.from = emMessage.getFrom();
        this.to = emMessage.getTo();
        this.id = emMessage.getMsgId();
        String substring = emMessage.getBody().toString().substring(4);
        this.message = substring.substring(1, substring.length() - 1);
        this.send_time = new Date(emMessage.getMsgTime());
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "send_time=" + send_time +
                ", id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }
}
