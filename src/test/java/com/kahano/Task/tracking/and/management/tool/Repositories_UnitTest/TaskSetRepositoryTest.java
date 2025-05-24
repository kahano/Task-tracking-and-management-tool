package com.kahano.Task.tracking.and.management.tool.Repositories_UnitTest;


import com.kahano.Task.tracking.and.management.tool.Repository.TaskSetRepository;
import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskPriority;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(properties = {
        "spring.jpa.properties.javax.persistence.validation.mode=none"
})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TaskSetRepositoryTest {

    @Autowired
    private TaskSetRepository taskSetRepository;

    @Test
    void itShouldReturnTaskSetByTaskId(){


        LocalDateTime now = LocalDateTime.now();
        LocalDate dueDate = LocalDate.of(2025, 4, 30);

        TaskSet taskSet = new TaskSet();
        taskSet.setTitle("Test TaskSet");
        taskSet.setDescription("Test task list and contents");
        taskSet.setCreated(now);
        taskSet.setUpdated(now);

        Task task1 = new Task();
        task1.setTitle("Business meeting");
        task1.setPriority(TaskPriority.HIGH);
        task1.setTaskSet(taskSet);

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskSet.setTasks(taskList);

        taskSet = taskSetRepository.save(taskSet);
        Optional<TaskSet> tasks = taskSetRepository.findTaskSetByTaskId(task1.getId());

        TaskSet finalTaskSet = taskSet;
        assertThat(tasks)
                .isPresent()
                .hasValueSatisfying(t -> {
                    assertThat(t.getId()).isEqualTo(finalTaskSet.getId());
                    assertThat(t.getTitle()).isEqualToIgnoringCase("Test TaskSet");
                });
    }

}
