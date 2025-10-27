package com.kuza.kuzasokoni.domain.client.services.command;

import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.entities.Documentation;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ClientDocumentationServiceImpl implements ClientDocumentationService {

    private final ClientRepository clientRepository;
    private final FileStorageService fileStorageService;

    @Override
    public Documentation uploadDocumentation(Long clientId, MultipartFile baruaFile, MultipartFile kitambulishoFile, String nidaNumber, String kitambulishoType) {

        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("Client ID must be greater than 0");
        }


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


        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));


        String baruaFileName = fileStorageService.store(baruaFile);
        String kitambulishoFileName = fileStorageService.store(kitambulishoFile);


        Documentation documentation = Documentation.builder()
                .nidaNumber(nidaNumber.trim())
                .kitambulishoType(kitambulishoType.trim())
                .kitambulishoFileName(kitambulishoFileName)
                .baruaFileName(baruaFileName)
                .client(client)
                .build();

        client.setDocumentation(documentation);
        Client savedClient = clientRepository.save(client);

        return savedClient.getDocumentation();
    }
}
