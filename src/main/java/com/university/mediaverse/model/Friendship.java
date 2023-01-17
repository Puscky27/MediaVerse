package com.university.mediaverse.model;

import com.university.mediaverse.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friendship")
public class Friendship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    public Friendship(User user1, User user2){
        this.user1 = user1;
        this.user2 = user2;
    }
}
