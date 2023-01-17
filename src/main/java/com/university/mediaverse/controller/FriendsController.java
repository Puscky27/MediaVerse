package com.university.mediaverse.controller;

import com.university.mediaverse.model.FriendRequest;
import com.university.mediaverse.model.Friendship;
import com.university.mediaverse.model.Message;
import com.university.mediaverse.model.Notifications;
import com.university.mediaverse.model.user.User;
import com.university.mediaverse.repository.user.UserRepository;
import com.university.mediaverse.service.MessageService;
import com.university.mediaverse.service.friends.FriendRequestService;
import com.university.mediaverse.service.friends.FriendshipService;
import com.university.mediaverse.service.friends.NotificationsService;
import com.university.mediaverse.service.user.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class FriendsController {

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

    @Autowired
    private MessageService messageService;

    @GetMapping("/myfriends")
    public String userFriends(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        model.addAttribute("user", user);
        List<User> friendsForCurrentUser = friendshipService.findFriendsForUser(user);
        model.addAttribute("friendsUser", friendsForCurrentUser);
        return "mainpage/myfriends";
    }


    @PostMapping("/deleteFriend")
    public String deleteUser (@Valid User addUser, BindingResult bindingResult, @RequestParam String friend_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        User friend = userService.findUserById(Integer.parseInt(friend_id));


        friendshipService.deleteFriend(friendshipService.findFriendshipByUser1AndUser2(user, friend));
        return "redirect:/myfriends";
    }


    @PostMapping("/sendFriendRequest")
    public String sendFriendRequest (@Valid User addUser, BindingResult bindingResult, @RequestParam String friend_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        User friend = userService.findUserById(Integer.parseInt(friend_id));
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setUser1(user);
        friendRequest.setUser2(friend);
        friendRequest.setStatus(1);

        Notifications notifications = new Notifications();
        notifications.setUser1(user);
        notifications.setUser2(friend);
        notifications.setStatus(1);
        notifications.setTime(LocalDateTime.now());
        notifications.setType("friendRequest");

        notificationsService.saveNotification(notifications);
        friendRequestService.saveFriendRequest(friendRequest);

        return "redirect:/people";
    }

    @PostMapping("/cancelFriendRequest")
    public String deleteFriendRequest (@Valid User addUser, BindingResult bindingResult, @RequestParam String friend_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        User friend = userService.findUserById(Integer.parseInt(friend_id));

        FriendRequest friendRequest = friendRequestService.findFriendRequestUser1User2(user, friend);
        friendRequestService.deleteFriendRequest(friendRequest);

        Notifications notifications = notificationsService.findNotificationByUser1User2(user,friend);
        notificationsService.deleteNotification(notifications);

        return "redirect:/people";
    }

    @PostMapping("/confirmFriendRequest")
    public ResponseEntity confirmFriendRequest (@RequestParam String friend_id, @RequestParam int notificationId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        User friend = userService.findUserById(Integer.parseInt(friend_id));

        friendshipService.saveFriendship(new Friendship(user, friend));
        Notifications notifications= notificationsService.findNotificationsById(notificationId);
        notificationsService.deleteNotification(notifications);

        FriendRequest friendRequest = friendRequestService.findFriendRequestUser1User2(friend, user);
        friendRequestService.deleteFriendRequest(friendRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getFriendsForUser")
    public @ResponseBody List<Map<String, String>> getFriendsForUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        List<Map<String, String>> formattedFriendsList = new ArrayList<>();

        List<User> friends = friendshipService.findFriendsForUser(user);

        for (User friend:friends){
            formattedFriendsList.add(Map.ofEntries(Map.entry("username", friend.getUserName()),
                    Map.entry("name", friend.getFirstName()+ " " + friend.getLastName())));
        }

        return formattedFriendsList;
    }

    @PostMapping("/getMessagesForUser")
    public @ResponseBody List<Map<String, String>> getMessagesForUser(@RequestParam String friend_id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUserName(auth.getName());
        User friend = userService.findUserByUserName(friend_id);

        List<Map<String, String>> messagesResponse = new ArrayList<>();

        List<Message> messages = messageService.findMessagesForUserWithFriend(currentUser, friend);

        for(Message message:messages) {
            messagesResponse.add(Map.ofEntries(Map.entry("message", message.getContent()),
                    Map.entry("type", message.getUser1().getUserName().equals(currentUser.getUserName()) ? "sent" : "received")));
        }

        return messagesResponse;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity sendMessage (@RequestParam String friend_id, @RequestParam String messageText) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        User friend = userService.findUserByUserName(friend_id);
        if(!Strings.isBlank(messageText)) {
            Message message = new Message(user, friend, LocalDateTime.now(), messageText, 0);

            messageService.saveMessage(message);
        }
        return ResponseEntity.ok().build();
    }

}
