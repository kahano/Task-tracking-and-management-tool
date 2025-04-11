package com.kahano.Task.tracking.and.management.tool.Services;

import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<Task> getAllTasks(UUID taskSetId) ;

    Task findTaskById(UUID taskSetId, UUID taskId);

}
