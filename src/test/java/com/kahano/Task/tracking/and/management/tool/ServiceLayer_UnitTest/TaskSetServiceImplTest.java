package com.kahano.Task.tracking.and.management.tool.ServiceLayer_UnitTest;

import com.kahano.Task.tracking.and.management.tool.Repository.TaskRespository;
import com.kahano.Task.tracking.and.management.tool.Repository.TaskSetRepository;
import com.kahano.Task.tracking.and.management.tool.Services.Impl.TaskSetServiceImpl;
import com.kahano.Task.tracking.and.management.tool.Services.TaskSetService;
import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskPriority;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskStatus;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskSetServiceImplTest {

    @Mock
    private TaskSetRepository taskSetRepository_test;



    @InjectMocks
    private TaskSetServiceImpl TaskService_test;

    private TaskSet taskSet ;

    private Task task;




    @BeforeEach
    void setUp() {


        taskSet = TaskSet.builder()
                .title("Test TaskSet")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .tasks(new ArrayList<>())
                .build();


        // 2. Create a Task that links to the TaskSet
        task = Task.builder()
                .title("Business meeting")
                .description("talks about convention")
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.HIGH)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                 .taskSet(taskSet) // ← now this works because taskSet is already initialized
                .build();

        // 3. Now add the Task to the TaskSet's list
        taskSet.setTasks(List.of(task));




    }

    @AfterEach
    void tearDown() {

        taskSetRepository_test.deleteAll();

    }

    @Test
    void itShouldGetAllTaskSet() {

        // Given
        List<TaskSet> taskSets = List.of(taskSet);
        // when

        when(taskSetRepository_test.findAll()).thenReturn(taskSets);
        List<TaskSet> allTaskSets = TaskService_test.getAllTaskSet();


        // then

        assertThat(allTaskSets.size()).isGreaterThan(0);
        assertThat(allTaskSets).isNotNull();
        assertThat(allTaskSets.size()).isEqualTo(1);


    }

    @Test
    void itShouldGetTaskSetByTaskId() {
        // Given



        // when
        when(taskSetRepository_test.findTaskSetByTaskId(task.getId())).thenReturn(Optional.ofNullable(taskSet));

        Optional<TaskSet> existing_taskSet = Optional.ofNullable(TaskService_test.getTaskSetByTaskId(task.getId()));

        // then


            assertThat(existing_taskSet).isNotNull();
            assertThat(existing_taskSet).isEqualTo(Optional.ofNullable(taskSet));





    }

    @Test
    void itShouldCreateTaskSet() {
        // Given
        when(taskSetRepository_test.save(any(TaskSet.class))).thenAnswer(invocation -> {
            TaskSet saved = invocation.getArgument(0);

            if (saved.getTasks() != null) {
                for (Task t : saved.getTasks()) {
                    t.setTaskSet(saved); // <- bidirectional link
                }
            } else {
                saved.setTasks(new ArrayList<>());
                saved.getTasks().add(task);
                task.setTaskSet(saved);
            }

            return saved;


        });

        // when
        TaskSet savedTaskSet = TaskService_test.createTaskSet(taskSet);

        // then
        assertThat(savedTaskSet).isNotNull();
        assertThat(savedTaskSet.getTitle()).isEqualTo("Test TaskSet");
        assertThat(savedTaskSet.getTasks())
                .isNotNull();

       assertThat(savedTaskSet.getTasks().size()).isEqualTo(1);

         //Verify the bidirectional relationship
        assertThat(savedTaskSet.getTasks().getFirst().getTaskSet())
                .isEqualTo(savedTaskSet);

        verify(taskSetRepository_test, times(1)).save(savedTaskSet);



    }

    @Test
    void itShouldUpdateTaskSet() {
        // Given

        UUID id = UUID.randomUUID(); // acts as an existing taskset id

        TaskSet taskSetUpdate = TaskSet.builder()
                .id(id)
                .title("Test an updated TaskSet")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .tasks(new ArrayList<>())
                .build();
        Task nytask = Task.builder()
                .title("Parents meeting")
                .description("talks about convention")
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .taskSet(taskSetUpdate)
                .build();

        taskSetUpdate.setTasks(List.of(nytask,task));

        // when

        when(taskSetRepository_test.findById(id)).thenReturn(Optional.of(taskSetUpdate));
        when(taskSetRepository_test.save(taskSetUpdate)).thenReturn(taskSetUpdate);

        TaskSet taskSet_check = TaskService_test.updateTaskSet(id,taskSetUpdate);

        // then

        assertThat(taskSetUpdate).isNotNull();
        assertThat(taskSetUpdate.getTasks().contains(nytask));
        assertThat(taskSetUpdate.getTasks().size()).isGreaterThan(1);
        assertThat(taskSetUpdate.getTitle()).isEqualToIgnoringCase("Test an updated taskSet");


    }

    @Test
    void itShouldGetTaskSet(){
        // Given

        UUID id = UUID.randomUUID(); // acts as an existing taskset id

        TaskSet taskSetUpdate = TaskSet.builder()
                .id(id)
                .title("Test an updated TaskSet")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .tasks(new ArrayList<>())
                .build();
        Task nytask = Task.builder()
                .title("Parents meeting")
                .description("talks about convention")
                .status(TaskStatus.OPEN)
                .priority(TaskPriority.MEDIUM)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .taskSet(taskSetUpdate)
                .build();

        taskSetUpdate.setTasks(List.of(nytask,task));

        // when

        when(taskSetRepository_test.findById(id)).thenReturn(Optional.of(taskSetUpdate));

        Optional<TaskSet> getTaskSet = Optional.ofNullable(TaskService_test.getTaskSet(id));

        // then
        assertThat(getTaskSet).isNotNull();
        assertThat(getTaskSet.get().getTasks().containsAll(List.of(task,nytask)));
        assertThat(getTaskSet.get().getTasks().size()).isEqualTo(2);
    }
}