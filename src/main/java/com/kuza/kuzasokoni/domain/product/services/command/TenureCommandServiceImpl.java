package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.TenureUpdateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import com.kuza.kuzasokoni.domain.product.entities.Tenure;
import com.kuza.kuzasokoni.domain.product.mappers.TenureCommandMapper;
import com.kuza.kuzasokoni.domain.product.repositories.ProductRepository;
import com.kuza.kuzasokoni.domain.product.repositories.TenureRepository;
import com.kuza.kuzasokoni.domain.product.services.command.TenureCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TenureCommandServiceImpl implements TenureCommandService {

    private final TenureRepository tenureRepository;
    private final ProductRepository productRepository;
    private final TenureCommandMapper tenureCommandMapper;

    public TenureCommandServiceImpl(TenureRepository tenureRepository,
                                    ProductRepository productRepository,
                                    TenureCommandMapper tenureCommandMapper) {
        this.tenureRepository = tenureRepository;
        this.productRepository = productRepository;
        this.tenureCommandMapper = tenureCommandMapper;
    }

    @Override
    public TenureCreateCommand createTenure(TenureCreateCommand cmd) {
        Product product = productRepository.findById(cmd.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Tenure tenure = tenureCommandMapper.toEntity(cmd, product);
        Tenure saved = tenureRepository.save(tenure);

        return tenureCommandMapper.toDto(saved);
    }

    @Override
    public TenureUpdateCommand updateTenure(TenureUpdateCommand cmd) {
        Tenure tenure = tenureRepository.findById(cmd.getId())
                .orElseThrow(() -> new RuntimeException("Tenure not found"));

        Product product = productRepository.findById(cmd.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Map update command to entity
        Tenure updated = tenureCommandMapper.toEntity(cmd, product);
        updated.setId(cmd.getId());

        Tenure saved = tenureRepository.save(updated);

        // Return the same update command (or you could map back if needed)
        TenureUpdateCommand response = new TenureUpdateCommand();
        response.setId(saved.getId());
        response.setPenaltyGroupOne(saved.getPenaltyGroupOne());
        response.setPenaltyGroupTwo(saved.getPenaltyGroupTwo());
        response.setPenaltyGroupThree(saved.getPenaltyGroupThree());
        response.setPenaltyGroupFour(saved.getPenaltyGroupFour());
        response.setPhase1Days(saved.getPhase1Days());
        response.setPhase2Days(saved.getPhase2Days());
        response.setPhase3Days(saved.getPhase3Days());
        response.setPhase4Days(saved.getPhase4Days());
        response.setPenaltyCap(saved.getPenaltyCap());
        response.setGracePeriodDays(saved.getGracePeriodDays());
        response.setInterest(saved.getInterest());
        response.setNumberOfDays(saved.getNumberOfDays());
        response.setProductId(saved.getProduct().getId());

        return response;
    }

    @Override
    public void deleteTenure(Long id) {
        tenureRepository.deleteById(id);
    }
}

