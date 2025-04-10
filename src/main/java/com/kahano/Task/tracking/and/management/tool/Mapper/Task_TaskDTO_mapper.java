package com.kahano.Task.tracking.and.management.tool.Mapper;

import com.kahano.Task.tracking.and.management.tool.domain.DTO.TaskDTO;
import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import org.springframework.stereotype.Component;

@Component
public class Task_TaskDTO_mapper {
    public TaskDTO toTaskDTO( Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );

    }

    public Task toTask( TaskDTO dto) {
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
