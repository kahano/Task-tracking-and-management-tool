package com.kahano.Task.tracking.and.management.tool.Services;

import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    List<Task> getAllTask(UUID taskSetId);

    Optional<Task> getTaskById(UUID taskSetId, UUID taskId);

    @Transactional
    Task createTask(UUID taskListId, Task task);

    Task UpdateTask(UUID taskListId, UUID taskId, Task task);

    @Transactional
    void deleteTask(UUID taskSetId, UUID taskId);
}
