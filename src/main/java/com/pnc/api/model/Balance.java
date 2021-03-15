package com.pnc.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "balance")
public class Balance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonIgnore
    private Long id;
    @Column(length=3)
    private String accountNumber;
    private Timestamp lastUpdateTimestamp;
    private BigDecimal balance;//for money, i could use org.javamoney dependency or BigDecimal

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Balance() {
    }
    public Balance(String accountNumber, Timestamp lastUpdateTimestamp, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.lastUpdateTimestamp = lastUpdateTimestamp;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(Timestamp lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


}