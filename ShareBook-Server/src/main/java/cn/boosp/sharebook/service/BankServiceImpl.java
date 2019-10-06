package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.pojo.Bank;
import cn.boosp.sharebook.common.pojo.User;
import cn.boosp.sharebook.common.pojo.Wallet;
import cn.boosp.sharebook.dao.Bank$TradingRecordRepository;
import cn.boosp.sharebook.dao.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    BankRepository bankRepository;
    @Autowired
    Bank$TradingRecordRepository bank$TradingRecordRepository;

    @Override
    public void add(BigDecimal money) {
        Optional<Bank> optionalBank = bankRepository.findById(1);
        if(!optionalBank.isPresent()){
            Bank bank = new Bank(1);
            bankRepository.save(bank);
        }else {
            Bank bank = optionalBank.get();
            bank.add(money);
        }
    }

    @Override
    public BigDecimal getAmount() {
        Optional<Bank> optionalBank = bankRepository.findById(1);
        if(!optionalBank.isPresent()){
            Bank bank = new Bank(1);
            bankRepository.save(bank);
            return BigDecimal.valueOf(0);
        }else {
            Bank bank = optionalBank.get();
            return bank.getAmount();
        }
    }

    @Override
    public boolean tranfer(User payer, User payee, BigDecimal money) {
        Wallet wallet = payer.getWallet();
        Wallet wallet2 = payee.getWallet();
        if(wallet.getBalance().compareTo(money) < 0){
            return false;
        }else {
            Object o = new Object();
            synchronized (o){
                wallet.reduce(money);
                wallet2.add(money);
            }
            Bank.TradingRecord record= new Bank.TradingRecord(payer,payee,money,new Date(System.currentTimeMillis()));
            bank$TradingRecordRepository.save(record);
            return true;
        }
    }
}
