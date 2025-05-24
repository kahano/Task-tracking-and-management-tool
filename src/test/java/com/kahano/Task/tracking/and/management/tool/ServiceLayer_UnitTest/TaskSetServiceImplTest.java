package com.kahano.Task.tracking.and.management.tool.ServiceLayer_UnitTest;

import com.kahano.Task.tracking.and.management.tool.Repository.TaskRespository;
import com.kahano.Task.tracking.and.management.tool.Repository.TaskSetRepository;
import com.kahano.Task.tracking.and.management.tool.Services.Impl.TaskSetServiceImpl;
import com.kahano.Task.tracking.and.management.tool.Services.TaskSetService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskSetServiceImplTest {

    @Mock
    private TaskSetRepository taskSetRepository_test;



    @InjectMocks
    private TaskSetServiceImpl TaskService_test;

//    TaskSetServiceImplTest(TaskSetService taskServiceTest) {
//        TaskService_test = taskServiceTest;
//    }

//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void itShouldGetAllTaskSet() {
        // Given
        // when
        // then


    }

    @Test
    void itShouldGetTaskSetByTaskId() {
        // Given
        // when
        // then


    }

    @Test
    void itShouldCreateTaskSet() {
        // Given
        // when
        // then


    }

    @Test
    void itShouldUpdateTaskSet() {
        // Given
        // when
        // then


    }
}