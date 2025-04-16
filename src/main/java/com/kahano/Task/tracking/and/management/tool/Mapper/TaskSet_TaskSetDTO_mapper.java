package com.kahano.Task.tracking.and.management.tool.Mapper;

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


    public static TaskSet toTaskSet(TaskSetDTO taskSetDTO) {
        return new TaskSet(
                taskSetDTO.id(),
                taskSetDTO.title(),
                taskSetDTO.Description(),
                 null, null,
                 Optional.ofNullable(taskSetDTO.tasks())
                        .map(tasks -> tasks.stream()
                                .map(Task_TaskDTO_mapper::toTask)
                                .collect(Collectors.toList()))
                        .orElse(null)
        );
    }

    public static TaskSetDTO toTaskSetDTO(TaskSet taskSet) {
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
                        .map(Task_TaskDTO_mapper::toTaskDTO)
                        .collect(Collectors.toList()))
                        .orElse(null)
        );
    }

    private static Double calculateProgress(List<Task> tasks){
        if(tasks==null){
            return null;
        }
        long closedTaskCount = tasks.stream()
                .filter(task-> TaskStatus.CLOSED == task.getStatus())
                .count();
        return (double) closedTaskCount / tasks.size();
    }
}
