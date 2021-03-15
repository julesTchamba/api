package com.pnc.api.web.controller;

import com.google.common.collect.Iterables;
import com.pnc.api.dao.BalanceDao;
import com.pnc.api.model.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
public class BalanceController {

    @Autowired
    private BalanceDao balanceDao;

    //get all balances
    @GetMapping(value = "/balances")
    public List<Balance> balances() {
        return balanceDao.findAll();
    }

    @GetMapping(value = "/balances/{accountNumber}")
    public BigDecimal latestbalances(@PathVariable String accountNumber) {
        Balance  balanceFound = Iterables.getLast(balanceDao.findByAccountNumber(accountNumber));
        if (balanceFound == null)
        {
            //Todo()  we have to manage the case the user send a wrong accountNumber or
            // if the accountNumber does not exists
            return BigDecimal.valueOf(-1.0);
        } else
            return balanceFound.getBalance();
    }


    //add balance
    @PostMapping(value = "/balances")
    public ResponseEntity<Void> addBalance(@RequestBody Balance balance) {
        Balance balanceAdded =  balanceDao.save(balance);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{accountNumber}")
                .buildAndExpand(balanceAdded.getAccountNumber())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}