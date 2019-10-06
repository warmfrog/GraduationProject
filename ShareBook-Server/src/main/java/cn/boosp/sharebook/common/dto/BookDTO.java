package cn.boosp.sharebook.common.dto;

import cn.boosp.sharebook.common.pojo.Book;
import cn.boosp.sharebook.common.pojo.Gbook;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class BookDTO implements Serializable {
    Integer id;
    String rating;
    String authors;
    String pubdate;
    String tags;
    String title;
    String originTitle;
    String image;
    String ebookUrl;
    String binding;
    BigDecimal ebookPrice;
    String translator;
    String catalog;
    Integer pages;
    String publisher;
    String isbn13;
    String series;
    String price;
    String type;
    public BookDTO() {
    }
    public BookDTO(Book book) {
        this.id = book.getId();
        this.rating = book.getRating().getAverage();
        this.pubdate = book.getPubdate();
        this.title = book.getTitle();
        this.originTitle = book.getOriginTitle();
        this.image = book.getImage();
        this.ebookUrl = book.getEbookUrl();
        this.ebookPrice = book.getEbookPrice();
        this.catalog = book.getCatalog();
        this.pages = book.getPages();
        this.publisher = book.getPublisher();
        this.isbn13 = book.getIsbn13();
        this.series = book.getSeries() == null ? "" : book.getSeries().getTitle();
        this.price = book.getPrice();
        this.authors = getAuthors(book.getAuthor());
        this.tags = getTags(book.getTags());
        this.translator = getAuthors(book.getTranslator());
        this.type = "book";
    }

    public BookDTO(Gbook.VolumeInfo gbook) {
        this.id = gbook.getId();
        this.title = gbook.getTitle();
        this.authors = getAuthors(gbook.getAuthors());
        this.pages = Long.valueOf(gbook.getPageCount()).intValue();
        this.pubdate = gbook.getPublishedDate();
        this.isbn13 = gbook.getIndustryIdentifiers().get(1).getIdentifier();
        Gbook.ImageLinks imageLinks = gbook.getImageLinks();
        if (imageLinks != null) {
            this.image = imageLinks.getSmallThumbnail();
        }
        this.originTitle = title;
        this.type = "gbook";
        this.catalog = gbook.getDescription();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginTitle() {
        return originTitle;
    }

    public void setOriginTitle(String originTitle) {
        this.originTitle = originTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
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

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String getAuthors(List<String> itemList) {
        StringBuilder authorBuf = new StringBuilder();
        for (String item : itemList) {
            authorBuf.append(item + "-");
        }

        return new String(authorBuf);
    }

    private String getTags(List<Book.Tag> tags) {
        StringBuilder authorBuf = new StringBuilder();
        for (Book.Tag item : tags) {
            authorBuf.append(item.getName() + "-");
        }
        return new String(authorBuf);
    }
}
