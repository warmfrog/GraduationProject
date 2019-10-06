package cn.boosp.sharebook.common.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "[order]")
public class Order {
    @Enumerated(EnumType.STRING)
    OrderStatus status;
    Type type;
    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    @Column(length = 32)
    private String id;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "user_order",
            joinColumns = {
                    @JoinColumn(
                            name = "order_id")},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "customer_id")})
    private User customer;
//    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinTable(
//            name = "order_owner",
//            joinColumns = {
//                    @JoinColumn(
//                            name = "order_id",
//                            referencedColumnName = "id")},
//            inverseJoinColumns = {
//                    @JoinColumn(
//                            name = "owner_id",
//                            referencedColumnName = "id")})
//    private User owner;
    private BigDecimal price;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 每次记录借阅的书
     */
    @OneToOne
    @JoinTable(
            name = "order_ubook",
            joinColumns = {
                    @JoinColumn(
                            name = "order_id",
                            referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "ubook_id",
                            referencedColumnName = "id")})
    private Ubook ubook;

    public Order(User customer, Ubook ubook, BigDecimal price, String type) {
        this.customer = customer;
//        this.owner = ubook.getUser();
        this.ubook = ubook;
        this.status = OrderStatus.unpaid;
        this.price = price;
        this.createTime = new Date(System.currentTimeMillis());
        this.type = Type.valueOf(type);
    }

    public Order() {
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Ubook getUbook() {
        return ubook;
    }

    public void setUbook(Ubook ubook) {
        this.ubook = ubook;
    }

    public enum OrderStatus {
        unpaid("未支付"),
        paid("已支付"),
        finished("已完成"),
        closed("关闭");

        private String value;

        OrderStatus(String value) {
            this.value = value;
        }
    }

    public enum Type {
        sell("在售"), rent("在租");
        private String value;

        Type(String value) {
            this.value = value;
        }
    }
}
