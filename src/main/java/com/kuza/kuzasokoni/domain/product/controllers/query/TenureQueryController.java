package com.kuza.kuzasokoni.domain.product.controllers.query;


import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;
import com.kuza.kuzasokoni.domain.product.services.query.TenureQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenures/query")
@RequiredArgsConstructor
public class TenureQueryController {

    private final TenureQueryService tenureQueryService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public TenureCreateCommand getTenure(@PathVariable Long id) {
        return tenureQueryService.getTenure(id);
    }

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public PageResponse<TenureCreateCommand> getTenuresByProduct(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return tenureQueryService.getTenuresByProduct(productId, page, size);
    }
}

