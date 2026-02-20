package com.fileManager.file.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fileManager.file.models.Post;
import com.fileManager.file.models.User;


public interface PostRepository extends JpaRepository<Post,Integer> {
    
}
