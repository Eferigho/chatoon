package com.chaton.model.user;

import com.chaton.web.config.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "relationship")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private ApplicationUser friendRequestSender;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private ApplicationUser friendRequestReceiver;

    @CreationTimestamp
    private Timestamp timeOfFriendshipRequest;

    @UpdateTimestamp
    private  Timestamp timeOfFriendshipUpdate;

    private FriendshipStatus status = FriendshipStatus.PENDING;


}
