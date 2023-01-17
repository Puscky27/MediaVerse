package com.university.mediaverse.repository.friends;

import com.university.mediaverse.model.Friendship;
import com.university.mediaverse.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findByUser1(User user);

    List<Friendship> findByUser2(User user);

    Friendship findByUser1AndUser2(User user1, User user2);
}
