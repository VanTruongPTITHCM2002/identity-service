package com.test.identity_service.config;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.test.identity_service.entity.Role;
import com.test.identity_service.entity.User;
import com.test.identity_service.repository.RoleRepository;
import com.test.identity_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationConfigInit {

    PasswordEncoder passwordEncoder;

    @Bean
//    @ConditionalOnProperty(
//            prefix = "spring",
//            value = "datasource.driverClassName",
//            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            boolean checkAdmin = userRepository.findByUsername("admin").isEmpty();
            if (checkAdmin) {
                var role = roleRepository.findById("ADMIN").orElse(null);
                var roles = new HashSet<Role>();
                roles.add(role);
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
