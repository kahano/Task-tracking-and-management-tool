package com.kahano.Task.tracking.and.management.tool.domain.DTO;

import java.util.List;
import java.util.UUID;

public record TaskSetDTO(

        UUID id,
        String title,
        String Description,
        Integer count, Double progress, List<TaskDTO> tasks
) {
}
