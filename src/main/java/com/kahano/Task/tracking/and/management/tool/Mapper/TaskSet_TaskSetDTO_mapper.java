package com.kahano.Task.tracking.and.management.tool.domain.Mapper;

import com.kahano.Task.tracking.and.management.tool.domain.DTO.TaskSetDTO;
import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskSet_TaskSetDTO_mapper {

    private final Task_TaskDTO_mapper mapper;

    public TaskSet_TaskSetDTO_mapper(Task_TaskDTO_mapper mapper) {
        this.mapper = mapper;
    }

    public TaskSet toTaskSet(TaskSetDTO taskSetDTO) {
        return new TaskSet(
                taskSetDTO.id(),
                taskSetDTO.title(),
                taskSetDTO.Description(),
                Optional.ofNullable(taskSetDTO.tasks())
                        .map(tasks -> tasks.stream()
                                .map(mapper::toTask)
                                .collect(Collectors.toList()))
                        .orElse(null)
        );
    }

    public TaskSetDTO toTaskSetDTO(TaskSet taskSet) {
        final List<Task> tasks = taskSet.getTasks();
        return new TaskSetDTO(
                taskSet.getId(),
                taskSet.getTitle(),
                taskSet.getDescription(),
                Optional.ofNullable(tasks)
                        .map(List::size)
                        .orElse(0),
                calculateProgress(tasks),
                Optional.ofNullable(taskSet.getTasks())
                      .map(task -> task.stream()
                        .map(mapper::toTaskDTO)
                        .collect(Collectors.toList()))
                        .orElse(null)
        );
    }

    private Double calculateProgress(List<Task> tasks){
        if(tasks==null){
            return null;
        }
        long closedTaskCount = tasks.stream()
                .filter(task-> TaskStatus.CLOSED == task.getStatus())
                .count();
        return (double) closedTaskCount / tasks.size();
    }
}
