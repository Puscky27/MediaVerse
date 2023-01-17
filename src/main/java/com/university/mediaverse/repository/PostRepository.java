package com.university.mediaverse.repository;

import com.university.mediaverse.model.Post;
import com.university.mediaverse.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  PostRepository extends JpaRepository<Post, Long>{

    List<Post> findByUser(User user);

    Post findById(int id);

    List<Post> findAll();

}
