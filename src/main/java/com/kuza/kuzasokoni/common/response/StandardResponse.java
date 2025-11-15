package com.kuza.kuzasokoni.common.response;

import com.kuza.kuzasokoni.domain.client.dtos.query.DocumentationView;

public record StandardResponse<T>(
        int status,
        String message,
        String endpoint,
        T data
) {
    public static <T> StandardResponse<T> success(int status, String message, String endpoint, T data) {
        return new StandardResponse<>(status, message, endpoint, data);
    }

    public static StandardResponse<DocumentationView> error(int status, String message, String endpoint) {
        return new StandardResponse<>(status, message, endpoint, null);
    }
}
