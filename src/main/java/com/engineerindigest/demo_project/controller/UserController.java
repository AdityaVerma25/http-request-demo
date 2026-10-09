package com.engineerindigest.demo_project.controller;

import com.engineerindigest.demo_project.dto.UserPatchRequest;
import com.engineerindigest.demo_project.dto.UserRequest;
import com.engineerindigest.demo_project.dto.UserResponse;

import com.engineerindigest.demo_project.mappers.UserMapper;
import com.engineerindigest.demo_project.model.User;
import com.engineerindigest.demo_project.respository.UserRepository;
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

//    private static final Logger logger = LoggerFactory.getLogger(UserController.class); we used @SLF4J


    private final UserMapper userMapper;

    private final UserRepository userRepository;
    private final UserService userService;


    @GetMapping("/")
    @Operation(summary = "API for get all users list")
    public List<User> getUser() {
        return userService.getAllUsers();

    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a user from databases by Id")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") int userId) {
        return ResponseEntity.ok()
                .header("something", "good")
                .body(userService.getUserById(userId));
    }

    @PostMapping
    @Operation(summary = "API for crate a new user")
    public UserResponse createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping
    @Operation(summary = "Replace user details", description = "Replace all info of an user")
    public User replaceUser(@RequestParam int id, @RequestBody @Valid UserRequest userRequest) {
        return userService.replaceUser(id, userRequest);
    }

    @PatchMapping
    @Operation(summary = "Partially update a user", description = "Updates only the provided fields for the given user")
    public void updateUser(@RequestParam int id, @RequestBody @Valid UserPatchRequest userPatchRequest) {
        userService.updateUser(id, userPatchRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an user")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Find user by name")
    public UserResponse getUserByName(@RequestParam String name) {
        return userService.getUserByName(name);
    }
}

