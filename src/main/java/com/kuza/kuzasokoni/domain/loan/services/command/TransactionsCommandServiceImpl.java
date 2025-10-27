package com.kuza.kuzasokoni.domain.loan.services.command;

import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.entities.Transaction;
import com.kuza.kuzasokoni.domain.loan.enums.LoanStatus;
import com.kuza.kuzasokoni.domain.loan.enums.TransactionDirection;
import com.kuza.kuzasokoni.domain.loan.enums.TransactionEntity;
import com.kuza.kuzasokoni.domain.loan.enums.TransactionType;
import com.kuza.kuzasokoni.domain.loan.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransactionsCommandServiceImpl implements TransactionsCommandService {

    private final TransactionRepository transactionRepository;

    @Override
    public void logDisbursement(Loan loan, BigDecimal amount) {
        Transaction tx = new Transaction();
        tx.setLoan(loan);
        tx.setAmount(amount);
        tx.setType(TransactionType.DISBURSEMENT);
        tx.setDirection(TransactionDirection.TO);
        tx.setDate(LocalDate.now());
        tx.setOnEntity(TransactionEntity.PRINCIPAL);
        tx.setInitiator("system");
        transactionRepository.save(tx);
    }

    @Override
    public void logCharges(Loan loan, BigDecimal penalty, BigDecimal fees, BigDecimal interest, BigDecimal charges) {
        if (penalty != null && penalty.compareTo(BigDecimal.ZERO) > 0) {
            Transaction tx = new Transaction(loan, penalty, TransactionType.COLLECTION, TransactionDirection.TO, LocalDate.now(), TransactionEntity.PENALTY);
            tx.setInitiator("system");
            transactionRepository.save(tx);
        }
        if (fees != null && fees.compareTo(BigDecimal.ZERO) > 0) {
            Transaction tx = new Transaction(loan, fees, TransactionType.COLLECTION, TransactionDirection.TO, LocalDate.now(), TransactionEntity.FEES);
            tx.setInitiator("system");
            transactionRepository.save(tx);
        }
        if (interest != null && interest.compareTo(BigDecimal.ZERO) > 0) {
            Transaction tx = new Transaction(loan, interest, TransactionType.COLLECTION, TransactionDirection.TO, LocalDate.now(), TransactionEntity.INTEREST);
            tx.setInitiator("system");
            transactionRepository.save(tx);
        }
        if (charges != null && charges.compareTo(BigDecimal.ZERO) > 0) {
            Transaction tx = new Transaction(loan, charges, TransactionType.COLLECTION, TransactionDirection.TO, LocalDate.now(), TransactionEntity.CHARGES);
            tx.setInitiator("system");
            transactionRepository.save(tx);
        }
    }

    @Override
    public void logRestructure(Loan loan) {
        Transaction tx = new Transaction();
        tx.setLoan(loan);
        tx.setAmount(BigDecimal.ZERO);
        tx.setType(TransactionType.RESTRUCTURE);
        tx.setDirection(TransactionDirection.TO);
        tx.setDate(LocalDate.now());
        tx.setOnEntity(TransactionEntity.PRINCIPAL);
        tx.setInitiator("system");
        transactionRepository.save(tx);
    }

    @Override
    public void logClosure(Loan loan, LoanStatus status) {
        Transaction tx = new Transaction();
        tx.setLoan(loan);
        tx.setAmount(BigDecimal.ZERO);
        tx.setType(TransactionType.REVERSED);
        tx.setDirection(TransactionDirection.TO);
        tx.setDate(LocalDate.now());
        tx.setOnEntity(TransactionEntity.PRINCIPAL);
        tx.setInitiator("system");
        transactionRepository.save(tx);
    }
}
