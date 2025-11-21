package com.kuza.kuzasokoni.common.utils;

import com.kuza.kuzasokoni.common.response.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.IntStream;

public class PageUtil {

    public static <T> PageResponse<T> build(Page<T> page) {

        int totalPages = page.getTotalPages();
        int current = page.getNumber();
        int visible = 5;

        int start = Math.max(0, current - visible / 2);
        int end = Math.min(totalPages - 1, start + visible - 1);

        if (end - start + 1 < visible) {
            start = Math.max(0, end - visible + 1);
        }

        List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                .map(i -> i + 1)
                .boxed()
                .toList();

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                totalPages,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious(),
                pageNumbers
        );
    }
}
