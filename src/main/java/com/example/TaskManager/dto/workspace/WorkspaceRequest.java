package com.example.TaskManager.dto.workspace;

import javax.validation.constraints.*;

public record WorkspaceRequest(
        @NotBlank @Size(max = 100) String name,
        @Size(max = 500) String description
) {}
