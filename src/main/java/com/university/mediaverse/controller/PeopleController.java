package com.university.mediaverse.controller;

import com.university.mediaverse.model.user.User;
import com.university.mediaverse.repository.user.UserRepository;
import com.university.mediaverse.service.friends.FriendRequestService;
import com.university.mediaverse.service.friends.FriendshipService;
import com.university.mediaverse.service.friends.NotificationsService;
import com.university.mediaverse.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PeopleController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private FriendshipService friendshipService;

    @GetMapping("/people")
    public String userIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        model.addAttribute("user", user);
        List<User> listUsers = userRepo.findAll();
        List<User> friendsForCurrentUser = friendshipService.findFriendsForUser(user);
        listUsers.removeAll(friendsForCurrentUser);
        listUsers.remove(user);

        List<User> friendRequests = friendRequestService.findFriendsForUser(user);

        model.addAttribute("listUsers", listUsers);
        model.addAttribute("friendRequests", friendRequests);
        return "mainpage/people";
    }

    @GetMapping("/getPeople")
    public @ResponseBody List<Map<String, String>> getPeople() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        List<User> listUsers = userRepo.findAll();
        listUsers.remove(user);

        List<Map<String, String>> formattedPeopleList = new ArrayList<>();

        for (User users:listUsers){
            formattedPeopleList.add(Map.ofEntries(Map.entry("username", users.getUserName()),
                    Map.entry("id", users.getId().toString()),
                    Map.entry("name", users.getFirstName()+ " " + users.getLastName())));
        }

        return formattedPeopleList;
    }

}
