package cn.boosp.sharebook.common.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
            @JoinTable(name = "payment_order",
            joinColumns={
                    @JoinColumn(name = "payment_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "order_id",
                    referencedColumnName = "id")})
    Order order;

    BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    Date payTime;
}
