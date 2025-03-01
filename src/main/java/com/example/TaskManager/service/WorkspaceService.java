package com.example.TaskManager.service;

import com.example.TaskManager.dto.workspace.WorkspaceRequest;
import com.example.TaskManager.exception.AccessDeniedException;
import com.example.TaskManager.exception.ResourceNotFoundException;
import com.example.TaskManager.model.User;
import com.example.TaskManager.model.Workspace;
import com.example.TaskManager.repository.UserRepository;
import com.example.TaskManager.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    @Transactional
    public Workspace createWorkspace(Workspace workspace, String userEmail) {
        User user = userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        workspace.setUser(user);
        return workspaceRepository.save(workspace);
    }

    @Transactional(readOnly = true)
    public Workspace getSecureWorkspace(UUID workspaceId, String userEmail) {
        return workspaceRepository.findSecureById(workspaceId, userEmail)
                .orElseThrow(() -> new AccessDeniedException("Workspace access denied"));
    }

    public boolean validateWorkspaceAccess(UUID workspaceId, String userEmail) {
        return workspaceRepository.existsSecure(workspaceId, userEmail);
    }
    public void deleteWorkspace(UUID workspaceId, String userEmail) {
        Workspace workspace = workspaceRepository.findSecureById(workspaceId, userEmail)
                .orElseThrow(() -> new AccessDeniedException("Workspace access denied"));
        workspaceRepository.delete(workspace);
    }

    public Workspace updateWorkspace(UUID workspaceId, WorkspaceRequest request, String userEmail) {
        Workspace workspace = workspaceRepository.findSecureById(workspaceId, userEmail)
                .orElseThrow(() -> new AccessDeniedException("Workspace access denied"));
        workspace.setName(request.name());
        workspace.setDescription(request.description());
        return workspaceRepository.save(workspace);
    }
}