package com.university.mediaverse.service.friends;

import com.university.mediaverse.model.Friendship;
import com.university.mediaverse.model.user.User;
import com.university.mediaverse.repository.friends.FriendshipRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public void saveFriendship(Friendship friendship){
         friendshipRepository.save(friendship);
    }

    public List<User> findFriendsForUser(User currentUser){
        List<Friendship> friendships =  friendshipRepository.findByUser1(currentUser);
        friendships.addAll(friendshipRepository.findByUser2(currentUser));

        List<User> users = new ArrayList<>();

        for(Friendship fr:friendships){
            if (fr.getUser1().getId().equals(currentUser.getId()))
                users.add(fr.getUser2());
            else
                users.add(fr.getUser1());
        }
        return users;
    }

    public Friendship findFriendshipByUser1AndUser2(User user1, User user2){
        Friendship friendship =  friendshipRepository.findByUser1AndUser2(user1, user2);
        if (friendship!= null)
            return friendship;
        friendship = friendshipRepository.findByUser1AndUser2(user2, user1);
        return friendship;
    }

    public void deleteFriend(Friendship friend){
        friendshipRepository.delete(friend);
    }
}
