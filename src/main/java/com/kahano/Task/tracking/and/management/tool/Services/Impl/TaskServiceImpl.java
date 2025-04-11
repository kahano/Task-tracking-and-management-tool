package com.kahano.Task.tracking.and.management.tool.Services.Impl;

import com.kahano.Task.tracking.and.management.tool.Repository.TaskRespository;
import com.kahano.Task.tracking.and.management.tool.Repository.TaskSetRepository;
import com.kahano.Task.tracking.and.management.tool.Services.TaskService;
import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRespository taskRepository;


    public TaskServiceImpl(TaskRespository taskRepository) {
        this.taskRepository = taskRepository;

    }



    @Override
    public List<Task> getAllTasks(UUID taskSetId) {
        return taskRepository.findByTaskSetId(taskSetId);
    }

    @Override
    public Task findTaskById(UUID taskSetId, UUID taskId) {
        Optional<Task> task = taskRepository.findByTaskSetIdAndTaskId(taskSetId,taskId);
        return task.isPresent() ? task.get() : null;
    }


}
