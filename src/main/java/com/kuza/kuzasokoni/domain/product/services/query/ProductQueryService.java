package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductQueryService {
    PageResponse<ProductView> getAllProducts(int page, int size, String sortBy, String sortDir);
    ProductView getProductById(Long id);
}
