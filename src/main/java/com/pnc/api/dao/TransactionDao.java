package com.pnc.api.dao;

import com.pnc.api.model.Transaction;
import com.pnc.api.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Long> {
       List<Transaction> findByAccountNumber(String accountNumber);
       List<Transaction> findByAccountNumberAndType(String accountNumber,TransactionType type);

       List<Transaction> findAllByYearMonthAndAccountNumber(int yearMonth,String acc);
       List<Transaction> findAllByYearMonthAndAccountNumberAndType(int yearMonth,String acc, TransactionType type);

       List<Transaction> findAllByTransactionDateAndAccountNumber(Date transactionDate,String acc);
       List<Transaction> findAllByTransactionDateAndAccountNumberAndType(Date transactionDate,String acc,TransactionType type);

       List<Transaction> findByTransactionDateGreaterThanAndAccountNumber(Date transactionDate,String acc);
       List<Transaction> findByTransactionDateGreaterThanAndAccountNumberAndType(Date transactionDate,String acc,TransactionType type);

       List<Transaction> findAllByTransactionDateBetweenAndAccountNumber(Date transactionDateStart,Date transactionDateEnd,String acc);
       List<Transaction> findAllByTransactionDateBetweenAndAccountNumberAndType(Date transactionDateStart,Date transactionDateEnd,String acc,TransactionType type);
}