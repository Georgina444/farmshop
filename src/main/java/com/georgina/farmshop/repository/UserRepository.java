package com.georgina.farmshop.repository;

import com.georgina.farmshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// with Long we provide the user id
// <User> entity is used by JPA repository to perform database operations
public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);
}

// In the context of JPA (Java Persistence API) and databases,
// Long is used because JPA requires the use of objects for IDs, not primitives.
// When JPA interacts with the database, it treats the ID field as an object that can be nullable
// (i.e., it can represent a null value),