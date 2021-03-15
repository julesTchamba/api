package com.pnc.api.web.controller;

import com.pnc.api.dao.TransactionDao;
import com.pnc.api.model.CustomDate;
import com.pnc.api.model.Transaction;
import com.pnc.api.model.enums.TIME_TYPE;
import com.pnc.api.model.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionDao transactionDao;

    //add transaction to the H2 database
    @PostMapping(value = "/transactions")
    public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) throws ParseException {
        if(transaction == null)
            return ResponseEntity.noContent().build();
        String timeStamp = transaction.getTransactionTs();
        Date date = CustomDate.getDate(timeStamp,
                TIME_TYPE.DATE);
        Date time = CustomDate.getDate(timeStamp,
                TIME_TYPE.TIME);
        Date dateTime = CustomDate.getDate(timeStamp,
                TIME_TYPE.DATE_TIME);
        int yearMonth = CustomDate.getYearMonth(timeStamp,false);

        transaction.setTransactionDate(date);
        transaction.setTransactionTime(time);
        transaction.setTransactionDateTime(dateTime);
        transaction.setYearMonth(yearMonth);

        Transaction transactionAdded =  transactionDao.save(transaction);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{accountNumber}")
                .buildAndExpand(transactionAdded.getAccountNumber())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //get all transactions
    @GetMapping(value = "/transactions/{acc}")
    public List<Transaction> transactions(@PathVariable String acc) {
        return transactionDao.findByAccountNumber(acc);
    }

    @GetMapping(value = "/transactions/{accountNumber}/{type}")
    public List<Transaction> transactions(@PathVariable String accountNumber,@PathVariable TransactionType type) {
        return transactionDao.findByAccountNumberAndType(accountNumber,type);
    }

    @GetMapping(value = "/transactions/{accountNumber}/today")
    public List<Transaction> getTransactionsAcc(@PathVariable String accountNumber) {
        return transactionDao.findAllByTransactionDateAndAccountNumber(new Date(),accountNumber);
    }

    @GetMapping(value = "/transactions/{acc}/today/{type}")
    public List<Transaction> getTransactions(@PathVariable String acc,@PathVariable TransactionType type) {
        return transactionDao.findAllByTransactionDateAndAccountNumberAndType(new Date(),acc,type);
    }

    @GetMapping(value = "/transactions/{acc}/last/{numberOfDays}")
    public List<Transaction> getTransactions(@PathVariable String acc,@PathVariable int numberOfDays)  {
        LocalDate localDate = LocalDate.now().minusDays(numberOfDays+1);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return transactionDao.findByTransactionDateGreaterThanAndAccountNumber(date,acc);
    }

    @GetMapping(value = "/transactions/{acc}/last/{numberOfDays}/{type}")
    public List<Transaction> getTransactions(@PathVariable String acc,@PathVariable int numberOfDays,
                                             @PathVariable TransactionType type)  {
        LocalDate localDate = LocalDate.now().minusDays(numberOfDays+1);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return transactionDao.findByTransactionDateGreaterThanAndAccountNumberAndType(date,acc,type);
    }

    @GetMapping(value = "/transactions/{acc}/lastmonth")
    public List<Transaction> getAllLastMonthTransactions(@PathVariable String acc) {
        Instant timestamp = Timestamp.valueOf(LocalDate.now().atStartOfDay()).toInstant();
        int lastMonth  = CustomDate.getYearMonth(timestamp.toString(),true);
        return  transactionDao.findAllByYearMonthAndAccountNumber(lastMonth,acc);
    }

    @GetMapping(value = "/transactions/{acc}/lastmonth/{type}")
    public List<Transaction> getAllLastMonthTransactions(@PathVariable String acc,@PathVariable  TransactionType type) {
        Instant timestamp = Timestamp.valueOf(LocalDate.now().atStartOfDay()).toInstant();
        int lastMonth  = CustomDate.getYearMonth(timestamp.toString(),true);
        return  transactionDao.findAllByYearMonthAndAccountNumberAndType(lastMonth,acc,type);
    }

    @GetMapping("/transactions/{acc}/fetchAll/{startDate}/{endDate}")
    public List<Transaction> getAllTransactionBetweenDate(@PathVariable String acc, @PathVariable(value = "startDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date fromDate,
                                                          @PathVariable(value = "endDate")
                                                          @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                  Date toDate) {
        return transactionDao.findAllByTransactionDateBetweenAndAccountNumber(fromDate,toDate,acc);
    }

    @GetMapping("/transactions/{acc}/fetchAll/{startDate}/{endDate}/{type}")
    public List<Transaction> getAllTransactionBetweenDateAndType(
            @PathVariable String acc,@PathVariable(value = "startDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date fromDate,
            @PathVariable(value = "endDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
                    Date toDate,
            @PathVariable
                    TransactionType type) {
        return transactionDao.findAllByTransactionDateBetweenAndAccountNumberAndType(fromDate,toDate,acc,type);
    }

}