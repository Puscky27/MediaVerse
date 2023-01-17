package com.university.mediaverse.controller.user;

import com.university.mediaverse.Utils.ImageUtility;
import com.university.mediaverse.model.Post;
import com.university.mediaverse.model.user.User;
import com.university.mediaverse.repository.PostRepository;
import com.university.mediaverse.repository.user.UserRepository;
import com.university.mediaverse.service.PostService;
import com.university.mediaverse.service.friends.FriendRequestService;
import com.university.mediaverse.service.friends.FriendshipService;
import com.university.mediaverse.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserService userService;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private FriendRequestService friendRequestService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;

    @GetMapping("/settings")
    public String userSettings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        model.addAttribute("user", user);
        return "mainpage/usersettings";
    }

    @GetMapping("/myprofile")
    public String myProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        List<Post> listPosts = postService.findPostForUser(user);
        model.addAttribute("user", user);
        Collections.reverse(listPosts);
        model.addAttribute("listPosts", listPosts);
        return "mainpage/myprofile";
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model, @RequestParam int friend_id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        User friend = userService.findUserById(friend_id);
        List<Post> listPosts = postService.findPostForUser(friend);
        Collections.reverse(listPosts);
        List<User> listUsers = userRepo.findAll();
        List<User> friendsForCurrentUser = friendshipService.findFriendsForUser(user);
        listUsers.removeAll(friendsForCurrentUser);
        listUsers.remove(user);

        List<User> friendRequests = friendRequestService.findFriendsForUser(user);


        model.addAttribute("friendRequests", friendRequests);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("user", user);
        model.addAttribute("friend", friend);
        model.addAttribute("listPosts", listPosts);
        return "mainpage/userprofile";
    }

    @PostMapping("/userProfile")
    public String userProfileOld(Model model, @RequestParam int friend_id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        User friend = userService.findUserById(friend_id);
        List<Post> listPosts = postService.findPostForUser(friend);
        Collections.reverse(listPosts);
        List<User> listUsers = userRepo.findAll();
        List<User> friendsForCurrentUser = friendshipService.findFriendsForUser(user);
        listUsers.removeAll(friendsForCurrentUser);
        listUsers.remove(user);

        List<User> friendRequests = friendRequestService.findFriendsForUser(user);

        model.addAttribute("friendRequests", friendRequests);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("user", user);
        model.addAttribute("friend", friend);
        model.addAttribute("listPosts", listPosts);
        return "mainpage/userprofile";
    }

    @GetMapping("/viewUserProfile/{friend_id}")
    public String userProfileFromSearch(Model model, @PathVariable("friend_id")  int friend_id, RedirectAttributes redirectAttrs){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        User friend = userService.findUserById(friend_id);
        List<Post> listPosts = postService.findPostForUser(friend);
        Collections.reverse(listPosts);

        List<User> listUsers = userRepo.findAll();
        List<User> friendsForCurrentUser = friendshipService.findFriendsForUser(user);
        listUsers.removeAll(friendsForCurrentUser);
        listUsers.remove(user);

        List<User> friendRequests = friendRequestService.findFriendsForUser(user);

        model.addAttribute("friendRequests", friendRequests);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("user", user);
        model.addAttribute("friend", friend);
        model.addAttribute("listPosts", listPosts);
        redirectAttrs.addAttribute("friend_id", friend_id);
        return "redirect:userProfile";
    }

//    @PostMapping("/viewUserProfileFromSearch")
//    public void viewUserProfileFromSearch(Model model, @RequestParam int friend_id){
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        User user = userService.findUserByUserName(auth.getName());
////        User friend = userService.findUserById(friend_id);
////        List<Post> listPosts = postService.findPostForUser(friend);
////        Collections.reverse(listPosts);
////        model.addAttribute("user", user);
////        model.addAttribute("friend", friend);
////        model.addAttribute("listPosts", listPosts);
//        return "redirect:/userProfile";
//    }

    @PostMapping(value = "/update-user")
    public String updateUser(@Valid User updatedUser, BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        if(!StringUtils.isBlank(updatedUser.getPassword())) {
            user.setPassword(updatedUser.getPassword());
        }

        if(!StringUtils.isBlank(updatedUser.getEmail())) {
            user.setEmail(updatedUser.getEmail());
        }

        if(!StringUtils.isBlank(updatedUser.getSkype())) {
            user.setSkype(updatedUser.getSkype());
        }

        if(!StringUtils.isBlank(updatedUser.getPhone())) {
            user.setPhone(updatedUser.getPhone());
        }

        if(!StringUtils.isBlank(updatedUser.getOther_contact())) {
            user.setOther_contact(updatedUser.getOther_contact());
        }

        userService.updateUser(user);
        return "redirect:/settings";
    }

    @PostMapping("/post")
    public String savePost(@Valid User addUser, BindingResult bindingResult, @RequestParam("image") MultipartFile file, @RequestParam String comments)
            throws IOException, URISyntaxException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        Post post= new Post();
        postRepository.save(Post.builder()
                .description(comments)
                .time(LocalDate.now())
                .user(user)
                .type(file.getContentType())
                .image(ImageUtility.compressImage(file.getBytes())).build());
        return "redirect:/home";

    }

    @PostMapping("/deletePost")
    public String deletePost(@RequestParam int post_id)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        Post post = postRepository.findById(post_id);
        postRepository.delete(post);
        return "redirect:/myprofile";
    }
}
