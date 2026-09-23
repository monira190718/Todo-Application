package com.shagupta.kabir.todo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    // Filter by completed status
    List<Task> findByCompleted(boolean completed);

    // Search by title (case-insensitive)
    List<Task> findByTitleContainingIgnoreCase(String title);

    // Search by title and completed status
    List<Task> findByTitleContainingIgnoreCaseAndCompleted(String title, boolean completed);

    
}

