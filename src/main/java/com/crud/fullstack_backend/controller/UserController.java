package com.crud.fullstack_backend.controller;

import com.crud.fullstack_backend.exception.UserNotFoundException;
import com.crud.fullstack_backend.model.User;
import com.crud.fullstack_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users/add")
    public Map<String, Object> newUser(@RequestBody User newUser) {
        User savedUser = userRepository.save(newUser);

        // Create response JSON
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", savedUser);

        return response;
    }

    @GetMapping("/users")
    public Map<String, Object> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Create response JSON
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", users);

        return response;
    }

    @GetMapping("/user/{id}")
    public Map<String, Object> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Create response JSON
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", user);

        return response;
    }

    @PutMapping("/user/edit/{id}")
    public Map<String, Object> updateUser(@RequestBody User newUser, @PathVariable Long id) {
        User updatedUser = userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setUsername(newUser.getUsername());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));

        // Create response JSON
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", updatedUser);

        return response;
    }

    @DeleteMapping("/user/delete/{id}")
    public Map<String, Object> deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        // Return JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "User with id " + id + " has been deleted successfully");
        return response;
    }
}
