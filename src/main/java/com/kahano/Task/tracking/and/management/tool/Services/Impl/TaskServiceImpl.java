package com.kahano.Task.tracking.and.management.tool.Services.Impl;

import com.kahano.Task.tracking.and.management.tool.Repository.TaskRespository;
import com.kahano.Task.tracking.and.management.tool.Repository.TaskSetRepository;
import com.kahano.Task.tracking.and.management.tool.Services.TaskService;
import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskPriority;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRespository taskRepository;

    private final TaskSetRepository taskSetRepository;
    public TaskServiceImpl(TaskRespository taskRepository, TaskSetRepository taskSetRepository) {
        this.taskRepository = taskRepository;


        this.taskSetRepository = taskSetRepository;
    }

    @Override
    public List<Task> getAllTask(UUID taskSetId) {
        return taskRepository.findByTaskSetId(taskSetId);
    }

    @Override
    public Task getTaskById(UUID taskSetId, UUID taskId) {
        Optional<Task> task = taskRepository.findByTaskSetIdAndTaskId(taskSetId, taskId);
        return task.isPresent() ? task.get() : null;

    }

    @Override
    public Task createTask(UUID taskListId, Task task) {

        if (null != task.getId()) {
            throw new IllegalArgumentException("Task already has ID!");
        }
        if (null == task.getTitle() || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task must have a title");
        }
        TaskPriority priority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);
        TaskSet taskList = taskSetRepository
                .findById(taskListId)
                .orElseThrow(()->
                        new IllegalArgumentException("Invalid Task List ID provided")
                );
        LocalDateTime now = LocalDateTime.now();
        return taskRepository.save(new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                priority,
                TaskStatus.OPEN,
                now,
                now,
                taskList
 ));
    }
}
