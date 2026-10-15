package com.engineerindigest.demo_project.controller;

import com.engineerindigest.demo_project.dto.UserListResponse;
import com.engineerindigest.demo_project.dto.UserPatchRequest;
import com.engineerindigest.demo_project.dto.UserRequest;
import com.engineerindigest.demo_project.dto.UserResponse;
import com.engineerindigest.demo_project.model.User;
import com.engineerindigest.demo_project.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User APIs", description = "APIs for managing user")
@Slf4j
public class UserController {


    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users in the system")
    public UserListResponse getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a single user for the given ID. Throws 404 if not found.")
    public ResponseEntity<UserResponse> getUser(@PathVariable(name = "id") int userId) {
        return ResponseEntity.ok()
                .header("something", "good")
                .body(userService.getUserById(userId));
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Accepts name and age, validates them, and creates a new user")
    public UserResponse createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping
    @Operation(summary = "Replace a user", description = "Completely replaces user data for the given ID")
    public UserResponse replaceUser(@RequestParam int id, @RequestBody @Valid UserRequest userRequest) {
        return userService.replaceUser(id, userRequest);
    }

    @PatchMapping
    @Operation(summary = "Partially update a user", description = "Updates only the provided fields for the given user ID")
    public UserResponse updateUser(@RequestParam int id, @RequestBody @Valid UserPatchRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes the user with the given ID")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search users by name", description = "Returns users whose name contains the search term")
    public List<User> searchUsers(@RequestParam String name) {
        return userService.searchUsers(name);
    }
}

