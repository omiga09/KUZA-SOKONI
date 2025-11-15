package com.kuza.kuzasokoni.domain.loan.mappers;

import com.kuza.kuzasokoni.domain.loan.dtos.command.LoanInitiateCommand;
import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.enums.LoanStatus;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import com.kuza.kuzasokoni.domain.product.repositories.ProductRepository;
import com.kuza.kuzasokoni.domain.loan.repositories.RepaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoanCommandMapperImpl implements LoanCommandMapper {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @Override
    public Loan toInitiatedLoan(LoanInitiateCommand cmd) {
        Loan loan = new Loan();
        loan.setStatus(LoanStatus.PENDING);
        loan.setPrincipal(cmd.getPrincipal());
        loan.setCollateral(cmd.getCollateral());
        loan.setCollateralAmount(cmd.getCollateralAmount());
        loan.setAnnualInterestRate(cmd.getAnnualInterestRate());
        loan.setInterestMethod(cmd.getInterestMethod());
        loan.setNumberOfInstallments(cmd.getNumberOfInstallments());


        if (cmd.getClientId() != null) {
            loan.setClient(clientRepository.getReferenceById(cmd.getClientId()));
        }

        if (cmd.getProductId() != null) {
            loan.setProduct(productRepository.getReferenceById(cmd.getProductId()));
        }


        return loan;
    }
}


