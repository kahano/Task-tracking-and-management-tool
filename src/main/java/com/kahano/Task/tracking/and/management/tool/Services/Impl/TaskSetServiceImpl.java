package com.kahano.Task.tracking.and.management.tool.Services.Impl;

import com.kahano.Task.tracking.and.management.tool.Mapper.TaskSet_TaskSetDTO_mapper;
import com.kahano.Task.tracking.and.management.tool.Repository.TaskSetRepository;
import com.kahano.Task.tracking.and.management.tool.Services.TaskSetService;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return taskList.isPresent() ? taskList.get() : null;
    }

    @Override
    public TaskSet createTaskSet(TaskSet taskSet) {
        return taskSetRepository.save(taskSet);
    }
}
