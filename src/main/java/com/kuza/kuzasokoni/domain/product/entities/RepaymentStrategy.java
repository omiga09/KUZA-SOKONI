package com.kuza.kuzasokoni.domain.product.entities;


import com.kuza.kuzasokoni.common.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "repayment_strategies", indexes = {
        @Index(name = "idx_strategy_name", columnList = "name")
})
public class RepaymentStrategy extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean includePrincipal;
    private Boolean includeInterest;
    private Boolean includePenalty;
    private Boolean includeFees;
    private Boolean includeCharges;


}
