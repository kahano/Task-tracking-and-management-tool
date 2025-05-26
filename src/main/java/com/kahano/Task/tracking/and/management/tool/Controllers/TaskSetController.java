package com.kahano.Task.tracking.and.management.tool.Controllers;

import com.kahano.Task.tracking.and.management.tool.Mapper.TaskSet_TaskSetDTO_mapper;
import com.kahano.Task.tracking.and.management.tool.Services.TaskSetService;
import com.kahano.Task.tracking.and.management.tool.domain.DTO.TaskSetDTO;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/task-set")
public class TaskSetController {

    private final TaskSet_TaskSetDTO_mapper taskSetMapper;

    private final TaskSetService taskSetService;

    public TaskSetController(TaskSet_TaskSetDTO_mapper taskSetMapper, TaskSetService taskSetService) {
        this.taskSetMapper = taskSetMapper;
        this.taskSetService = taskSetService;
    }

    @GetMapping
    public List<TaskSetDTO> getAllTaskSet(){
        List<TaskSet> tasks = taskSetService.getAllTaskSet();
        return tasks.stream().map(taskSetMapper::toTaskSetDTO).toList();
    }

    @PostMapping
    public TaskSetDTO createTaskSet(@RequestBody TaskSetDTO taskSetDTO){
        TaskSet taskSet = taskSetService.createTaskSet(taskSetMapper.toTaskSet(taskSetDTO));
        return taskSetMapper.toTaskSetDTO(taskSet);
    }

    @GetMapping("/{task_set_id}")
    public Optional<TaskSetDTO> getTaskSet(@PathVariable("task_set_id") UUID id){
        return taskSetService.getTaskSet(id).map(taskSetMapper::toTaskSetDTO);
    }

    @PutMapping("/{task_set_id}")
    public TaskSetDTO UpdateTaskSet(@PathVariable("task_set_id") UUID id, @RequestBody TaskSetDTO taskSetDTO){
        TaskSet taskSet = taskSetService.updateTaskSet(id,taskSetMapper.toTaskSet(taskSetDTO));
        return taskSetMapper.toTaskSetDTO(taskSet);
    }







}
