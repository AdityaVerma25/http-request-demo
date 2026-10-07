package com.engineerindigest.demo_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 character")
    @Schema(name = "Name of the user", example = "John Hoe")
    private String name;

    @Min(value = 1, message = "Age must be 1")
    @Max(value = 70, message = "Age must be less than 70")
    @Schema(name = "Age of User", example = "25")
    private int age;
}
