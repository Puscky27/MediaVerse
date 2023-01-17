package com.university.mediaverse.service.friends;

import com.university.mediaverse.model.Notifications;
import com.university.mediaverse.model.user.User;
import com.university.mediaverse.repository.friends.NotificationsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationsService {

    private final NotificationsRepository notificationsRepository;

    public NotificationsService(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    public Notifications saveNotification(Notifications  notifications){
        return notificationsRepository.save(notifications);
    }

    public void deleteNotification(Notifications notifications){
        notificationsRepository.delete(notifications);
    }
    public List<Notifications> findNotificationsForUser(User user) {
       return notificationsRepository.findByUser2(user);
    }

    public Notifications findNotificationsById(int notificationId) {
        return notificationsRepository.findById(notificationId);
    }

    public Notifications findNotificationByUser1User2(User user1, User user2) {
        return notificationsRepository.findByUser1AndUser2(user1, user2);
    }
}


