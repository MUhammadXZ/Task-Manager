package com.example.TaskManager.controller;

import com.example.TaskManager.dto.task.TaskRequest;
import com.example.TaskManager.dto.task.TaskResponse;
import com.example.TaskManager.model.User;
import com.example.TaskManager.model.UserTask;
import com.example.TaskManager.model.enums.TaskStatus;
import com.example.TaskManager.security.UserPrincipal;
import com.example.TaskManager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<UserTask> updateTaskStatus(
            @PathVariable UUID taskId,
            @RequestParam TaskStatus status,
            @AuthenticationPrincipal User user
    ) throws AccessDeniedException {
        return ResponseEntity.ok(taskService.updateTaskSecurity(taskId, user.getEmail(),
                task -> task.setStatus(status)));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<UserTask> getTask(
            @PathVariable UUID taskId,
            @AuthenticationPrincipal User user
    ) throws AccessDeniedException {
        return ResponseEntity.ok(taskService.updateTaskSecurity(taskId, user.getEmail(),
                task -> {}));
    }
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable UUID taskId,
            @AuthenticationPrincipal User user
    ) throws AccessDeniedException {
        taskService.deleteTask(taskId, user.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<UserTask> updateTask(
            @PathVariable UUID taskId,
            @RequestBody TaskRequest request,
            @AuthenticationPrincipal User user
    ) throws AccessDeniedException {
        return ResponseEntity.ok(
                taskService.updateTask(taskId, request, user.getEmail())
        );
    }

}
