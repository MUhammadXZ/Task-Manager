package com.example.TaskManager.mapper;

import com.example.TaskManager.dto.workspace.WorkspaceRequest;
import com.example.TaskManager.dto.workspace.WorkspaceResponse;
import com.example.TaskManager.model.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkspaceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Workspace toEntity(WorkspaceRequest request);

    @Mapping(target = "id", source = "user.id")
    WorkspaceResponse toResponse(Workspace workspace);
}
