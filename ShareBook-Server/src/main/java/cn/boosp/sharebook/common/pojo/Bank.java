package cn.boosp.sharebook.common.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Bank {
    BigDecimal amount;
    @Id
    private Integer id;

    public Bank() {
    }

    public Bank(Integer id) {
        this.id = id;
        this.amount = BigDecimal.valueOf(0);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        amount = amount;
    }

    public BigDecimal add(BigDecimal money) {
        amount = amount.add(money);
        return amount;
    }

    public BigDecimal subtract(BigDecimal money) {
        amount = amount.subtract(money);
        return amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 交易记录
     */
    @Entity
    public static class TradingRecord {
        @Id
        @GenericGenerator(name = "uuid-generator", strategy = "uuid")
        @GeneratedValue(generator = "uuid-generator")
        @Column(length = 32)
        private String id;
        @Temporal(TemporalType.TIMESTAMP)
        private Date time;
        @ManyToOne(cascade = CascadeType.ALL)
        @JoinTable(
                name = "trading_record_payer",
                joinColumns = @JoinColumn(name = "trading_record_id"),
                inverseJoinColumns = @JoinColumn(name = "payer_id"))
        private User payer;
        @ManyToOne(cascade = CascadeType.ALL)
        @JoinTable(
                name = "trading_record_payee",
                joinColumns = @JoinColumn(name = "trading_record_id"),
                inverseJoinColumns = @JoinColumn(name = "payee_id"))
        private User payee;
        private BigDecimal money;

        public TradingRecord(User payer, User payee, BigDecimal money, Date time) {
            this.time = time;
            this.payer = payer;
            this.payee = payee;
            this.money = money;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public User getPayer() {
            return payer;
        }

        public void setPayer(User payer) {
            this.payer = payer;
        }

        public User getPayee() {
            return payee;
        }

        public void setPayee(User payee) {
            this.payee = payee;
        }

        public BigDecimal getMoney() {
            return money;
        }

        public void setMoney(BigDecimal money) {
            this.money = money;
        }
    }
}
