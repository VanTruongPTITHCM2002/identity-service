package com.test.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false,length = 100)
    String username;
    @Column(nullable = false,length = 50)
    String password;
    @Column(nullable = false,length = 100)
    String firstName;
    @Column(nullable = false,length = 100)
    String lastName;

    LocalDate dob;
}
