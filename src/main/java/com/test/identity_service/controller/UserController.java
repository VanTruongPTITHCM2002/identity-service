package com.test.identity_service.controller;

import com.test.identity_service.dto.request.UserCreationRequest;
import com.test.identity_service.dto.response.ApiResponse;
import com.test.identity_service.entity.User;
import com.test.identity_service.service.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    IUserService iUserService;

    @PostMapping
    public ResponseEntity<ApiResponse<User>> addUser(@RequestBody @Valid UserCreationRequest userCreationRequest){
        User user = this.iUserService.addUser(userCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<User>builder().status(201).message("Created User successfully")
                        .data(user)
                .build());
    }

    @GetMapping
    public ResponseEntity<?> getUsers(){
        List<User> users = this.iUserService.getUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser (@PathVariable String userId){
        User user = this.iUserService.getUser(userId);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser (@PathVariable String userId, @RequestBody UserCreationRequest userCreationRequest){
        User user = this.iUserService.updateUser(userId,userCreationRequest);
        return ResponseEntity.ok().body(user);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId){
        User user = this.iUserService.deleteUser(userId);
        return ResponseEntity.ok().body("delete user successfully");
    }
}
