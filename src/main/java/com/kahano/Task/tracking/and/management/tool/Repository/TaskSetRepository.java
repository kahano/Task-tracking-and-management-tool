package com.kahano.Task.tracking.and.management.tool.Repository;

import com.kahano.Task.tracking.and.management.tool.domain.entities.TaskSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskSetRepository extends JpaRepository<TaskSet, UUID> {



}
