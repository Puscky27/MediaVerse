package com.university.mediaverse.model;

import com.university.mediaverse.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Integer status;

    public Message(User user1, User user2, LocalDateTime time, String content, Integer status) {
        this.user1 = user1;
        this.user2 = user2;
        this.time = time;
        this.content = content;
        this.status = status;
    }
}
