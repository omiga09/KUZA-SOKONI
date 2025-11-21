package com.kuza.kuzasokoni.domain.authentication.services.query;

import com.kuza.kuzasokoni.domain.authentication.dto.command.PermissionDto;
import com.kuza.kuzasokoni.domain.authentication.dto.command.RoleDto;
import com.kuza.kuzasokoni.domain.authentication.entity.AppPermission;
import com.kuza.kuzasokoni.domain.authentication.entity.Role;
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
public class RoleQueryService {


    private final RoleRepository roleRepo;
    private final PermissionRepository permRepo;
    private final ModelMapper mapper;
    private final UserEntityRepository userEntityRepository;


    public List<RoleDto> getAllRoles() {
        return roleRepo.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public RoleDto getRoleById(Long id) {
        Role role = roleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        return toDto(role);
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
