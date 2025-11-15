package com.kuza.kuzasokoni.domain.product.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kuza.kuzasokoni.common.audit.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "repayment_strategies", indexes = {
        @Index(name = "idx_strategy_name", columnList = "name")
})
public class RepaymentStrategy extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Strategy name is required")
    @Size(max = 100)
    @Column(nullable = false, unique = true)
    private String name;

    @Min(1) @Max(5)
    private Integer principal;

    @Min(1) @Max(5)
    private Integer interest;

    @Min(1) @Max(5)
    private Integer penalty;

    @Min(1) @Max(5)
    private Integer fees;

    @Min(1) @Max(5)
    private Integer charges;
}
