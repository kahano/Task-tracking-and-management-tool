package com.kahano.Task.tracking.and.management.tool.ServiceLayer_UnitTest;

import com.kahano.Task.tracking.and.management.tool.Repository.TaskRespository;
import com.kahano.Task.tracking.and.management.tool.Repository.TaskSetRepository;
import com.kahano.Task.tracking.and.management.tool.Services.Impl.TaskServiceImpl;
import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskPriority;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
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

    private Task task2;

    private Task inputTask;

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
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .taskSet(taskSet)
                .build();

        task2 = Task.builder()

                .title("Parents meeting")
                .description("School activities")
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .taskSet(taskSet)
                .build();

         inputTask = Task.builder()
                .title("New Task")
                .description("Description of task")
                .dueDate(null) // optional
                .build();
    }

    @AfterEach

    void teardown(){

        taskRepository_test.deleteAll();
        taskSetRepository_test.deleteAll();
    }

    @Test
    void itShouldCreateTask() {



        when(taskSetRepository_test.findById(taskSet.getId())).thenReturn(Optional.ofNullable(taskSet)); // handle nullpointer exception in case
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

        List<Task> tasks = List.of(task,task2,inputTask);
        // when
        when(taskRepository_test.findByTaskSetId(taskSet.getId())).thenReturn(tasks);
        List<Task> taskList = taskService.getAllTask(taskSet.getId());


        // then

        assertThat(taskList).size().isGreaterThan(0);
        assertThat(taskList).isNotNull();

        Mockito.verify(taskRepository_test, times(1)).findByTaskSetId(taskSet.getId());



    }

    @Test
    void itShouldGetTaskById() {
        // Given

        // when
//
        when(taskRepository_test.findByTaskSetIdAndTaskId(taskSet.getId(),task.getId())).thenReturn(Optional.ofNullable(task));


        // then

        Optional<Task> existing_task = Optional.ofNullable(taskService.getTaskById(taskSet.getId(), task.getId()));

        assertThat(existing_task).isNotNull();
        assertThat(existing_task.get().getTitle()).isEqualTo("Business meeting");
        assertThat(existing_task.get().getTaskSet()).isEqualTo(taskSet);
        assertThat(existing_task.get().getPriority()).isEqualTo(TaskPriority.HIGH); // default
        assertThat(existing_task.get().getStatus()).isEqualTo(TaskStatus.OPEN);
        assertThat(existing_task.get().getCreated()).isNotNull();
        assertThat(existing_task.get().getUpdated()).isNotNull();


        Mockito.verify(taskRepository_test, times(1)).findByTaskSetIdAndTaskId(taskSet.getId(),task.getId());



    }


}

