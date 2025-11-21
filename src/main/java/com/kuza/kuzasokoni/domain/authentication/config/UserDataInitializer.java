package com.kuza.kuzasokoni.domain.authentication.config;

import com.kuza.kuzasokoni.domain.authentication.entity.AppPermission;
import com.kuza.kuzasokoni.domain.authentication.entity.Role;
import com.kuza.kuzasokoni.domain.authentication.entity.User;
import com.kuza.kuzasokoni.domain.authentication.entity.UserEntity;
import com.kuza.kuzasokoni.domain.authentication.repositories.PermissionRepository;
import com.kuza.kuzasokoni.domain.authentication.repositories.RoleRepository;
import com.kuza.kuzasokoni.domain.authentication.repositories.UserEntityRepository;
import com.kuza.kuzasokoni.domain.authentication.repositories.UserRepository;
import com.kuza.kuzasokoni.common.utils.EntityType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserDataInitializer {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        // SAVE PERMISSIONS SAFELY
        List<String> permissionNames = List.of(
                "USER_MANAGEMENT", "ROLE_MANAGEMENT", "PERMISSION_MANAGEMENT",
                "PRODUCT_CREATE", "PRODUCT_VIEW", "PRODUCT_UPDATE", "PRODUCT_DELETE",
                "CHARGE_MANAGEMENT", "TENURE_MANAGEMENT", "REPAYMENT_STRATEGY_MANAGEMENT",
                "CLIENT_CREATE", "CLIENT_VIEW", "CLIENT_UPDATE", "CLIENT_VERIFY", "CLIENT_ACTIVATE",
                "LOAN_INITIATE", "LOAN_APPROVE", "LOAN_DISBURSE", "LOAN_RESTRUCTURE", "LOAN_CLOSE",
                "VIEW_ALL_LOANS", "VIEW_DASHBOARD", "GENERATE_REPORTS"
        );

        for (String permName : permissionNames) {
            permissionRepository.findByName(permName)
                    .orElseGet(() -> {
                        AppPermission p = new AppPermission();
                        p.setName(permName);
                        p.setMethod(null);
                        return permissionRepository.save(p);
                    });
        }

        // SUPER_ADMIN ROLE
        Role superAdminRole = roleRepository.findByName("SUPER_ADMIN")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("SUPER_ADMIN");
                    r.setEntityType(EntityType.MASTER);
                    r.setPermissions(Set.copyOf(permissionRepository.findAll()));
                    return roleRepository.save(r);
                });

        if (superAdminRole.getEntityType() == null) {
            superAdminRole.setEntityType(EntityType.MASTER);
            roleRepository.save(superAdminRole);
        }

        // ADMIN ROLE
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("ADMIN");
                    r.setEntityType(EntityType.MASTER);
                    r.setPermissions(Set.of(
                            permissionRepository.findByName("PRODUCT_CREATE").orElseThrow(),
                            permissionRepository.findByName("PRODUCT_VIEW").orElseThrow(),
                            permissionRepository.findByName("PRODUCT_UPDATE").orElseThrow()

                    ));
                    return roleRepository.save(r);
                });

        if (adminRole.getEntityType() == null) {
            adminRole.setEntityType(EntityType.MASTER);
            roleRepository.save(adminRole);
        }

        // CUSTOMER ROLE
        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("CUSTOMER");
                    r.setEntityType(EntityType.NON);
                    r.setPermissions(Set.of(
                            permissionRepository.findByName("PRODUCT_VIEW").orElseThrow()

                    ));
                    return roleRepository.save(r);
                });

        if (customerRole.getEntityType() == null) {
            customerRole.setEntityType(EntityType.NON);
            roleRepository.save(customerRole);
        }

        // SUPER ADMIN USER
        if (userRepository.findByUsername("superadmin").isEmpty()) {

            User superAdmin = new User();
            superAdmin.setUsername("superadmin");
            superAdmin.setEmail("super@kuza.com");
            superAdmin.setFirstName("Super");
            superAdmin.setSecondName("System");
            superAdmin.setLastName("Admin");
            superAdmin.setPhoneNumber("255700000001");
            superAdmin.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(superAdmin);

            UserEntity entity = new UserEntity();
            entity.setEntityType(EntityType.MASTER);
            entity.setEntityId(1L);
            entity.setRoles(Set.of(superAdminRole));
            entity.setUser(superAdmin);

            userEntityRepository.save(entity);
        }

    }

}
