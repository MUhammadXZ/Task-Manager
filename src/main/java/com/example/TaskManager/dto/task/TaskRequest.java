package com.example.TaskManager.dto.task;

import javax.validation.constraints.*;

public record TaskRequest(
        @NotBlank @Size(max = 200) String title,
        @Size(max = 1000) String description
) {}