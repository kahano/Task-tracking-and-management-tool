package com.kahano.Task.tracking.and.management.tool.Controllers;

import com.kahano.Task.tracking.and.management.tool.Mapper.Task_TaskDTO_mapper;
import com.kahano.Task.tracking.and.management.tool.Repository.TaskRespository;
import com.kahano.Task.tracking.and.management.tool.Services.TaskService;
import com.kahano.Task.tracking.and.management.tool.domain.DTO.TaskDTO;
import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path="/task/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final Task_TaskDTO_mapper taskMappper;
    private final TaskRespository taskRespository;

    public TaskController(TaskService taskService, Task_TaskDTO_mapper taskMappper, TaskRespository taskRespository) {
        this.taskService = taskService;
        this.taskMappper = taskMappper;
        this.taskRespository = taskRespository;
    }

    @PostMapping
    public TaskDTO createTask(@PathVariable("task_list_id") UUID taskSetId, @RequestBody TaskDTO taskDTO){

        Task task = taskService.createTask(taskSetId,taskMappper.toTask(taskDTO));
        return taskMappper.toTaskDTO(task);
    }

    @GetMapping("/{task_id}")
    public Optional<TaskDTO> getTask(@PathVariable("task_list_id") UUID taskSetId, @PathVariable("task_id") UUID taskId){

        Optional<Task> existing_task = taskService.getTaskById(taskSetId,taskId);
        return existing_task.map(taskMappper::toTaskDTO);
    }
    
    @GetMapping
    public List<TaskDTO> getAlltasks(@PathVariable("task_list_id") UUID taskSetId){
        List<Task> tasks = taskService.getAllTask(taskSetId);
        return tasks.stream().map(taskMappper::toTaskDTO).toList();
    }

    @PutMapping("/{task_id}")
    public TaskDTO updateTask(@PathVariable("task_list_id") UUID taskSetId, @PathVariable("task_id")
            UUID taskId, TaskDTO taskDTO){

        Task task = taskService.UpdateTask(taskSetId,taskId,taskMappper.toTask(taskDTO));
        return taskMappper.toTaskDTO(task);

    }

    @DeleteMapping("/{task_id}")
    public void DeleteTask(@PathVariable("task_list_id") UUID taskSetId, @PathVariable("task_id") UUID taskId){

        taskService.deleteTask(taskSetId,taskId);
    }






}
