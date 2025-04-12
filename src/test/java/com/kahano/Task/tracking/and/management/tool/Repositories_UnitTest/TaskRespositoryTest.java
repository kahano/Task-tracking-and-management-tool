package com.kahano.Task.tracking.and.management.tool.Repositories_UnitTest;

import com.kahano.Task.tracking.and.management.tool.Repository.TaskRespository;
import com.kahano.Task.tracking.and.management.tool.Repository.TaskSetRepository;
import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskPriority;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskStatus;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(  properties = {
        "spring.jpa.properties.javax.persistence.validation.mode=none"
})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)

class TaskRespositoryTest {

    @Autowired
    private TaskRespository taskRespository;

    @Autowired
    private TaskSetRepository taskSetRepository;








    @Test
//    @Transactional
    void itShouldReturnTaskByTaskSetID_And_TaskID() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate dueDate = LocalDate.of(2025, 4, 30);

        TaskSet taskSet = new TaskSet();
        taskSet.setTitle("Test TaskSet");
        taskSet.setCreated(null);
        taskSet.setUpdated(null);

       taskSet = taskSetRepository.save(taskSet); // ✅ Save the TaskSet first
        Task task1 = new Task();
        task1.setTitle("Business meeting");
        task1.setPriority(TaskPriority.HIGH); // 👈 add this line
        task1.setTaskSet(taskSet);

        Task task2 = new Task();
       task2.setTitle("Parents meeting");
        task1.setPriority(TaskPriority.MEDIUM);
        task2.setTaskSet(taskSet);
        taskRespository.save(task2);

        Optional<Task> foundTask = taskRespository.findByTaskSetIdAndTaskId(taskSet.getId(), task2.getId());

        assertThat(foundTask)
                .isPresent()
                .hasValueSatisfying(t -> {
                    assertThat(t.getId()).isEqualTo(task2.getId());
                    assertThat(t.getTitle()).isEqualToIgnoringCase("Parents meeting");
                });
    }

    @Test
    void itShouldReturnListOfTasksByTaskSetID() {

        LocalDateTime now = LocalDateTime.now();
        LocalDate dueDate = LocalDate.of(2025, 4, 30);

        TaskSet taskSet = new TaskSet();
        taskSet.setTitle("Test TaskSet");
        taskSet.setCreated(now);
        taskSet.setUpdated(now);

        taskSet = taskSetRepository.save(taskSet); // ✅ Save the TaskSet first
        Task task1 = new Task();
        task1.setTitle("Business meeting");
        task1.setStatus(TaskStatus.OPEN);
        task1.setPriority(TaskPriority.HIGH);
        task1.setTaskSet(taskSet);
        taskRespository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Parents meeting");
        task2.setStatus(TaskStatus.OPEN);
        task2.setPriority(TaskPriority.MEDIUM);
        task2.setTaskSet(taskSet);
        taskRespository.save(task2);


        List<Task> task_List = taskRespository.findByTaskSetId(taskSet.getId());
        TaskSet finalTaskSet = taskSet;
        Assertions.assertThat(task_List).isNotNull()
                .hasSize(2)
                .anySatisfy(task -> {
            assertThat(task.getTitle()).isEqualTo("Parents meeting");
            assertThat(task.getPriority()).isEqualTo(TaskPriority.MEDIUM);
            assertThat(task.getStatus()).isEqualTo(TaskStatus.OPEN);
            assertThat(task.getTaskSet().getId()).isEqualTo(finalTaskSet.getId());
        });



    }





}