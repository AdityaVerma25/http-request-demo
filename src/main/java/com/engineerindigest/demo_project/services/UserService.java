package com.engineerindigest.demo_project.services;

import com.engineerindigest.demo_project.dto.UserPatchRequest;
import com.engineerindigest.demo_project.dto.UserRequest;
import com.engineerindigest.demo_project.dto.UserResponse;
import com.engineerindigest.demo_project.exception.ResourceNotFoundException;
import com.engineerindigest.demo_project.mappers.UserMapper;
import com.engineerindigest.demo_project.model.User;
import com.engineerindigest.demo_project.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //Method for fetch all user
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        List<User> all = userRepository.findAll();
        log.info("Total user found: {} ", all.size());
        return all;
    }


    public UserResponse createUser(UserRequest userRequest) {

        log.info("User is creating,{}", userRequest.getName());
        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        UserResponse userResponse = userMapper.getUserResponse(userRepository.save(user));
        log.info("User successfully created {}", userResponse.getName());
        return userResponse;
    }

    public User getUserById(int userId)  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = (User) authentication.getPrincipal();
        if (loggedInUser.getId() != userId && !loggedInUser.getRole().equals("ADMIN")) {
            log.warn("User{} tried to access data of user {} ", loggedInUser.getId(), userId);

        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.warn("User not found with userId: {}", userId);
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }
        log.info("User found: {}", user.getName());
        return user;
    }

    public User replaceUser(int userId, UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        user.setId(userId);
        userRepository.save(user);
        return user;
    }

    public void updateUser(int userId, UserPatchRequest userPatchRequest) {
        User existingUser = userRepository.findById(userId).orElse(null);
        User newUser = userMapper.patchUser(userPatchRequest);
        if (newUser.getAge() > 0) {
            existingUser.setAge(newUser.getAge());
        }
        if (newUser.getName() != null && !newUser.getName().isEmpty() && !newUser.getName().isBlank()) {
            existingUser.setName(newUser.getName());
        }
        userRepository.save(existingUser);
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse getUserByName(String name) {
        return userMapper.getUserResponse(userRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }
}
