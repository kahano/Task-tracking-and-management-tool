package com.kahano.Task.tracking.and.management.tool.Mapper;

import com.kahano.Task.tracking.and.management.tool.domain.DTO.TaskDTO;
import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Task_TaskDTO_mapper {
    LocalDateTime now = LocalDateTime.now();
    public static TaskDTO toTaskDTO( Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );

    }

    public static Task toTask( TaskDTO dto) {
        return new Task(
                dto.id(),
                dto.title(),
                dto.description(),
                dto.dueDate(),
                dto.priority(),
                dto.status(),
                null,
                null,
                null
        );
    }
}
