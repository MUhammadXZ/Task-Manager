package com.example.TaskManager.service;

import com.example.TaskManager.dto.task.TaskRequest;
import com.example.TaskManager.model.UserTask;
import com.example.TaskManager.model.Workspace;
import com.example.TaskManager.repository.TaskRepository;
import com.example.TaskManager.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public UserTask createTask(UserTask task, UUID workspaceId, String userEmail) throws AccessDeniedException {
        Workspace workspace = workspaceRepository.findSecureById(workspaceId, userEmail)
                .orElseThrow(() -> new AccessDeniedException("Workspace access denied"));
        task.setWorkspace(workspace);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<UserTask> getWorkspaceTasks(UUID workspaceId) {
        return taskRepository.findByWorkspaceId(workspaceId);
    }

    @Transactional
    public UserTask updateTaskSecurity(UUID taskId, String userEmail, Consumer<UserTask> updater) throws AccessDeniedException {
        UserTask task = taskRepository.findSecureById(taskId, userEmail)
                .orElseThrow(() -> new AccessDeniedException("Task access denied"));
        updater.accept(task);
        return taskRepository.save(task);
    }
    public void deleteTask(UUID taskId, String userEmail) throws AccessDeniedException {
        UserTask task = taskRepository.findSecureById(taskId, userEmail)
                .orElseThrow(() -> new AccessDeniedException("Task access denied"));
        taskRepository.delete(task);
    }

    public UserTask updateTask(UUID taskId, TaskRequest request, String userEmail) throws AccessDeniedException {
        UserTask task = taskRepository.findSecureById(taskId, userEmail)
                .orElseThrow(() -> new AccessDeniedException("Task access denied"));
        task.setTitle(request.title());
        task.setDescription(request.description());
        return taskRepository.save(task);
    }

}