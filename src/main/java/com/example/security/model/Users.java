package com.example.security.model;


import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Users {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;
    private String provider;
    private String providerId;
    @CreationTimestamp
    private Timestamp createDate;
    
        @Builder
        public Users(String username, String password, String email, String role, String provider, String providerId, Timestamp createDate) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.role = role;
            this.provider = provider;
            this.providerId = providerId;
            this.createDate = createDate;
    }
}
