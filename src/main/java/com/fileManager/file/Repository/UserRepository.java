package com.fileManager.file.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fileManager.file.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
