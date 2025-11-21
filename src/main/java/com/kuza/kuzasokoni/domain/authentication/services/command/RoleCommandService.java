package com.kuza.kuzasokoni.domain.authentication.services.command;


import com.kuza.kuzasokoni.domain.authentication.dto.command.PermissionDto;
import com.kuza.kuzasokoni.domain.authentication.dto.command.RoleDto;
import com.kuza.kuzasokoni.domain.authentication.entity.AppPermission;
import com.kuza.kuzasokoni.domain.authentication.entity.Role;
import com.kuza.kuzasokoni.domain.authentication.entity.UserEntity;
import com.kuza.kuzasokoni.domain.authentication.exception.ResourceNotFoundException;
import com.kuza.kuzasokoni.domain.authentication.repositories.PermissionRepository;
import com.kuza.kuzasokoni.domain.authentication.repositories.RoleRepository;
import com.kuza.kuzasokoni.domain.authentication.repositories.UserEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleCommandService {

    private final RoleRepository roleRepo;
    private final PermissionRepository permRepo;
    private final ModelMapper mapper;
    private final UserEntityRepository userEntityRepository;

    public RoleDto createRole(RoleDto dto) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setEntityType(dto.getEntityType());
        role.setPermissions(resolvePermissions(dto.getPermissionIds()));
        roleRepo.save(role);
        return toDto(role);
    }

    public RoleDto updateRole(Long id, RoleDto dto) {
        Role role = roleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        role.setName(dto.getName());
        role.setEntityType(dto.getEntityType());
        role.getPermissions().clear();
        role.getPermissions().addAll(resolvePermissions(dto.getPermissionIds()));
        return toDto(role);
    }

    public void deleteRole(Long id) {
        Role role = roleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        // Find all UserEntities using this Role
        List<UserEntity> usersWithRole = userEntityRepository.findAllByRolesContaining(role);

        // Remove role from all these UserEntities
        for (UserEntity ue : usersWithRole) {
            ue.getRoles().remove(role);
        }
        userEntityRepository.saveAll(usersWithRole);

        // Now delete the role
        roleRepo.delete(role);
    }



    private Set<AppPermission> resolvePermissions(Set<Long> permIds) {
        if (permIds == null || permIds.isEmpty()) return Set.of();
        return new HashSet<>(permRepo.findAllById(permIds));
    }

    private RoleDto toDto(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setEntityType(role.getEntityType());
        dto.setPermissions(role.getPermissions().stream()
                .map(p -> mapper.map(p, PermissionDto.class))
                .collect(Collectors.toSet()));
        dto.setPermissionIds(role.getPermissions().stream()
                .map(AppPermission::getId)
                .collect(Collectors.toSet()));
        return dto;
    }
}
