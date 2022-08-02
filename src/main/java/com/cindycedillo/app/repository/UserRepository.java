package com.cindycedillo.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cindycedillo.app.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

}
