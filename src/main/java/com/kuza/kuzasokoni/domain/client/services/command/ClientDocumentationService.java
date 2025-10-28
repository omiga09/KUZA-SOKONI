package com.kuza.kuzasokoni.domain.client.services.command;

import com.kuza.kuzasokoni.domain.client.dtos.query.DocumentationView;
import com.kuza.kuzasokoni.domain.client.entities.Documentation;
import org.springframework.web.multipart.MultipartFile;

public interface ClientDocumentationService {
    DocumentationView uploadDocumentation(Long clientId, MultipartFile baruaFile, MultipartFile kitambulishoFile, String nidaNumber, String kitambulishoType);
    DocumentationView updateDocumentation(Long clientId, MultipartFile baruaFile, MultipartFile kitambulishoFile, String nidaNumber, String kitambulishoType);

}

