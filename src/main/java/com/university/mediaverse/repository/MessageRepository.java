package com.university.mediaverse.repository;

import com.university.mediaverse.model.*;
import com.university.mediaverse.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

    List<Message> findByUser1(User user1);

    Message findById(int id);

    List<Message> findAll();

    List<Message> findAllByUser1AndUser2(User user1, User user2);

    List<Message> findAllByUser2AndUser1(User user1, User user2);


}
