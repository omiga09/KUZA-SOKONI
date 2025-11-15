package com.kuza.kuzasokoni.common.authentication.controller;

import com.kuza.kuzasokoni.common.authentication.Dto.CreateUserEntityRequest;
import com.kuza.kuzasokoni.common.authentication.entity.AppPermission;
import com.kuza.kuzasokoni.common.authentication.entity.Role;
import com.kuza.kuzasokoni.common.authentication.entity.User;
import com.kuza.kuzasokoni.common.authentication.entity.UserEntity;
import com.kuza.kuzasokoni.common.authentication.repositories.PermissionRepository;
import com.kuza.kuzasokoni.common.authentication.repositories.RoleRepository;
import com.kuza.kuzasokoni.common.authentication.repositories.UserEntityRepository;
import com.kuza.kuzasokoni.common.authentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user-entities")
@RequiredArgsConstructor
public class SuperAdminController {

        private final UserRepository userRepository;
        private final UserEntityRepository userEntityRepository;
        private final RoleRepository roleRepository;
        private final PermissionRepository permissionRepository;

        @PostMapping
        @PreAuthorize("hasAuthority('CREATE_USER')") // Super Admin tu
        public ResponseEntity<?> createUserEntity(@RequestBody CreateUserEntityRequest request) {

            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Role role = roleRepository.findByName(request.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            UserEntity entity = new UserEntity();
            entity.setEntityType(request.getEntityType());
            entity.setEntityId(request.getEntityId());
            entity.setUser(user);
            entity.setRoles(Set.of(role));

            userEntityRepository.save(entity);

            return ResponseEntity.ok("UserEntity created successfully");
        }

    // RoleController.java
    @PutMapping("/{roleId}/permissions")
    @PreAuthorize("hasAuthority('UPDATE_USER')") // au permission mpya
    public ResponseEntity<?> assignPermissionsToRole(
            @PathVariable Long roleId,
            @RequestBody Set<String> permissionNames) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Set<AppPermission> permissions = permissionNames.stream()
                .map(name -> permissionRepository.findByName(name)
                        .orElseThrow(() -> new RuntimeException("Permission not found: " + name)))
                .collect(Collectors.toSet());

        role.getPermissions().addAll(permissions);
        roleRepository.save(role);

        return ResponseEntity.ok("Permissions assigned to role");
    }

    @PutMapping("/{entityId}/roles")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public ResponseEntity<?> assignRoleToUserEntity(
            @PathVariable Long entityId,
            @RequestBody Set<String> roleNames) {

        UserEntity userEntity = userEntityRepository.findById(entityId)
                .orElseThrow(() -> new RuntimeException("UserEntity not found"));

        Set<Role> roles = roleNames.stream()
                .map(name -> roleRepository.findByName(name)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + name)))
                .collect(Collectors.toSet());

        userEntity.setRoles(roles);
        userEntityRepository.save(userEntity);

        return ResponseEntity.ok("Roles assigned to UserEntity");
    }
}


