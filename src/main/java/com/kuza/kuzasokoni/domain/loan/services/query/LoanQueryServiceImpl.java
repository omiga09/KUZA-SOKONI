package com.kuza.kuzasokoni.domain.loan.services.query;

import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.domain.loan.dtos.query.LoanRepaymentView;
import com.kuza.kuzasokoni.domain.loan.exceptions.LoanNotFoundException;
import com.kuza.kuzasokoni.domain.loan.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LoanQueryServiceImpl implements LoanQueryService {

    private final LoanRepository loanRepository;

    @Override
    public PageResponse<LoanRepaymentView> getAllLoans(int page, int size, String sortBy, String sortDir) {

        int pageNumber = Math.max(0, page);
        int pageSize = Math.max(1, size);

        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isBlank()) {
            sort = "desc".equalsIgnoreCase(sortDir)
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
        }

        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<LoanRepaymentView> pageResult = loanRepository.findAllRepaymentViews(pageable);

        int totalPages = pageResult.getTotalPages();
        int current = pageResult.getNumber();
        int visible = 5;

        int start = Math.max(0, current - visible / 2);
        int end = Math.min(totalPages - 1, start + visible - 1);

        if (end - start + 1 < visible) {
            start = Math.max(0, end - visible + 1);
        }

        List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                .map(p -> p + 1)
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
    public LoanRepaymentView getLoanById(Long loanId) {
        return loanRepository.findRepaymentViewById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(loanId));
    }


    @Override
    public PageResponse<LoanRepaymentView> searchLoans(String q, int page, int size, String sortBy, String sortDir) {

        Pageable pageable = PageRequest.of(page, size,
                "desc".equalsIgnoreCase(sortDir)
                        ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending()
        );

        Page<LoanRepaymentView> result = loanRepository.searchLoanRepaymentViews(q, pageable);

        return buildPageResponse(result);
    }

    private PageResponse<LoanRepaymentView> buildPageResponse(Page<LoanRepaymentView> pageResult) {

        int totalPages = pageResult.getTotalPages();
        int current = pageResult.getNumber();

        int visible = 5;
        int start = Math.max(0, current - visible / 2);
        int end = Math.min(totalPages - 1, start + visible - 1);

        if (end - start + 1 < visible) {
            start = Math.max(0, end - visible + 1);
        }

        List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                .map(i -> i + 1)
                .boxed()
                .collect(Collectors.toList());

        return new PageResponse<>(
                pageResult.getContent(),
                current,
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


