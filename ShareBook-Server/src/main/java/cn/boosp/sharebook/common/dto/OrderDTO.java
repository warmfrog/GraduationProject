package cn.boosp.sharebook.common.dto;

import cn.boosp.sharebook.common.pojo.Order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDTO {
    private String status;
    private String id;

    private String customerUsername;

    private String type;
    private BigDecimal price;
    private Date createTime;
    private String owner;
    private Integer ubookId;

    public OrderDTO() {
    }

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.customerUsername = order.getCustomer().getUsername();
        this.status = order.getStatus().name();
        this.owner = order.getUbook().getUser().getUsername();
        this.type = order.getType().name();
        this.ubookId = order.getUbook().getId();
        this.price = order.getPrice();
        this.createTime = order.getCreateTime();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
