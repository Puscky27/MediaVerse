package com.university.mediaverse.service;


import com.university.mediaverse.model.Post;
import com.university.mediaverse.model.user.User;
import com.university.mediaverse.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post savePost(Post  post){
        return postRepository.save(post);
    }

    public void deletePost(Post post){
        postRepository.delete(post);
    }

    public List<Post> findAll(){return postRepository.findAll();}

    public List<Post> findPostForUser(User user) {
        return postRepository.findByUser(user);
    }
}
