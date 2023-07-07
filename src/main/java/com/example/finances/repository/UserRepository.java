package com.example.finances.repository;

// Spring Dependencies
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.finances.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
