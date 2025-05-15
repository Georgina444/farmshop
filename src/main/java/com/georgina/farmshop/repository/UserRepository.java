package com.georgina.farmshop.repository;

import com.georgina.farmshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// with long we provide the user id
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
