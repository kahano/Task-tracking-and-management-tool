package com.kahano.Task.tracking.and.management.tool.Repository;

import com.kahano.Task.tracking.and.management.tool.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRespository extends JpaRepository<Task, UUID> {

    List<Task> findByTaskSetId(UUID TaskSet);


    // JPQL with named parameters
    @Query("SELECT t FROM Task t WHERE t.taskSet.id = :taskSetId AND t.id = :taskId")
    Optional<Task> findByTaskSetIdAndTaskId(
            @Param("taskSetId") UUID taskSetId,
            @Param("taskId") UUID taskId
    );
}
