package com.test.identity_service.config;

import com.test.identity_service.entity.User;
import com.test.identity_service.enums.Role;
import com.test.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ApplicationConfigInit {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner (UserRepository userRepository){
        return args -> {
            boolean checkAdmin = userRepository.findByUsername("admin").isEmpty();
            if(checkAdmin){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .username("admin")
                        .firstName("")
                        .lastName("")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
            userRepository.save(user);
            log.info("admin has been created with password");
            }
        };
    }
}
