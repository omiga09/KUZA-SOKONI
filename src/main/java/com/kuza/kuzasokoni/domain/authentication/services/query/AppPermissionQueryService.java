package com.kuza.kuzasokoni.domain.authentication.services.query;


import com.kuza.kuzasokoni.domain.authentication.dto.command.PermissionDto;
import com.kuza.kuzasokoni.domain.authentication.entity.AppPermission;
import com.kuza.kuzasokoni.domain.authentication.exception.ResourceNotFoundException;
import com.kuza.kuzasokoni.domain.authentication.repositories.PermissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AppPermissionQueryService {

    private final PermissionRepository repo;

    public List<PermissionDto> getAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PermissionDto getById(Long id) {
        AppPermission perm = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found"));
        return toDto(perm);
    }

    // ====== Private helper ======
    private PermissionDto toDto(AppPermission perm) {
        PermissionDto dto = new PermissionDto();
        dto.setId(perm.getId());
        dto.setName(perm.getName());
        dto.setMethod(perm.getMethod());
        perm.setEndpoint(dto.getEndpoint());
        dto.setDescription(perm.getDescription());
        return dto;
    }

}
