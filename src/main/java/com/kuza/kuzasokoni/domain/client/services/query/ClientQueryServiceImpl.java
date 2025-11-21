package com.kuza.kuzasokoni.domain.client.services.query;


import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class ClientQueryServiceImpl implements ClientQueryService {

    private final ClientRepository clientRepository;

    @Override
    public PageResponse<ClientView> getAllClients(int page, int size, String sortBy, String sortDir) {
        int pageNumber = Math.max(0, page);
        int pageSize = Math.max(1, size);

        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isBlank()) {
            sort = "desc".equalsIgnoreCase(sortDir) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        }

        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<ClientView> pageResult = clientRepository.findAllClientViews(pageable);


        int totalPages = pageResult.getTotalPages();
        int current = pageResult.getNumber();
        int visible = 5;
        int start = Math.max(0, current - visible / 2);
        int end = Math.min(totalPages - 1, start + visible - 1);
        if (end - start + 1 < visible) {
            start = Math.max(0, end - visible + 1);
        }

        List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                .map(i -> i + 1) // convert to 1-based for UI
                .boxed()
                .collect(Collectors.toList());

        return new PageResponse<>(
                pageResult.getContent(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                totalPages,
                pageResult.isFirst(),
                pageResult.isLast(),
                pageResult.hasNext(),
                pageResult.hasPrevious(),
                pageNumbers
        );
    }

    @Override
    public ClientView getClientById(Long id) {
        return clientRepository.findClientViewById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    public PageResponse<ClientView> searchClients(String search, int page, int size) {

        int pageNumber = Math.max(0, page);
        int pageSize = Math.max(1, size);

        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        Page<ClientView> pageResult = clientRepository.searchClientViews(search, pageable);

        int totalPages = pageResult.getTotalPages();
        int current = pageResult.getNumber();
        int visible = 5;

        int start = Math.max(0, current - visible / 2);
        int end = Math.min(totalPages - 1, start + visible - 1);

        if (end - start + 1 < visible) {
            start = Math.max(0, end - visible + 1);
        }

        List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                .map(i -> i + 1)     // convert to 1-based
                .boxed()
                .collect(Collectors.toList());

        return new PageResponse<>(
                pageResult.getContent(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                totalPages,
                pageResult.isFirst(),
                pageResult.isLast(),
                pageResult.hasNext(),
                pageResult.hasPrevious(),
                pageNumbers
        );
    }

}


