package com.kuza.kuzasokoni.domain.loan.repositories;


import com.kuza.kuzasokoni.domain.loan.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}