package com.engineerindigest.demo_project.services;

import com.engineerindigest.demo_project.dto.UserListResponse;
import com.engineerindigest.demo_project.dto.UserPatchRequest;
import com.engineerindigest.demo_project.dto.UserRequest;
import com.engineerindigest.demo_project.dto.UserResponse;
import com.engineerindigest.demo_project.exception.ResourceNotFoundException;
import com.engineerindigest.demo_project.mappers.UserMapper;
import com.engineerindigest.demo_project.model.User;
import com.engineerindigest.demo_project.respository.UserRepository;
import com.engineerindigest.demo_project.security.AccessControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.AccessDeniedException;
import java.time.Duration;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AccessControl accessControl;

    @Cacheable(value = "users", key = "'all'")
    public UserListResponse getAllUsers() {
        accessControl.allowOnlyAdmin();
        log.info("Fetching all users");
        List<UserResponse> users = userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
        log.info("Total users found: {}", users.size());
        return new UserListResponse(users);
    }

    @Cacheable(value = "user", key = "#userId")
    public UserResponse getUserById(int userId) {
        accessControl.allowSameUserOrAdmin(userId);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.warn("User not found with id: {}", userId);
            throw new ResourceNotFoundException(
                    "User with id " + userId + " not found");
        }
        log.info("User found: {}", user.getName());
        return userMapper.toUserResponse(user);
    }

    public UserResponse createUser(UserRequest userRequest) {
        log.info("Creating new user with name: {}", userRequest.getName());
        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String role = (userRequest.getRole() == null || userRequest.getRole().isBlank())
                ? "USER"
                : userRequest.getRole();

        userRequest.setRole(role);
        user.setRole(role);
        UserResponse response = userMapper.toUserResponse(userRepository.save(user));
        log.info("User created successfully with id: {}", response.getId());
        return response;
    }

    @CachePut(value = "user", key = "#id")
    public UserResponse replaceUser(int id, UserRequest userRequest) {
        accessControl.allowSameUserOrAdmin(id);
        log.info("Replacing user with id: {}", id);
        User user = userMapper.toUser(userRequest);
        user.setId(id);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @CachePut(value = "user", key = "#id")
    public UserResponse updateUser(int id, UserPatchRequest userRequest) {
        accessControl.allowSameUserOrAdmin(id);
        log.info("Updating user with id: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        User newUser = userMapper.toUser(userRequest);
        if (newUser.getAge() > 0) {
            existingUser.setAge(newUser.getAge());
        }
        if (newUser.getName() != null && !newUser.getName().isBlank()) {
            existingUser.setName(newUser.getName());
        }
        return userMapper.toUserResponse(userRepository.save(existingUser));
    }

    @CacheEvict(value = "user", key = "#id")
    public void deleteUser(int id) {
        accessControl.allowOnlyAdmin();
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }

    public List<User> searchUsers(String name) {
        accessControl.allowOnlyAdmin();
        log.info("Searching users with name containing: {}", name);
        return userRepository.findByNameContainingIgnoreCase(name);
    }

}