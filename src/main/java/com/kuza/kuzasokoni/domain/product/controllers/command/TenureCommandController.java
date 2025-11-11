package com.kuza.kuzasokoni.domain.product.controllers.command;


import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.TenureUpdateCommand;
import com.kuza.kuzasokoni.domain.product.services.command.TenureCommandService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenures/command")
public class TenureCommandController {
    private final TenureCommandService tenureCommandService;

    public TenureCommandController(TenureCommandService tenureCommandService) {
        this.tenureCommandService = tenureCommandService;
    }

    @PostMapping
    public TenureCreateCommand createTenure(@RequestBody TenureCreateCommand cmd) {
        return tenureCommandService.createTenure(cmd);
    }

    @PutMapping("/{id}")
    public TenureUpdateCommand updateTenure(@PathVariable Long id,
                                            @RequestBody TenureUpdateCommand cmd) {
        cmd.setId(id);
        return tenureCommandService.updateTenure(cmd);
    }

    @DeleteMapping("/{id}")
    public void deleteTenure(@PathVariable Long id) {
        tenureCommandService.deleteTenure(id);
    }
}
