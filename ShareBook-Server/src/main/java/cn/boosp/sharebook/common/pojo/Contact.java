package cn.boosp.sharebook.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;
    @Column(name = "[name]", nullable = false)
    private String name;
    @Column(name = "recent_time", nullable = false)
    private Date recentTime;
    @Column(name = "first_message", nullable = false)
    private String firstMessage;
    @JsonIgnore
    @ManyToOne
    @JoinTable(
            name = "user_contact",
            joinColumns = {@JoinColumn(
                    name = "user_id"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "contact_id"
            )})
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
