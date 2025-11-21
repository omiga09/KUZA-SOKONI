package com.kuza.kuzasokoni.domain.authentication.services.command;


import com.kuza.kuzasokoni.domain.authentication.dto.command.PermissionDto;
import com.kuza.kuzasokoni.domain.authentication.entity.AppPermission;
import com.kuza.kuzasokoni.domain.authentication.exception.ResourceNotFoundException;
import com.kuza.kuzasokoni.domain.authentication.repositories.PermissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AppPermissionCommandService {

    private final PermissionRepository repo;

    public PermissionDto create(PermissionDto dto) {
        AppPermission perm = new AppPermission();
        perm.setName(dto.getName());
        perm.setMethod(dto.getMethod());
        perm.setEndpoint(dto.getEndpoint());
        perm.setDescription(dto.getDescription());
        repo.save(perm);
        dto.setId(perm.getId());
        return dto;
    }

    public PermissionDto update(Long id, PermissionDto dto) {
        AppPermission perm = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found"));
        perm.setName(dto.getName());
        perm.setMethod(dto.getMethod());
        perm.setEndpoint(dto.getEndpoint());
        perm.setDescription(dto.getDescription());
        repo.save(perm);
        return toDto(perm);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Permission not found");
        }
        repo.deleteById(id);
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
