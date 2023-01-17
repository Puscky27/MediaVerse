package com.university.mediaverse.repository.user;

import com.university.mediaverse.model.AccountType;
import com.university.mediaverse.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUserName(String userName);

    List<User> findAll();

    User findUserById(int id);

}