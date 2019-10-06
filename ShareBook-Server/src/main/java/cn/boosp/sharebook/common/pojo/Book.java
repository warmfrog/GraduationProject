package cn.boosp.sharebook.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 图书详细信息,根据ISBN号查询豆瓣api获得的信息.
 */
@Entity
@Proxy(lazy = false)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_rating",
            joinColumns = {@JoinColumn(name = "book_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id",
                    referencedColumnName = "id")})
    private Rating rating;

    @Column(length = 50)
    private String subtitle;
    @ElementCollection
    private List<String> author;
    private String pubdate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_tag",
            joinColumns = {@JoinColumn(name = "book_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id",
                    referencedColumnName = "id")})
    private List<Tag> tags;
    @Column(length = 100)
    private String originTitle;
    @Column(length = 150)
    private String image;

    /**
     * 捆绑装订类型
     */
    private String binding;
    private String ebookUrl;
    private BigDecimal ebookPrice;
    @ElementCollection
    private List<String> translator;
    /**
     * 目录
     */
    @Lob
    private String catalog;
    /**
     * 页数
     */
    private Integer pages;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_images",
            joinColumns = {@JoinColumn(name = "book_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "images_id",
                    referencedColumnName = "id")})
    private Images images;
    @Column(length = 50)
    private String alt;
    @Column(length = 50)
    private String publisher;
    @Column(length = 10)
    private String isbn10;
    @Column(length = 13)
    private String isbn13;
    @Column(length = 50)
    private String title;
    @Column(name = "[url]", length = 150)
    private String url;
    @Column(length = 100)
    private String altTitle;
    @Column(length = 500)
    private String authorIntro;
    @Column(length = 2000)
    private String summary;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_series",
            joinColumns = {
                    @JoinColumn(
                            name = "book_id",
                            referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "series_id",
                            referencedColumnName = "id")})
    private Series series;
    private String price;

    public Book(String isbn13) {
        this.isbn13 = isbn13;
    }

    public Book() {
    }

    public Book(Integer bookId) {
        this.id = bookId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", rating=" + rating +
                ", subtitle='" + subtitle + '\'' +
                ", author=" + author +
                ", pubdate='" + pubdate + '\'' +
                ", tags=" + tags +
                ", originTitle='" + originTitle + '\'' +
                ", image='" + image + '\'' +
                ", binding='" + binding + '\'' +
                ", ebookUrl='" + ebookUrl + '\'' +
                ", ebookPrice=" + ebookPrice +
                ", translator=" + translator +
                ", catalog='" + catalog + '\'' +
                ", pages=" + pages +
                ", images=" + images +
                ", alt='" + alt + '\'' +
                ", publisher='" + publisher + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", altTitle='" + altTitle + '\'' +
                ", authorIntro='" + authorIntro + '\'' +
                ", summary='" + summary + '\'' +
                ", series=" + series +
                ", price='" + price + '\'' +
                '}';
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating value) {
        this.rating = value;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String value) {
        this.subtitle = value;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> value) {
        this.tags = value;
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

    public List<String> getTranslator() {
        return translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
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

    public Images getImages() {
        return images;
    }

    public void setImages(Images value) {
        this.images = value;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String value) {
        this.alt = value;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String value) {
        this.publisher = value;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String value) {
        this.isbn10 = value;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String value) {
        this.isbn13 = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getAltTitle() {
        return altTitle;
    }

    public void setAltTitle(String value) {
        this.altTitle = value;
    }

    public String getAuthorIntro() {
        return authorIntro;
    }

    public void setAuthorIntro(String value) {
        this.authorIntro = value;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String value) {
        this.summary = value;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series value) {
        this.series = value;
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

    @Entity
    @Proxy(lazy=false)
    public static class Images {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String small;
        private String large;
        private String medium;
        private String localSmall;
        private String localMedium;
        private String localLarge;

        public Images(String small, String medium) {
            this.small = small;
            this.medium = medium;
        }

        public String getLocalSmall() {
            return localSmall;
        }

        @JsonIgnoreProperties
        public void setLocalSmall(String localSmall) {
            this.localSmall = localSmall;
        }

        public String getLocalMedium() {
            return localMedium;
        }

        @JsonIgnoreProperties
        public void setLocalMedium(String localMedium) {
            this.localMedium = localMedium;
        }

        public String getLocalLarge() {
            return localLarge;
        }

        @JsonIgnoreProperties
        public void setLocalLarge(String localLarge) {
            this.localLarge = localLarge;
        }

        public Images() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSmall() { return small; }
        public void setSmall(String value) { this.small = value; }

        public String getLarge() { return large; }
        public void setLarge(String value) { this.large = value; }

        public String getMedium() { return medium; }
        public void setMedium(String value) { this.medium = value; }
    }

    @Entity
    @Proxy(lazy=false)
    public static class Rating {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private Integer max;
        private Integer numRaters;
        private String average;
        private Integer min;
        public Rating() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer value) {
            this.max = value;
        }

        public Integer getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(Integer value) {
            this.numRaters = value;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String value) {
            this.average = value;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer value) {
            this.min = value;
        }
    }

    @Entity
    @Proxy(lazy=false)
    public static class Tag {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private long count;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Tag() {
        }

        @Column(length = 50)
        private String name;

        @Column(length = 50)
        private String title;

        public long getCount() { return count; }
        public void setCount(long value) { this.count = value; }

        public String getName() { return name; }
        public void setName(String value) { this.name = value; }

        public String getTitle() { return title; }
        public void setTitle(String value) { this.title = value; }
    }

    @Entity
    @Proxy(lazy=false)
    public static class Series {
        @Id
        private Integer id;

        @Column(length = 50)
        private String title;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Series() {
        }

        public String getTitle() { return title; }
        public void setTitle(String value) { this.title = value; }
    }
}


