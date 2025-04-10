package com.kahano.Task.tracking.and.management.tool.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;





@Entity
@Table(name="Task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id", nullable=false, updatable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name="priority", nullable = false)
    private TaskPriority priority;

    @Column(name="created", nullable = false)
    private LocalDateTime created;



    @Column(name="updated", nullable = false)
    private LocalDateTime updated;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_set_id")
    private TaskSet taskSet;

    public Task(UUID id, String title, String description, LocalDate dueDate,
                TaskPriority priority, TaskStatus status, LocalDateTime created,
                LocalDateTime updated, TaskSet taskSet) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.created = created;
        this.updated = updated;
        this.taskSet = taskSet;
    }


}
