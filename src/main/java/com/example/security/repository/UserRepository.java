package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.model.Users;

//CRUD 함수를 Jpa가 들고잇음
public interface UserRepository extends JpaRepository<Users, Integer> {

    
}
