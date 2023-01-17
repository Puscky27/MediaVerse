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
@Table(name = "notifications")
public class Notifications {

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

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private Integer status;
}
