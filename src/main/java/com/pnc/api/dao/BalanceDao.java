package com.pnc.api.dao;

import com.pnc.api.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceDao extends JpaRepository<Balance, Long> {
    Iterable<Balance> findByAccountNumber(String accountNumber);
}