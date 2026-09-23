package com.shagupta.kabir.todo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getAllTasks(
            @RequestParam(value = "filter", required = false, defaultValue = "all") String filter,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            Model model) {

        model.addAttribute("tasks", taskService.getFilteredTasks(filter, search));
        model.addAttribute("newTask", new Task());
        model.addAttribute("currentFilter", filter);
        model.addAttribute("currentSearch", search);
        model.addAttribute("today", LocalDate.now());
        return "page";
    }

    @PostMapping
    public String addTask(@ModelAttribute("newTask") Task task) {
        taskService.addTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/toggle/{id}")
    public String toggleTask(@PathVariable("id") String id) {
        taskService.toggleTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") String id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
