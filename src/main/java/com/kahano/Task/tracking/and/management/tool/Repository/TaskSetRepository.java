package com.kahano.Task.tracking.and.management.tool.Repository;

import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
    public interface TaskSetRepository extends JpaRepository<TaskSet, UUID> {

        @Query(value = "SELECT ts FROM TaskSet ts JOIN ts.tasks t WHERE t.id = :taskId")
        Optional<TaskSet> findTaskSetByTaskId(@Param("taskId") UUID taskId);
    }



