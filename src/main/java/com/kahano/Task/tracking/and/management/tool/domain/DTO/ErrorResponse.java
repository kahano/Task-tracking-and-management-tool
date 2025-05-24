package com.kahano.Task.tracking.and.management.tool.domain.DTO;

public record ErrorResponse(
        int status,
        String message,
        String description
) {
}
