package com.engineerindigest.demo_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UserPatchRequest {
    @Schema(description = "Full name of the user", example = "Akshit Sharma")
    private String name;

    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 70, message = "Age cannot exceed 70")
    @Schema(description = "Age of the user", example = "25")
    private Integer age;
}
