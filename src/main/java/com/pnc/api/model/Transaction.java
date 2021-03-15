package com.pnc.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pnc.api.model.enums.TransactionType;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonIgnore
    private Long id;
    @Column(length=3)
    private String accountNumber;
    private String transactionTs;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private BigDecimal amount;//for money, i could use org.javamoney dependency
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    @Temporal(TemporalType.TIME)
    private  Date transactionTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDateTime;
    @Column(name = "yearMonth")
    private int yearMonth;

    public int getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(int yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Transaction() {
    }

    public Transaction(String accountNumber,
                       String transactionTs,
                       TransactionType type,
                       BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.transactionTs = transactionTs;
        this.type = type;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionTs() {
        return transactionTs;
    }

    public void setTransactionTs(String transactionTs) {
        this.transactionTs = transactionTs;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }
}
