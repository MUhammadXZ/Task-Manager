package com.example.TaskManager.mapper;

import com.example.TaskManager.dto.task.TaskRequest;
import com.example.TaskManager.dto.task.TaskResponse;
import com.example.TaskManager.model.UserTask;
import com.example.TaskManager.model.enums.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "workspace", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserTask toEntity(TaskRequest request);

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    TaskResponse toResponse(UserTask task);

    @Named("statusToString")
    default String statusToString(TaskStatus status) {
        return status.name();
    }
}