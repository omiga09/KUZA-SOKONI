package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.common.utils.PageUtil;
import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import com.kuza.kuzasokoni.domain.product.exception.ProductNotFoundException;
import com.kuza.kuzasokoni.domain.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    @Override
    public PageResponse<ProductView> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ProductView> result = productRepository.findAllProductViews(pageable);
        return PageUtil.build(result);
    }


    @Override
    public ProductView getProductById(Long id) {
        Product product = productRepository.findProductWithDetails(id);
        if (product == null) throw new ProductNotFoundException(id);
        return toProductView(product);
    }

    // Helper method to manually map Product → ProductView
    private ProductView toProductView(Product product) {
        return new ProductView() {
            @Override
            public Long getId() { return product.getId(); }
            @Override
            public String getProductName() { return product.getProductName(); }
            @Override
            public String getShortName() { return product.getShortName(); }
            @Override
            public com.kuza.kuzasokoni.domain.product.enums.InterestMethod getInterestMethod() { return product.getInterestMethod(); }
            @Override
            public com.kuza.kuzasokoni.domain.product.enums.RepaymentFrequency getRepaidEvery() { return product.getRepaidEvery(); }
            @Override
            public com.kuza.kuzasokoni.domain.product.enums.Currency getCurrency() { return product.getCurrency(); }
            @Override
            public com.kuza.kuzasokoni.domain.product.enums.ProductStatus getStatus() { return product.getStatus(); }
            @Override
            public java.math.BigDecimal getInterestMin() { return product.getInterestMin(); }
            @Override
            public java.math.BigDecimal getInterestMax() { return product.getInterestMax(); }
            @Override
            public Integer getOverdue_days() { return product.getOverdue_days(); }
            @Override
            public Integer getNpa_days() { return product.getNpa_days(); }

            @Override
            public List<ChargeView> getCharges() {
                return product.getCharges().stream().map(c -> new ChargeView() {
                    @Override public Long getId() { return c.getId(); }
                    @Override public String getName() { return c.getName(); }
                    @Override public java.math.BigDecimal getAmount() { return c.getAmount(); }
                    @Override public String getRepaymentType() {
                        return c.getRepaymentType() != null ? c.getRepaymentType().name() : null;
                    }

                    @Override public String getCollectedOn() {
                        return c.getCollectedOn() != null ? c.getCollectedOn().name() : null;
                    }

                    @Override public String getDeductedOn() {
                        return c.getDeductedOn() != null ? c.getDeductedOn().name() : null;
                    }

                }).collect(Collectors.toList());
            }

            @Override
            public List<TenureView> getTenures() {
                return product.getTenures().stream().map(t -> new TenureView() {
                    @Override public Long getId() { return t.getId(); }
                    @Override public Integer getNumberOfDays() { return t.getNumberOfDays(); }
                    @Override public java.math.BigDecimal getInterest() { return t.getInterest(); }
                    @Override public Integer getPhase1Days() { return t.getPhase1Days(); }
                    @Override public Integer getPhase2Days() { return t.getPhase2Days(); }
                    @Override public Integer getPhase3Days() { return t.getPhase3Days(); }
                    @Override public Integer getPhase4Days() { return t.getPhase4Days(); }
                    @Override public java.math.BigDecimal getPenaltyGroupOne() { return t.getPenaltyGroupOne(); }
                    @Override public java.math.BigDecimal getPenaltyGroupTwo() { return t.getPenaltyGroupTwo(); }
                    @Override public java.math.BigDecimal getPenaltyGroupThree() { return t.getPenaltyGroupThree(); }
                    @Override public java.math.BigDecimal getPenaltyGroupFour() { return t.getPenaltyGroupFour(); }
                    @Override
                    public BigDecimal getPenaltyCap() {
                        return t.getPenaltyCap() != null ? BigDecimal.valueOf(t.getPenaltyCap()) : null;
                    }
                    @Override public Integer getGracePeriodDays() { return t.getGracePeriodDays(); }
                }).collect(Collectors.toList());
            }
        };
    }
}
