package com.kuza.kuzasokoni.domain.loan.repositories;

import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.dtos.query.LoanRepaymentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("""
    SELECT l.id AS id,
           l.principal AS principal,
           l.outstanding AS outstanding,
           l.interest AS interest,
           CONCAT(c.firstName, ' ', c.secondName, ' ', c.lastName) AS clientName
    FROM Loan l
    JOIN l.client c
    WHERE l.status = 'DISBURSED'
""")
    Page<LoanRepaymentView> findAllRepaymentViews(Pageable pageable);


    @Query("""
    SELECT l.id AS id,
           l.outstanding AS outstanding,
           l.principal AS principal,
           l.interest AS interest,
           CONCAT(c.firstName, ' ', c.secondName, ' ', c.lastName) AS clientName
    FROM Loan l
    JOIN l.client c
    WHERE l.id = :loanId
""")
    Optional<LoanRepaymentView> findRepaymentViewById(@Param("loanId") Long loanId);

    @Query("SELECT l FROM Loan l JOIN FETCH l.repaymentSchedules")
    Page<Loan> findAllWithSchedules(Pageable pageable);

    @Query("""
    SELECT l.id AS id,
           l.principal AS principal,
           l.outstanding AS outstanding,
           l.interest AS interest,
           CONCAT(c.firstName, ' ', c.secondName, ' ', c.lastName) AS clientName
    FROM Loan l
    JOIN l.client c
    WHERE l.status = 'DISBURSED'
      AND (
            LOWER(CONCAT(c.firstName, ' ', c.secondName, ' ', c.lastName)) LIKE LOWER(CONCAT('%', :q, '%'))
            OR CAST(l.id AS string) LIKE %:q%
            OR CAST(l.principal AS string) LIKE %:q%
            OR CAST(l.outstanding AS string) LIKE %:q%
      )
""")
    Page<LoanRepaymentView> searchLoanRepaymentViews(@Param("q") String q, Pageable pageable);

}
