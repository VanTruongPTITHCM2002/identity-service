package com.test.identity_service.controller;

import com.test.identity_service.dto.request.UserCreationRequest;
import com.test.identity_service.dto.request.UserUpdateRequest;
import com.test.identity_service.dto.response.ApiResponse;
import com.test.identity_service.dto.response.UserResponse;
import com.test.identity_service.entity.User;
import com.test.identity_service.service.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    IUserService iUserService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> addUser(@RequestBody @Valid UserCreationRequest userCreationRequest){
        UserResponse userResponse = this.iUserService.addUser(userCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserResponse>builder()
                        .status(201)
                        .message("Created User successfully")
                        .data(userResponse)
                .build());
    }

    @GetMapping
   // @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('GET_USERS')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers(){

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("username: " + authentication.getName());
        authentication.getAuthorities().forEach(System.out::println);

        List<UserResponse> userResponseList = this.iUserService.getUsers();
        return ResponseEntity.ok().body(ApiResponse.<List<UserResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get Users successfully")
                        .data(userResponseList)
                .build());
    }

    @GetMapping("/{userId}")
    @PostAuthorize("hasRole('ADMIN') || returnObject.body.username == authentication.name")
    public ResponseEntity<UserResponse> getUser (@PathVariable String userId){
        UserResponse userResponse = this.iUserService.getUser(userId);
        return ResponseEntity.ok().body(userResponse);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser (@PathVariable String userId, @RequestBody UserUpdateRequest userUpdateRequest){
        UserResponse userResponse = this.iUserService.updateUser(userId,userUpdateRequest);
        return ResponseEntity.ok().body(userResponse);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String userId){
        UserResponse userResponse = this.iUserService.deleteUser(userId);
        return ResponseEntity.ok().body(null);
    }
}
