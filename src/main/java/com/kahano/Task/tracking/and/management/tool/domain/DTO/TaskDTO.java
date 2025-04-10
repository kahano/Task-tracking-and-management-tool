package com.kahano.Task.tracking.and.management.tool.domain.DTO;

import java.time.LocalDate;
import java.util.UUID;

import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskPriority;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskStatus;

public record TaskDTO(
        UUID id,
        String title,
        String description,
        LocalDate dueDate,
        TaskPriority priority,
        TaskStatus status

) { }
