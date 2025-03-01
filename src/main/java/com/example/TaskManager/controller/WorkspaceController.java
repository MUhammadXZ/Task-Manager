package com.example.TaskManager.controller;

import com.example.TaskManager.dto.workspace.WorkspaceRequest;
import com.example.TaskManager.exception.AccessDeniedException;
import com.example.TaskManager.model.User;
import com.example.TaskManager.model.UserTask;
import com.example.TaskManager.model.Workspace;
import com.example.TaskManager.service.TaskService;
import com.example.TaskManager.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(
            @RequestBody WorkspaceRequest request,
            @AuthenticationPrincipal User user
    ) {
        Workspace workspace = new Workspace();
        workspace.setName(request.name());
        workspace.setDescription(request.description());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workspaceService.createWorkspace(workspace, user.getEmail()));
    }

    @GetMapping("/{workspaceId}/tasks")
    public ResponseEntity<List<UserTask>> getWorkspaceTasks(
            @PathVariable UUID workspaceId,
            @AuthenticationPrincipal User user
    ) {
        if (!workspaceService.validateWorkspaceAccess(workspaceId, user.getEmail())) {
            throw new AccessDeniedException("Workspace access denied");
        }
        return ResponseEntity.ok(taskService.getWorkspaceTasks(workspaceId));
    }
    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<Void> deleteWorkspace(
            @PathVariable UUID workspaceId,
            @AuthenticationPrincipal User user
    ) {
        workspaceService.deleteWorkspace(workspaceId, user.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<Workspace> updateWorkspace(
            @PathVariable UUID workspaceId,
            @RequestBody WorkspaceRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(
                workspaceService.updateWorkspace(workspaceId, request, user.getEmail())
        );
    }
}

