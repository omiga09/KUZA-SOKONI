package com.kuza.kuzasokoni.common.authentication.config;

import com.kuza.kuzasokoni.common.authentication.entity.AppPermission;
import com.kuza.kuzasokoni.common.authentication.entity.Role;
import com.kuza.kuzasokoni.common.authentication.entity.User;
import com.kuza.kuzasokoni.common.authentication.entity.UserEntity;
import com.kuza.kuzasokoni.common.authentication.repositories.PermissionRepository;
import com.kuza.kuzasokoni.common.authentication.repositories.RoleRepository;
import com.kuza.kuzasokoni.common.authentication.repositories.UserEntityRepository;
import com.kuza.kuzasokoni.common.authentication.repositories.UserRepository;
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

        // 1️⃣ SAVE PERMISSIONS SAFELY
        List<String> permissionNames = List.of(
                "CREATE_PRODUCT", "VIEW_PRODUCT", "UPDATE_PRODUCT", "DELETE_PRODUCT",
                "CREATE_USER", "VIEW_USER", "UPDATE_USER", "DELETE_USER"
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

        // 2️⃣ ROLES
        Role superAdminRole = roleRepository.findByName("SUPER_ADMIN")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("SUPER_ADMIN");
                    r.setPermissions(Set.copyOf(permissionRepository.findAll()));
                    return roleRepository.save(r);
                });

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("ADMIN");
                    r.setPermissions(Set.of(
                            permissionRepository.findByName("CREATE_PRODUCT").orElseThrow(),
                            permissionRepository.findByName("VIEW_PRODUCT").orElseThrow(),
                            permissionRepository.findByName("UPDATE_PRODUCT").orElseThrow()
                    ));
                    return roleRepository.save(r);
                });

        roleRepository.findByName("CUSTOMER")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("CUSTOMER");
                    r.setPermissions(Set.of(
                            permissionRepository.findByName("VIEW_PRODUCT").orElseThrow()
                    ));
                    return roleRepository.save(r);
                });

        // 3️⃣ SUPER ADMIN USER
        if (userRepository.findByUsername("superadmin").isEmpty()) {

            User superAdmin = new User();
            superAdmin.setUsername("superadmin");
            superAdmin.setEmail("super@kuza.com");
            superAdmin.setPhoneNumber("255700000001");
            superAdmin.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(superAdmin);

            UserEntity entity = new UserEntity();
            entity.setEntityType(EntityType.SOKONI);
            entity.setEntityId(1L);
            entity.setRoles(Set.of(superAdminRole));
            entity.setUser(superAdmin);

            userEntityRepository.save(entity);
        }

    }

}

