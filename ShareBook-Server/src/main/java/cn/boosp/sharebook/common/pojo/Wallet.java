package cn.boosp.sharebook.common.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 用户钱包
 */
@Entity
public class Wallet {
    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid-generator")
    @Column(length = 32)
    private String id;
    private BigDecimal balance;

    public Wallet(){
        balance = BigDecimal.valueOf(2);
    }

    public Wallet(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public synchronized BigDecimal add(BigDecimal money) {
        this.balance = balance.add(money);
        return this.balance;
    }

    public synchronized BigDecimal reduce(BigDecimal money) {
        this.balance = balance.subtract(money);
        return this.balance;
    }
}
