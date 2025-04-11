package com.kahano.Task.tracking.and.management.tool.Services;

import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    List<Task> getAllTask(UUID taskSetId);

    Task getTaskById(UUID taskSetId, UUID taskId);
}
