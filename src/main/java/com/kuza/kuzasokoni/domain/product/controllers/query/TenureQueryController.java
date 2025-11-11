package com.kuza.kuzasokoni.domain.product.controllers.query;


import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;
import com.kuza.kuzasokoni.domain.product.services.query.TenureQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tenures/query")
public class TenureQueryController {
    private final TenureQueryService tenureQueryService;

    public TenureQueryController(TenureQueryService tenureQueryService) {
        this.tenureQueryService = tenureQueryService;
    }

    @GetMapping("/{id}")
    public TenureCreateCommand getTenure(@PathVariable Long id) {
        return tenureQueryService.getTenure(id);
    }

    @GetMapping("/product/{productId}")
    public List<TenureCreateCommand> getTenuresByProduct(@PathVariable Long productId) {
        return tenureQueryService.getTenuresByProduct(productId);
    }
}
