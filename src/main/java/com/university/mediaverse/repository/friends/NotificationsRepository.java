package com.university.mediaverse.repository.friends;


import com.university.mediaverse.model.Notifications;
import com.university.mediaverse.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {

    List<Notifications> findByUser2(User user);

    Notifications findById(int id);

    Notifications findByUser1AndUser2(User user1, User user2);
}