package com.anant.SpringSecurity_2X.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anant.SpringSecurity_2X.entity.Post;

public interface PostRepository extends JpaRepository<Post,Integer>{

}
