package com.kuza.kuzasokoni.domain.authentication.controller.command;

import com.kuza.kuzasokoni.common.enums.UserType;
import com.kuza.kuzasokoni.domain.authentication.dto.command.CreateUserEntityRequest;
import com.kuza.kuzasokoni.domain.authentication.entity.AppPermission;
import com.kuza.kuzasokoni.domain.authentication.entity.Role;
import com.kuza.kuzasokoni.domain.authentication.entity.User;
import com.kuza.kuzasokoni.domain.authentication.entity.UserEntity;
import com.kuza.kuzasokoni.domain.authentication.repositories.PermissionRepository;
import com.kuza.kuzasokoni.domain.authentication.repositories.RoleRepository;
import com.kuza.kuzasokoni.domain.authentication.repositories.UserEntityRepository;
import com.kuza.kuzasokoni.domain.authentication.repositories.UserRepository;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-entities")
@RequiredArgsConstructor
public class SuperAdminCommandController {

        private final UserRepository userRepository;
        private final UserEntityRepository userEntityRepository;
        private final RoleRepository roleRepository;
        private final PermissionRepository permissionRepository;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> createUserEntity(@RequestBody CreateUserEntityRequest request) {

        // Fetch roles
        Set<Role> roles = new HashSet<>();
        for (Long roleId : request.getRoles()) {
            Role r = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found with ID " + roleId));
            roles.add(r);
        }

        User user;

        if (request.getUserId() != null) {
            // Attach to existing user
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID " + request.getUserId()));
        } else {
            // Create new user
            String password = UUID.randomUUID().toString();
            user = User.builder()
                    .pin(password)
                    .password(password)
                    .passwordExpired(true)
                    .passwordExpiryDate(LocalDate.now())
                    .userType(request.getUserType())
                    .isVerified(VerificationStatus.VERIFIED)
                    .enabled(true)
                    .build();

            userRepository.saveAndFlush(user);


        }

        // Create UserEntity
        UserEntity entity = new UserEntity();
        entity.setEntityType(request.getEntityType());
        entity.setEntityId(request.getEntityId());
        entity.setUser(user);
        entity.setRoles(roles);
        userEntityRepository.save(entity);

        return ResponseEntity.ok("UserEntity created successfully");
    }


    @PutMapping("/{roleId}/permissions")
        @PreAuthorize("hasRole('SUPER_ADMIN')")
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
       @PreAuthorize("hasRole('SUPER_ADMIN')")
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


