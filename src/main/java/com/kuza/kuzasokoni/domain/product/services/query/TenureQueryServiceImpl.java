package com.kuza.kuzasokoni.domain.product.services.query;


import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Tenure;
import com.kuza.kuzasokoni.domain.product.mappers.TenureCommandMapper;
import com.kuza.kuzasokoni.domain.product.repositories.TenureRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TenureQueryServiceImpl implements TenureQueryService{


        private final TenureRepository tenureRepository;
        private final TenureCommandMapper tenureCommandMapper;

        public TenureQueryServiceImpl(TenureRepository tenureRepository,
                                      TenureCommandMapper tenureCommandMapper) {
            this.tenureRepository = tenureRepository;
            this.tenureCommandMapper = tenureCommandMapper;
        }

        @Override
        public TenureCreateCommand getTenure(Long id) {
            Tenure tenure = tenureRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tenure not found"));
            return tenureCommandMapper.toDto(tenure);
        }

        @Override
        public List<TenureCreateCommand> getTenuresByProduct(Long productId) {
            return tenureRepository.findByProductId(productId)
                    .stream()
                    .map(tenureCommandMapper::toDto)
                    .collect(Collectors.toList());
        }
    }


