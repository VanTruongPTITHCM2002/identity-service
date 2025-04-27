package com.test.identity_service.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(
        name = "user",
        uniqueConstraints = {@UniqueConstraint(name = "uk_users_username", columnNames = "username")})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(
            name = "username",
            unique = true,
            nullable = false,
            columnDefinition = "varchar(255) COLLATE utf8mb4_unicode_ci")
    String username;

    @Column(nullable = false, length = 255)
    String password;

    @Column(nullable = false, length = 100)
    String firstName;

    @Column(nullable = false, length = 100)
    String lastName;

    LocalDate dob;

    @ManyToMany
    Set<Role> roles;
}
