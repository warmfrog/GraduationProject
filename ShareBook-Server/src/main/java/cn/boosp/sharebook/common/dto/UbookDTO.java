package cn.boosp.sharebook.common.dto;

import cn.boosp.sharebook.common.pojo.Book;
import cn.boosp.sharebook.common.pojo.Gbook;
import cn.boosp.sharebook.common.pojo.Ubook;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UbookDTO implements Serializable {
    Integer id;
    String status;
    Integer bookId;
    String username;
    String bookName;
    String bookIntro;
    String isbn13;
    Date releaseTime;
    BigDecimal sellPrice;
    BigDecimal rentPrice;
    String type;
    String image;

    public UbookDTO() {
    }

    public UbookDTO(Ubook ubook) {
        Book book = ubook.getBook();
        if (null != book) {
            this.bookName = book.getTitle();
            this.bookId = book.getId();
        }
        Gbook.VolumeInfo volumeInfo = ubook.getVolumeInfo();
        if (volumeInfo != null) {
            this.bookName = volumeInfo.getTitle();
            this.bookId = volumeInfo.getId();
            Gbook.ImageLinks imageLinks = volumeInfo.getImageLinks();
            if (imageLinks != null) {
                this.image = imageLinks.getThumbnail();
            }
        }

        this.status = ubook.getStatus().name();
        this.id = ubook.getId();
        this.username = ubook.getUser().getUsername();
        this.bookIntro = ubook.getBookIntro();
        this.sellPrice = ubook.getSellPrice();
        this.rentPrice = ubook.getRentPrice();
        this.releaseTime = ubook.getReleaseTime();
        this.isbn13 = ubook.getIsbn13();
        this.type = ubook.getType();
        this.image = ubook.getImage();
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
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
