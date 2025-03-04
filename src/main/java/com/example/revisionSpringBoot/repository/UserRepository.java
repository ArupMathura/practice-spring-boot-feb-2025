package com.example.revisionSpringBoot.repository;

import com.example.revisionSpringBoot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
