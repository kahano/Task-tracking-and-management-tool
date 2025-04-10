package com.kahano.Task.tracking.and.management.tool.domain.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Task_list")
@Data
@NoArgsConstructor

public class TaskSet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;


    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @OneToMany(mappedBy = "taskSet", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Task> tasks;


    public TaskSet(UUID id, String title, String description, List<Task> tasks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tasks = tasks;
    }



}
