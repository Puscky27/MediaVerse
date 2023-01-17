package com.university.mediaverse.controller;

import com.university.mediaverse.model.Post;
import com.university.mediaverse.model.user.User;
import com.university.mediaverse.service.PostService;
import com.university.mediaverse.service.friends.FriendshipService;
import com.university.mediaverse.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private FriendshipService friendshipService;

    @GetMapping("/home")
    public String userIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        List<Post> listFriendsPosts = postService.findAll();
        List<Post> listPosts = new ArrayList<>();
        List<User> friendsForCurrentUser = friendshipService.findFriendsForUser(user);
        for(Post post : listFriendsPosts){
            if(friendsForCurrentUser.contains(post.getUser())){
                listPosts.add(post);
            }
        }
        listPosts.sort(Comparator.comparing(Post::getTime));
        Collections.reverse(listPosts);
        model.addAttribute("user", user);
        model.addAttribute("listPosts", listPosts);
        model.addAttribute("listFriends", friendsForCurrentUser);
        return "mainpage/home";
    }


}
