package com.kuza.kuzasokoni.domain.product.controllers.query;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;
import com.kuza.kuzasokoni.domain.product.services.query.ChargeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/charges")
@RequiredArgsConstructor
public class ChargeQueryController {

    private final ChargeQueryService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'LOAN_OFFICER', 'FINANCE')")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        return ResponseEntity.ok(
                StandardResponse.success(
                        200,
                        "Fetched successfully",
                        "/api/charges",
                        service.getAllCharges(page, size, sortBy, sortDir)
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'LOAN_OFFICER', 'FINANCE')")
    public ResponseEntity<StandardResponse<ChargeView>> getById(@PathVariable Long id) {
        return service.getChargeById(id)
                .map(charge -> ResponseEntity.ok(
                        StandardResponse.success(200, "Charge fetched successfully", "/api/charges/" + id, charge)
                ))
                .orElse(ResponseEntity.status(404).body(
                        StandardResponse.success(404, "Charge not found", "/api/charges/" + id, null)
                ));
    }
}
