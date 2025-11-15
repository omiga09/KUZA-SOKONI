package com.kuza.kuzasokoni.domain.loan.entities;

import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.domain.loan.enums.TransactionDirection;
import com.kuza.kuzasokoni.domain.loan.enums.TransactionEntity;
import com.kuza.kuzasokoni.domain.loan.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions", indexes = {
        @Index(name = "idx_transaction_loan", columnList = "loan_id"),
        @Index(name = "idx_transaction_type", columnList = "type"),
        @Index(name = "idx_transaction_date", columnList = "date")
})
public class Transaction extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private LocalDate date;
    private String initiator;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionDirection direction;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(name = "reversed_id")
    private Long reversedId;

    @Enumerated(EnumType.STRING)
    private TransactionEntity onEntity;

    public Transaction(
            Loan loan,
            BigDecimal penalty,
            TransactionType transactionType,
            TransactionDirection transactionDirection,
            LocalDate now,
            TransactionEntity transactionEntity) {
        super();
    }
}
