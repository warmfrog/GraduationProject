package cn.booksp.sharebook.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private String status;
    private String id;

    private String customerUsername;

    private String type;
    private BigDecimal price;
    private Date createTime;
    private String owner;
    private Integer ubookId;

    public Order() {
    }

    public Integer getUbookId() {
        return ubookId;
    }

    public void setUbookId(Integer ubookId) {
        this.ubookId = ubookId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
