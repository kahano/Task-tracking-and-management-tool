package com.kahano.Task.tracking.and.management.tool.Services.Impl;

import com.kahano.Task.tracking.and.management.tool.Repository.TaskSetRepository;
import com.kahano.Task.tracking.and.management.tool.Services.TaskSetService;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskSetServiceImpl implements TaskSetService {
    @Autowired
    private TaskSetRepository taskSetRepository;


    @Override
    public List<TaskSet> getAllTaskSet() {

        return taskSetRepository.findAll();
    }

    @Override
    public TaskSet getTaskSetByTaskId(UUID id) { // get a taskList by searching for a TaskId that is contained in a taskList
        Optional<TaskSet> taskList =  taskSetRepository.findTaskSetByTaskId(id);
        return taskList.orElse(null);
    }

    @Override
    public TaskSet createTaskSet(TaskSet taskSet) {
        if(taskSet.getId() != null) {
            throw new IllegalArgumentException("Task list has already an ID");

        }
        if(taskSet.getTitle().isEmpty() || taskSet.getTitle().isBlank()){
            throw new IllegalArgumentException("Task must have a title");
        }

        LocalDateTime now = LocalDateTime.now();

        return taskSetRepository.save(new TaskSet(
                null,
                taskSet.getTitle(),
                taskSet.getDescription(),
                now,
                now,
                null
        ));
    }

    @Override
    public TaskSet updateTaskSet(UUID taskSetId, TaskSet taskSet) {

        if(taskSet.getId() == null){
            throw new IllegalArgumentException("TaskList must have an ID");
        }

        if(!Objects.equals(taskSet.getId().toString(), taskSetId.toString())){
            throw new IllegalArgumentException("TaskList ID is not valid");
        }
         TaskSet existing_taskList = taskSetRepository.findById(taskSetId)
                 .orElseThrow(() -> new IllegalStateException("TaskSet is not found !"));

            existing_taskList.setTitle(taskSet.getTitle());
            existing_taskList.setDescription(taskSet.getDescription());
        existing_taskList.setTasks(taskSet.getTasks());
        existing_taskList.setUpdated(LocalDateTime.now());
        return taskSetRepository.save(existing_taskList);





    }

    @Override
    public Optional<TaskSet> getTaskSet(UUID id) {
        return taskSetRepository.findById(id);

    }
}
