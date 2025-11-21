package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.common.utils.PageUtil;
import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;
import com.kuza.kuzasokoni.domain.product.repositories.ChargeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ChargeQueryServiceImpl implements ChargeQueryService {

    private final ChargeRepository repository;


    @Override
    public PageResponse<ChargeView> getAllCharges(int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ChargeView> result = repository.findAllProjected(pageable);
        return PageUtil.build(result);
    }


    @Override
    public Optional<ChargeView> getChargeById(Long id) {
        return repository.findProjectedById(id);
    }
}
