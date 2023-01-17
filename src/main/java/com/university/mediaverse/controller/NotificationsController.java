package com.university.mediaverse.controller;

import com.university.mediaverse.model.Notifications;
import com.university.mediaverse.model.user.User;
import com.university.mediaverse.service.friends.NotificationsService;
import com.university.mediaverse.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class NotificationsController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationsService notificationsService;

    @GetMapping("/getNotifications")
    public @ResponseBody List<Map<String, String>> getNotifications(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        List<Notifications> notifications = notificationsService.findNotificationsForUser(user);
        List<Map<String, String>> formattedNotifications = new ArrayList<>();

        for(Notifications notifications1 : notifications){
            String notificationText = "";
            switch (notifications1.getType()){
                case "friendRequest":
                    notificationText = notifications1.getUser1().getFirstName() + " sent you a friend request.";
                default:
                    break;
            }
            formattedNotifications.add(Map.ofEntries(Map.entry("text", notificationText),
                    Map.entry("username", notifications1.getUser1().getUserName()),
                    Map.entry("friendId", notifications1.getUser1().getId().toString()),
                    Map.entry("notificationId", notifications1.getId().toString())));
        }
        return formattedNotifications;
    }

}
