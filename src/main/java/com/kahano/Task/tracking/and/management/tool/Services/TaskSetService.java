package com.kahano.Task.tracking.and.management.tool.Services;

import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface TaskSetService {

    List<TaskSet> getAllTaskSet();

    TaskSet getTaskSetByTaskId(UUID id);

    TaskSet createTaskSet(TaskSet taskSet);

    TaskSet updateTaskSet(UUID taskSetId, TaskSet taskSet);
}
