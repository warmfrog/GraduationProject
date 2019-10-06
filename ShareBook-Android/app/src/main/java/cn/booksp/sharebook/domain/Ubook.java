package cn.booksp.sharebook.domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity(indices = {@Index("book_id"), @Index(value = "username")})
public class Ubook implements Serializable {
    @ColumnInfo(name = "status")
    String status;
    @PrimaryKey(autoGenerate = false)
    private Integer id;
    @ColumnInfo(name = "book_id")
    private int bookId;
    private String isbn13;
    @ColumnInfo(name = "book_name")
    private String bookName;
    @ColumnInfo(name = "book_intro")
    private String bookIntro;
    @ColumnInfo(name = "sell_price")
    private BigDecimal sellPrice;
    @ColumnInfo(name = "rent_price")
    private String type;

    private String image;
    private Date releaseTime;
    private BigDecimal rentPrice;
    private String username;

    public Ubook() {
        this.releaseTime = new Date(System.currentTimeMillis());
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ubook ubook = (Ubook) o;
        return id.equals(ubook.id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Ubook{" +
                ", id=" + id +
                ", bookIntro='" + bookIntro + '\'' +
                ", sellPrice=" + sellPrice +
                ", rentPrice=" + rentPrice +
                '}';
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

}
