package com.kuza.kuzasokoni.domain.authentication.controller.command;

import com.kuza.kuzasokoni.domain.authentication.dto.command.PermissionDto;
import com.kuza.kuzasokoni.domain.authentication.dto.command.RoleDto;
import com.kuza.kuzasokoni.domain.authentication.services.command.AppPermissionCommandService;
import com.kuza.kuzasokoni.domain.authentication.services.command.RoleCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCommandController {

    private final RoleCommandService roleService;
    private final AppPermissionCommandService permissionService;


    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/permissions")
    public ResponseEntity<PermissionDto> createPermission(@RequestBody PermissionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(permissionService.create(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/permissions/{id}")
    public ResponseEntity<PermissionDto> updatePermission(@PathVariable Long id,
                                                          @RequestBody PermissionDto dto) {
        return ResponseEntity.ok(permissionService.update(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.delete(id);
        return ResponseEntity.noContent().build();
    }


    // ====================== ROLES ======================


    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/roles")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.createRole(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/roles/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long id,
                                              @RequestBody RoleDto dto) {
        return ResponseEntity.ok(roleService.updateRole(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

}
