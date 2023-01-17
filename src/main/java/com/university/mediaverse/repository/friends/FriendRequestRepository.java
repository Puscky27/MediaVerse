package com.university.mediaverse.repository.friends;

import com.university.mediaverse.model.FriendRequest;
import com.university.mediaverse.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findByUser1(User user1);

    FriendRequest findByUser1AndUser2(User user1, User user2);
}
