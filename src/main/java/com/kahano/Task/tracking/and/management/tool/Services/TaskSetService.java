package com.kahano.Task.tracking.and.management.tool.Services;

import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TaskSetService {

    List<TaskSet> getAllTaskSet();

    TaskSet getTaskSetByTaskId(UUID id);

    TaskSet createTaskSet(TaskSet taskSet);

    TaskSet updateTaskSet(UUID taskSetId, TaskSet taskSet);

    Optional<TaskSet> getTaskSet(UUID id);

    @Transactional
    void deleteTaskSet(UUID taskSetId);
}
