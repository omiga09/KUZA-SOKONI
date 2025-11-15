package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.repositories.LoanRepository;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargeCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;
import com.kuza.kuzasokoni.domain.product.entities.Charge;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import com.kuza.kuzasokoni.domain.product.enums.CollectedOn;
import com.kuza.kuzasokoni.domain.product.exception.ChargeNotFoundException;
import com.kuza.kuzasokoni.domain.product.mappers.ChargeCommandMapper;
import com.kuza.kuzasokoni.domain.product.repositories.ChargeRepository;
import com.kuza.kuzasokoni.domain.product.repositories.ProductRepository;
import com.kuza.kuzasokoni.domain.product.services.query.ChargeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ChargeCommandServiceImpl implements ChargeCommandService {

    private final ChargeRepository chargeRepository;
    private final ProductRepository productRepository;
    private final ChargeCommandMapper cMapper;
    private final ChargeQueryService chargeQueryService;
    private final LoanRepository loanRepository;

    @Override
    public ChargeView createCharge(ChargeCreateCommand cmd) {
        Product product = productRepository.findById(cmd.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Loan loan = loanRepository.findById(cmd.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Charge charge = cMapper.toEntity(cmd, product);
        charge.setProduct(product);
        charge.setIsPaid(false);
        charge.setRemainingAmount(charge.getAmount());

        // dueDate logic ikitumia Loan
        if (cmd.getDueDate() != null) {
            charge.setDueDate(cmd.getDueDate());
        } else if (cmd.getCollectedOn() == CollectedOn.INSTALLMENT) {
            charge.setDueDate(loan.getApprovalDate().plusDays(loan.getGracePeriodDays()));
        } else if (cmd.getCollectedOn() == CollectedOn.DISBURSEMENT) {
            charge.setDueDate(loan.getDisbursedDate());
        }

        Charge saved = chargeRepository.save(charge);

        return chargeRepository.findProjectedById(saved.getId())
                .orElseThrow(() -> new RuntimeException("Charge projection not found"));
    }


    @Override
    public ChargeView updatePaymentStatus(Long chargeId, BigDecimal paidAmount) {
        Charge charge = chargeRepository.findById(chargeId)
                .orElseThrow(() -> new ChargeNotFoundException(chargeId));

        charge.setPaidAmount(paidAmount);

        if (paidAmount != null && paidAmount.compareTo(charge.getAmount()) >= 0) {
            charge.setIsPaid(true);
            charge.setRemainingAmount(BigDecimal.ZERO);
        } else {
            charge.setIsPaid(false);
            BigDecimal remaining = charge.getAmount().subtract(paidAmount != null ? paidAmount : BigDecimal.ZERO);
            charge.setRemainingAmount(remaining);
        }

        Charge updated = chargeRepository.save(charge);
        return chargeQueryService.getChargeById(updated.getId())
                .orElseThrow(() -> new RuntimeException("Charge projection not found"));
    }
}


