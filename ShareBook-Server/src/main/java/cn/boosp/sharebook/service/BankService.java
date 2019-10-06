package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.pojo.User;

import java.math.BigDecimal;

public interface BankService {
    void add(BigDecimal money);
    BigDecimal getAmount();

    /**
     * 转账业务
     * @param payer 付款方
     * @param payee 收款方
     * @param money 金额
     * @return
     */
    boolean tranfer(User payer, User payee, BigDecimal money);
}
