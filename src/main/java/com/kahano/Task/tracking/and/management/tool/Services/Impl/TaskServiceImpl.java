package com.kahano.Task.tracking.and.management.tool.Services.Impl;

import com.kahano.Task.tracking.and.management.tool.Repository.TaskRespository;
import com.kahano.Task.tracking.and.management.tool.Repository.TaskSetRepository;
import com.kahano.Task.tracking.and.management.tool.Services.TaskService;
import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskPriority;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    public Optional<Task> getTaskById(UUID taskSetId, UUID taskId) {
        return taskRepository.findByTaskSetIdAndTaskId(taskSetId, taskId);

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
                        new IllegalArgumentException("Invalid TaskSet ID provided")
                );
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();
        return taskRepository.save(new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                today,
                priority,
                TaskStatus.OPEN,
                now,now,
                taskList
 ));
    }

    @Transactional
    @Override
    public Task UpdateTask(UUID taskListId, UUID taskId, Task task) {
        if(task.getId() == null){
            throw new IllegalArgumentException("Task mus have an ID");
        }

        if(!Objects.equals(taskId,task.getId())){
            throw new IllegalArgumentException("Given TaskID's do not match");
        }

        if(task.getPriority()==null){
            throw new IllegalArgumentException("Task must have a valid Priority");
        }
        if(task.getStatus()==null){
            throw new IllegalArgumentException("Task must have a valid status");
        }

        Task updated_task = taskRepository.findByTaskSetIdAndTaskId(taskListId,taskListId)
                .orElseThrow(() -> new IllegalStateException("Task not found"));

        updated_task.setTitle(task.getTitle());
        updated_task.setDescription(task.getDescription());
        updated_task.setDueDate(task.getDueDate());
        updated_task.setPriority(task.getPriority());
        updated_task.setStatus(task.getStatus());
        updated_task.setUpdated(LocalDateTime.now());

        return taskRepository.save(updated_task);


    }


    @Override
    public void deleteTask(UUID taskSetId, UUID taskId) {
        TaskSet taskSet = taskSetRepository.findById(taskSetId).orElseThrow(()->
                new RuntimeException("TaskSet is not found "));

        Task taskToDelete = taskSet.getTasks().stream().filter(
                task->task.getId().equals(taskId)).findFirst().orElseThrow(()->
                new RuntimeException("Task is not Found"));

        taskRepository.delete(taskToDelete);

        /*
        Another alternative is to add a separate method in the repository class that takes the same
        Parameters for UUIDS as here in this method, so it would make a query to the database by the help of Spring JPA.
        * */

    }

}
