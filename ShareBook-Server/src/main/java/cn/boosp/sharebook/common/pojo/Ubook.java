package cn.boosp.sharebook.common.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Ubook {

    @Enumerated(EnumType.STRING)
    Status status;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinTable(name = "ubook_gbook",
            joinColumns = @JoinColumn(name = "ubook_id"),
            inverseJoinColumns = @JoinColumn(name = "volume_info_id"))
    Gbook.VolumeInfo volumeInfo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "ubook_book",
            joinColumns = {
                    @JoinColumn(
                            name = "ubook_id")},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "book_id")})
    private Book book;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_ubook",
            joinColumns = {
                    @JoinColumn(
                            name = "ubook_id")},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "user_id")}
    )
    private User user;
    private String isbn13;
    private String bookIntro;
    private BigDecimal sellPrice;
    private BigDecimal rentPrice;
    private String image;
    private String type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseTime;

    public Ubook() {
        releaseTime
                = new Date(System.currentTimeMillis());
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Gbook.VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(Gbook.VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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


    public enum Status {
        normal("租售"), onorder("订单中"), removed("移除");
        private String value;

        Status(String value) {
            this.value = value;
        }
    }

}
