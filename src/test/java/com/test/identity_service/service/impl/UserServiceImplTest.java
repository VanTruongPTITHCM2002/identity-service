package com.test.identity_service.service.impl;

import com.test.identity_service.dto.request.UserCreationRequest;
import com.test.identity_service.dto.response.UserResponse;
import com.test.identity_service.entity.Permission;
import com.test.identity_service.entity.Role;
import com.test.identity_service.entity.User;
import com.test.identity_service.exception.AppException;
import com.test.identity_service.repository.RoleRepository;
import com.test.identity_service.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private Role role;
    private LocalDate dob;

    @BeforeEach
    void initData(){

        dob = LocalDate.of(1990,1,1);

        request = UserCreationRequest.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();


        user = User.builder()
                .id("dadsdasddasdads")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();
    }

    @Test
    void addUser_validRequest_success(){
        //GIVE
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        //WHEN
        var response =  userServiceImpl.addUser(request);
        //THEN
        Assertions.assertThat(response.getUsername()).isEqualTo("john");
    }

    @Test
    void addUser_userExisted_fail(){
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class,
                () -> userServiceImpl.addUser(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode())
                .isEqualTo(1001);
    }

    @Test
    @WithMockUser(username = "john")
    void getUser_valid_success(){
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        var response = userServiceImpl.getUser(user.getId());

        Assertions.assertThat(response.getUsername()).isEqualTo("john");

    }
}
