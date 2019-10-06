package cn.booksp.sharebook.domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 图书详细信息,根据ISBN号查询豆瓣api获得的信息.
 */
@Entity
public class Book implements Serializable {
    private Integer id;
    private String pubdate;
    private String title;
    @ColumnInfo(name = "origin_title")
    private String originTitle;
    private String image;
    private String rating;
    private String authors;
    private String tags;
    private String translator;
    private String series;
    private String type;
    /**
     * 捆绑装订类型
     */
    private String binding;
    @ColumnInfo(name = "ebook_url")
    private String ebookUrl;
    @ColumnInfo(name = "ebook_price")
    private BigDecimal ebookPrice;
    /**
     * 目录
     */
    private String catalog;
    /**
     * 页数
     */
    private Integer pages;
    private String publisher;
    @PrimaryKey
    @NonNull
    private String isbn13;
    private String price;
    private String imgUrl;
    @Ignore
    public Book(String isbn13) {
        this.isbn13 = isbn13;
    }

    public Book() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", pubdate='" + pubdate + '\'' +
                ", title='" + title + '\'' +
                ", originTitle='" + originTitle + '\'' +
                ", image='" + image + '\'' +
                ", rating='" + rating + '\'' +
                ", authors='" + authors + '\'' +
                ", tags='" + tags + '\'' +
                ", translator='" + translator + '\'' +
                ", series='" + series + '\'' +
                ", binding='" + binding + '\'' +
                ", ebookUrl='" + ebookUrl + '\'' +
                ", ebookPrice=" + ebookPrice +
                ", catalog='" + catalog + '\'' +
                ", pages=" + pages +
                ", publisher='" + publisher + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getEbookUrl() {
        return ebookUrl;
    }

    public void setEbookUrl(String ebookUrl) {
        this.ebookUrl = ebookUrl;
    }

    public BigDecimal getEbookPrice() {
        return ebookPrice;
    }

    public void setEbookPrice(BigDecimal ebookPrice) {
        this.ebookPrice = ebookPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginTitle() {
        return originTitle;
    }

    public void setOriginTitle(String value) {
        this.originTitle = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String value) {
        this.image = value;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String value) {
        this.binding = value;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String value) {
        this.catalog = value;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer value) {
        this.pages = value;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String value) {
        this.publisher = value;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String value) {
        this.isbn13 = value;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}


