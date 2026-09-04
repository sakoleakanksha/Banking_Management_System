package com.akanksha.bankingmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @Column(unique = true, nullable = false)
        private String email;

        private String password;

        private String phone;

        private String role;

        public User() {
        }

        public User(Long id, String name, String email, String password,
                         String phone, String role) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
            this.phone = phone;
            this.role = role;
        }

        // Getters and Setters

}

