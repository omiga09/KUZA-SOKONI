package com.kuza.kuzasokoni.domain.client.services.command;

import com.kuza.kuzasokoni.common.audit.Images;
import com.kuza.kuzasokoni.common.repository.ImagesRepository;
import com.kuza.kuzasokoni.common.utils.EntityType;
import com.kuza.kuzasokoni.domain.client.dtos.query.DocumentationView;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.entities.Documentation;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientDocumentationServiceImpl implements ClientDocumentationService {

    private final ClientRepository clientRepository;
    private final FileStorageService fileStorageService;
    private final ClientRepository clientRepo;
    private final ImagesRepository imageRepo;

    @Override
    public DocumentationView uploadDocumentation(Long clientId, MultipartFile baruaFile, MultipartFile kitambulishoFile, String nidaNumber, String kitambulishoType) {
        Client client = clientRepo.findById(clientId).orElseThrow(() ->
                new ClientNotFoundException(clientId));

        validateUploadInformation(baruaFile, kitambulishoFile, nidaNumber, kitambulishoType);

        String baruaPath = fileStorageService.saveFile(baruaFile, "images");
        String kitambulishoPath = fileStorageService.saveFile(kitambulishoFile, "images");

        Documentation documentation = Documentation.builder()
                .nidaNumber(nidaNumber.trim())
                .kitambulishoType(kitambulishoType.trim())
                .kitambulishoFileName(kitambulishoPath)
                .baruaFileName(baruaPath)
                .client(client)
                .build();

        List<Images> images = new ArrayList<>();
        images.add(Images.of(baruaPath, baruaFile.getOriginalFilename(), EntityType.SOKONI, client.getId()));
        images.add(Images.of(kitambulishoPath, kitambulishoFile.getOriginalFilename(), EntityType.SOKONI, client.getId()));
        imageRepo.saveAllAndFlush(images);

        client.setDocumentation(documentation);
        clientRepository.save(client);

        return clientRepository.findDocumentationViewByClientId(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
    }

    @Override
    public DocumentationView updateDocumentation(Long clientId, MultipartFile baruaFile, MultipartFile kitambulishoFile, String nidaNumber, String kitambulishoType) {
        Client client = clientRepo.findById(clientId).orElseThrow(() ->
                new ClientNotFoundException(clientId));

        validateUploadInformation(baruaFile, kitambulishoFile, nidaNumber, kitambulishoType);

        String baruaPath = fileStorageService.saveFile(baruaFile, "images");
        String kitambulishoPath = fileStorageService.saveFile(kitambulishoFile, "images");

        Documentation documentation = Documentation.builder()
                .nidaNumber(nidaNumber.trim())
                .kitambulishoType(kitambulishoType.trim())
                .kitambulishoFileName(kitambulishoPath)
                .baruaFileName(baruaPath)
                .client(client)
                .build();

        client.setDocumentation(documentation);
        clientRepository.save(client);

        return clientRepository.findDocumentationViewByClientId(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
    }

    private void validateUploadInformation(MultipartFile baruaFile, MultipartFile kitambulishoFile, String nidaNumber, String kitambulishoType) {
        if (baruaFile == null || baruaFile.isEmpty()) {
            throw new IllegalArgumentException("Barua file is required and must not be empty");
        }

        if (kitambulishoFile == null || kitambulishoFile.isEmpty()) {
            throw new IllegalArgumentException("Kitambulisho file is required and must not be empty");
        }

        if (!StringUtils.hasText(nidaNumber)) {
            throw new IllegalArgumentException("NIDA number is required");
        }

        if (!StringUtils.hasText(kitambulishoType)) {
            throw new IllegalArgumentException("Kitambulisho type is required");
        }
    }
}


