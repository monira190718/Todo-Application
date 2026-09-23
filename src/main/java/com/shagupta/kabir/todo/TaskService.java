package com.shagupta.kabir.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getFilteredTasks(String filter, String search) {
        boolean hasSearch = search != null && !search.trim().isEmpty();
        String keyword = hasSearch ? search.trim() : "";

        if ("active".equalsIgnoreCase(filter)) {
            return hasSearch
                    ? taskRepository.findByTitleContainingIgnoreCaseAndCompleted(keyword, false)
                    : taskRepository.findByCompleted(false);
        } else if ("completed".equalsIgnoreCase(filter)) {
            return hasSearch
                    ? taskRepository.findByTitleContainingIgnoreCaseAndCompleted(keyword, true)
                    : taskRepository.findByCompleted(true);
        } else {
            return hasSearch
                    ? taskRepository.findByTitleContainingIgnoreCase(keyword)
                    : taskRepository.findAll();
        }
    }

    public void addTask(Task task) {
        if (task.getTitle() != null && !task.getTitle().trim().isEmpty()) {
            task.setCompleted(false);
            if (task.getPriority() == null || task.getPriority().isEmpty()) {
                task.setPriority("LOW");
            }
            taskRepository.save(task);
        }
    }

    public void toggleTask(String id) {
        taskRepository.findById(id).ifPresent(task -> {
            task.setCompleted(!task.isCompleted());
            taskRepository.save(task);
        });
    }

    public void deleteTask(String id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        }
    }
}
