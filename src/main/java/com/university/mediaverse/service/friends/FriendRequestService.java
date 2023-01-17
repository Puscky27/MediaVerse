package com.university.mediaverse.service.friends;

import com.university.mediaverse.model.FriendRequest;
import com.university.mediaverse.model.user.User;
import com.university.mediaverse.repository.friends.FriendRequestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    public FriendRequest saveFriendRequest(FriendRequest friendRequest){
        return friendRequestRepository.save(friendRequest);
    }

    public List<User> findFriendsForUser(User user) {
        List<FriendRequest> requests = friendRequestRepository.findByUser1(user);

        List<User> users = new ArrayList<>();

        for(FriendRequest fr:requests){
            users.add(fr.getUser2());
        }
        return users;
    }

    public FriendRequest findFriendRequestUser1User2(User user1, User user2) {
        return friendRequestRepository.findByUser1AndUser2(user1, user2);
    }

    public void deleteFriendRequest(FriendRequest friendRequest) {
        friendRequestRepository.delete(friendRequest);
    }

}
