package cn.boosp.sharebook.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;


/**
 * 从google api 获取的图书信息实体，豆瓣的api挂了，愤怒！
 */
@Entity
@Proxy(lazy = false)
public class Gbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String kind;
    private long totalItems;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "gbook_items",
            joinColumns = @JoinColumn(name = "gbook_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

    @Override
    public String toString() {
        return "Gbook{" +
                "id=" + id +
                ", kind='" + kind + '\'' +
                ", totalItems=" + totalItems +
                ", items=" + items +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String value) {
        this.kind = value;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long value) {
        this.totalItems = value;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Entity
    public static class Item {
        private String kind;
        @Id
        private String id;
        private String etag;
        private String selfLink;
        @OneToOne(cascade = CascadeType.PERSIST)
        @JoinTable(name = "item_volume_info",
                joinColumns = @JoinColumn(name = "item_id"),
                inverseJoinColumns = @JoinColumn(name = "volume_info_id")
        )
        private VolumeInfo volumeInfo;
        @OneToOne(cascade = CascadeType.PERSIST)
        @JoinTable(name = "item_sale_info",
                joinColumns = @JoinColumn(name = "item_id"),
                inverseJoinColumns = @JoinColumn(name = "save_info_id")
        )
        private SaleInfo saleInfo;
        @OneToOne(cascade = CascadeType.PERSIST)
        @JoinTable(name = "item_access_info",
                joinColumns = @JoinColumn(name = "item_id"),
                inverseJoinColumns = @JoinColumn(name = "access_info_id")
        )
        private AccessInfo accessInfo;

        @Override
        public String toString() {
            return "Item{" +
                    "kind='" + kind + '\'' +
                    ", id='" + id + '\'' +
                    ", etag='" + etag + '\'' +
                    ", selfLink='" + selfLink + '\'' +
                    ", volumeInfo=" + volumeInfo +
                    ", saleInfo=" + saleInfo +
                    ", accessInfo=" + accessInfo +
                    '}';
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String value) {
            this.kind = value;
        }

        public String getID() {
            return id;
        }

        public void setID(String value) {
            this.id = value;
        }

        public String getEtag() {
            return etag;
        }

        public void setEtag(String value) {
            this.etag = value;
        }

        public String getSelfLink() {
            return selfLink;
        }

        public void setSelfLink(String value) {
            this.selfLink = value;
        }

        public VolumeInfo getVolumeInfo() {
            return volumeInfo;
        }

        public void setVolumeInfo(VolumeInfo value) {
            this.volumeInfo = value;
        }

        public SaleInfo getSaleInfo() {
            return saleInfo;
        }

        public void setSaleInfo(SaleInfo value) {
            this.saleInfo = value;
        }

        public AccessInfo getAccessInfo() {
            return accessInfo;
        }

        public void setAccessInfo(AccessInfo value) {
            this.accessInfo = value;
        }
    }

// AccessInfo.java

    @Entity
    @Proxy(lazy = false)
    public static class AccessInfo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String country;
        private String viewability;
        private boolean embeddable;
        private boolean publicDomain;
        private String textToSpeechPermission;
        @OneToOne(cascade = CascadeType.PERSIST)
        @JoinTable(name = "access_info_epub",
                joinColumns = @JoinColumn(name = "access_info_id"),
                inverseJoinColumns = @JoinColumn(name = "epub_id")
        )
        private Epub epub;
        @OneToOne(cascade = CascadeType.PERSIST)
        @JoinTable(name = "access_info_pdf",
                joinColumns = @JoinColumn(name = "access_info_id"),
                inverseJoinColumns = @JoinColumn(name = "pdf_id")
        )
        private Epub pdf;
        private String webReaderLink;
        private String accessViewStatus;
        private boolean quoteSharingAllowed;

        @Override
        public String toString() {
            return "AccessInfo{" +
                    "id=" + id +
                    ", country='" + country + '\'' +
                    ", viewability='" + viewability + '\'' +
                    ", embeddable=" + embeddable +
                    ", publicDomain=" + publicDomain +
                    ", textToSpeechPermission='" + textToSpeechPermission + '\'' +
                    ", epub=" + epub +
                    ", pdf=" + pdf +
                    ", webReaderLink='" + webReaderLink + '\'' +
                    ", accessViewStatus='" + accessViewStatus + '\'' +
                    ", quoteSharingAllowed=" + quoteSharingAllowed +
                    '}';
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String value) {
            this.country = value;
        }

        public String getViewability() {
            return viewability;
        }

        public void setViewability(String value) {
            this.viewability = value;
        }

        public boolean getEmbeddable() {
            return embeddable;
        }

        public void setEmbeddable(boolean value) {
            this.embeddable = value;
        }

        public boolean getPublicDomain() {
            return publicDomain;
        }

        public void setPublicDomain(boolean value) {
            this.publicDomain = value;
        }

        public String getTextToSpeechPermission() {
            return textToSpeechPermission;
        }

        public void setTextToSpeechPermission(String value) {
            this.textToSpeechPermission = value;
        }

        public Epub getEpub() {
            return epub;
        }

        public void setEpub(Epub value) {
            this.epub = value;
        }

        public Epub getPDF() {
            return pdf;
        }

        public void setPDF(Epub value) {
            this.pdf = value;
        }

        public String getWebReaderLink() {
            return webReaderLink;
        }

        public void setWebReaderLink(String value) {
            this.webReaderLink = value;
        }

        public String getAccessViewStatus() {
            return accessViewStatus;
        }

        public void setAccessViewStatus(String value) {
            this.accessViewStatus = value;
        }

        public boolean getQuoteSharingAllowed() {
            return quoteSharingAllowed;
        }

        public void setQuoteSharingAllowed(boolean value) {
            this.quoteSharingAllowed = value;
        }
    }

// Epub.java

    @Entity
    @Proxy(lazy = false)
    public static class Epub {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private boolean isAvailable;

        @Override
        public String toString() {
            return "Epub{" +
                    "id=" + id +
                    ", isAvailable=" + isAvailable +
                    '}';
        }

        public boolean getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(boolean value) {
            this.isAvailable = value;
        }
    }

// SaleInfo.java

    @Entity
    @Proxy(lazy = false)
    public static class SaleInfo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String country;
        private String saleability;
        private boolean isEbook;

        @Override
        public String toString() {
            return "SaleInfo{" +
                    "id=" + id +
                    ", country='" + country + '\'' +
                    ", saleability='" + saleability + '\'' +
                    ", isEbook=" + isEbook +
                    '}';
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String value) {
            this.country = value;
        }

        public String getSaleability() {
            return saleability;
        }

        public void setSaleability(String value) {
            this.saleability = value;
        }

        public boolean getIsEbook() {
            return isEbook;
        }

        public void setIsEbook(boolean value) {
            this.isEbook = value;
        }
    }

// ImageLinks.java

    @Entity(name = "gbook$image_links")
    @Proxy(lazy = false)
    public static class ImageLinks {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String smallThumbnail;
        private String thumbnail;
        private String smallThumbnailLocal;
        private String thumbnailLocal;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSmallThumbnailLocal() {
            return smallThumbnailLocal;
        }

        @JsonIgnoreProperties
        public void setSmallThumbnailLocal(String smallThumbnailLocal) {
            this.smallThumbnailLocal = smallThumbnailLocal;
        }

        public String getThumbnailLocal() {
            return thumbnailLocal;
        }
        @JsonIgnoreProperties
        public void setThumbnailLocal(String thumbnailLocal) {
            this.thumbnailLocal = thumbnailLocal;
        }

        @Override
        public String toString() {
            return "ImageLinks{" +
                    "id=" + id +
                    ", smallThumbnail='" + smallThumbnail + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    '}';
        }

        public String getSmallThumbnail() {
            return smallThumbnail;
        }

        public void setSmallThumbnail(String value) {
            this.smallThumbnail = value;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String value) {
            this.thumbnail = value;
        }
    }

// VolumeInfo.java

    @Entity
    @Proxy(lazy = false)
    public static class VolumeInfo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String title;
        private String subtitle;
        @ElementCollection
        private List<String> authors;
        private String publishedDate;
        @Column(length = 4096)
        private String description;
        @OneToMany(cascade = CascadeType.PERSIST)
        @JoinTable(name = "volume_info_industry_identifier",
                joinColumns = @JoinColumn(name = "volume_info_id"),
                inverseJoinColumns = @JoinColumn(name = "industry_identifier_id"))
        private List<IndustryIdentifier> industryIdentifiers;
        @OneToOne(cascade = CascadeType.PERSIST)
        @JoinTable(name = "volume_info_reading_modes", joinColumns = @JoinColumn(name = "volume_info_id"),
                inverseJoinColumns = @JoinColumn(name = "reading_modes_id"))
        private ReadingModes readingModes;
        private long pageCount;
        private String printType;
        @ElementCollection
        private List<String> categories;
        private String maturityRating;
        private boolean allowAnonLogging;
        private String contentVersion;
        @OneToOne(cascade = CascadeType.PERSIST)
        @JoinTable(name = "volume_info_images_links",
                joinColumns = @JoinColumn(name = "volume_info_id"),
                inverseJoinColumns = @JoinColumn(name = "image_links_id")
        )
        private ImageLinks imageLinks;
        private String language;
        private String previewLink;
        private String infoLink;
        private String canonicalVolumeLink;
        @OneToOne(cascade = CascadeType.PERSIST)
        @JoinTable(name = "volume_info_panelization_summary",
                joinColumns = @JoinColumn(name = "access_info_id"),
                inverseJoinColumns = @JoinColumn(name = "panelization_summary_id")
        )
        private PanelizationSummary panelizationSummary;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "VolumeInfo{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", authors=" + authors +
                    ", publishedDate='" + publishedDate + '\'' +
                    ", industryIdentifiers=" + industryIdentifiers +
                    ", readingModes=" + readingModes +
                    ", pageCount=" + pageCount +
                    ", printType='" + printType + '\'' +
                    ", categories=" + categories +
                    ", maturityRating='" + maturityRating + '\'' +
                    ", allowAnonLogging=" + allowAnonLogging +
                    ", contentVersion='" + contentVersion + '\'' +
                    ", imageLinks=" + imageLinks +
                    ", language='" + language + '\'' +
                    ", previewLink='" + previewLink + '\'' +
                    ", infoLink='" + infoLink + '\'' +
                    ", canonicalVolumeLink='" + canonicalVolumeLink + '\'' +
                    ", panelizationSummary=" + panelizationSummary +
                    '}';
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String value) {
            this.title = value;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String value) {
            this.subtitle = value;
        }

        public List<String> getAuthors() {
            return authors;
        }

        public void setAuthors(List<String> authors) {
            this.authors = authors;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String value) {
            this.publishedDate = value;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<IndustryIdentifier> getIndustryIdentifiers() {
            return industryIdentifiers;
        }

        public void setIndustryIdentifiers(List<IndustryIdentifier> industryIdentifiers) {
            this.industryIdentifiers = industryIdentifiers;
        }

        public boolean isAllowAnonLogging() {
            return allowAnonLogging;
        }

        public ReadingModes getReadingModes() {
            return readingModes;
        }

        public void setReadingModes(ReadingModes value) {
            this.readingModes = value;
        }

        public long getPageCount() {
            return pageCount;
        }

        public void setPageCount(long value) {
            this.pageCount = value;
        }

        public String getPrintType() {
            return printType;
        }

        public void setPrintType(String value) {
            this.printType = value;
        }

        public List<String> getCategories() {
            return categories;
        }

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }

        public String getMaturityRating() {
            return maturityRating;
        }

        public void setMaturityRating(String value) {
            this.maturityRating = value;
        }

        public boolean getAllowAnonLogging() {
            return allowAnonLogging;
        }

        public void setAllowAnonLogging(boolean value) {
            this.allowAnonLogging = value;
        }

        public String getContentVersion() {
            return contentVersion;
        }

        public void setContentVersion(String value) {
            this.contentVersion = value;
        }

        public ImageLinks getImageLinks() {
            return imageLinks;
        }

        public void setImageLinks(ImageLinks value) {
            this.imageLinks = value;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String value) {
            this.language = value;
        }

        public String getPreviewLink() {
            return previewLink;
        }

        public void setPreviewLink(String value) {
            this.previewLink = value;
        }

        public String getInfoLink() {
            return infoLink;
        }

        public void setInfoLink(String value) {
            this.infoLink = value;
        }

        public String getCanonicalVolumeLink() {
            return canonicalVolumeLink;
        }

        public void setCanonicalVolumeLink(String value) {
            this.canonicalVolumeLink = value;
        }

        public PanelizationSummary getPanelizationSummary() {
            return panelizationSummary;
        }

        public void setPanelizationSummary(PanelizationSummary value) {
            this.panelizationSummary = value;
        }
    }


// IndustryIdentifier.java

    @Entity
    @Proxy(lazy = false)
    public static class IndustryIdentifier {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String type;
        private String identifier;
        @ManyToOne
        @JoinTable(name = "volume_info_industry_identifier",
                joinColumns = @JoinColumn(name = "industry_identifier_id"),
                inverseJoinColumns = @JoinColumn(name = "volume_info_id"))
        private VolumeInfo volumeInfo;

        public VolumeInfo getVolumeInfo() {
            return volumeInfo;
        }

        public void setVolumeInfo(VolumeInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "IndustryIdentifier{" +
                    "id=" + id +
                    ", type='" + type + '\'' +
                    ", identifier='" + identifier + '\'' +
                    '}';
        }

        public String getType() {
            return type;
        }

        public void setType(String value) {
            this.type = value;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String value) {
            this.identifier = value;
        }
    }

    @Entity
    @Proxy(lazy = false)
    public static class PanelizationSummary {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private boolean containsEpubBubbles;
        private boolean containsImageBubbles;

        @Override
        public String toString() {
            return "PanelizationSummary{" +
                    "id=" + id +
                    ", containsEpubBubbles=" + containsEpubBubbles +
                    ", containsImageBubbles=" + containsImageBubbles +
                    '}';
        }

        public boolean getContainsEpubBubbles() {
            return containsEpubBubbles;
        }

        public void setContainsEpubBubbles(boolean value) {
            this.containsEpubBubbles = value;
        }

        public boolean getContainsImageBubbles() {
            return containsImageBubbles;
        }

        public void setContainsImageBubbles(boolean value) {
            this.containsImageBubbles = value;
        }
    }

// ReadingModes.java

    @Entity
    @Proxy(lazy = false)
    public static class ReadingModes {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private boolean text;
        private boolean image;

        @Override
        public String toString() {
            return "ReadingModes{" +
                    "id=" + id +
                    ", text=" + text +
                    ", image=" + image +
                    '}';
        }

        public boolean getText() {
            return text;
        }

        public void setText(boolean value) {
            this.text = value;
        }

        public boolean getImage() {
            return image;
        }

        public void setImage(boolean value) {
            this.image = value;
        }
    }
}

// Item.java


