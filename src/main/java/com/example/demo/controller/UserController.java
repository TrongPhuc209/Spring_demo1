package com.example.demo.controller;

import com.example.demo.model.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.example.demo.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public Object createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return result.getAllErrors();
        }
        return userRepository.save(user);
    }

    @GetMapping("/gets")
    public List<User> prinUser() {
        List<User> users = userRepository.findAll();

        System.out.println("In ra danh sach user: ");
        for (User i : users) {
            System.out.println("ID: " + i.getId() + ", name: " + i.getName() + ", age: " + i.getAge());
        }

        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User uesrDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User exitingUser = optionalUser.get();
            exitingUser.setName(uesrDetails.getName());
            exitingUser.setAge(uesrDetails.getAge());
            userRepository.save(exitingUser);
            return ResponseEntity.ok(exitingUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User delete successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
