package com.kahano.Task.tracking.and.management.tool.ServiceLayer_UnitTest;

import com.kahano.Task.tracking.and.management.tool.Repository.TaskRespository;
import com.kahano.Task.tracking.and.management.tool.Repository.TaskSetRepository;
import com.kahano.Task.tracking.and.management.tool.Services.Impl.TaskServiceImpl;
import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskPriority;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@Nested
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRespository taskRepository_test;

    @Mock
    private TaskSetRepository taskSetRepository_test;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskSet taskSet ;

    private Task task;

    @BeforeEach
    void setUp() {
        taskSet = TaskSet.builder()

                .title("Test TaskSet")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        task = Task.builder()

                .title("Business meeting")
                .description("talks about convention")
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.HIGH)
                .taskSet(taskSet)
                .build();
    }

    @Test
    void itShouldCreateTask() {

        // Given
        Task inputTask = Task.builder()
                .title("New Task")
                .description("Description of task")
                .dueDate(null) // optional
                .build();

        when(taskSetRepository_test.findById(taskSet.getId())).thenReturn(Optional.of(taskSet));
        when(taskRepository_test.save(Mockito.any(Task.class))).thenAnswer(invocation -> {
            Task taskToSave = invocation.getArgument(0);
            return taskToSave;
        });

        // When
        Task savedTask = taskService.createTask(taskSet.getId(), inputTask);

        // Then
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo("New Task");
        assertThat(savedTask.getTaskSet()).isEqualTo(taskSet);
        assertThat(savedTask.getPriority()).isEqualTo(TaskPriority.MEDIUM); // default
        assertThat(savedTask.getStatus()).isEqualTo(TaskStatus.OPEN);
        assertThat(savedTask.getCreated()).isNotNull();
        assertThat(savedTask.getUpdated()).isNotNull();

        Mockito.verify(taskSetRepository_test, times(1)).findById(taskSet.getId());
        Mockito.verify(taskRepository_test, times(1)).save(Mockito.any(Task.class));
    }





    @Test
    void itShouldGetAllTask() {
        // Given
        // when
        // then


    }

    @Test
    void itShouldGetTaskById() {
        // Given
        // when
        // then


    }


}

