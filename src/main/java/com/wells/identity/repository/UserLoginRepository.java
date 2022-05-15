package com.wells.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wells.identity.model.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, String>{

}
