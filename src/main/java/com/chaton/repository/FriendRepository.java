package com.chaton.repository;

import com.chaton.model.user.Friend;
import com.chaton.model.user.FriendshipStatus;
import com.chaton.web.config.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByFriendRequestSender(ApplicationUser user);

    List<Friend> findByFriendRequestReceiverAndStatus(ApplicationUser currentUser, FriendshipStatus status);

    void deleteByFriendRequestSenderOrFriendRequestReceiver(ApplicationUser sender, ApplicationUser receiver);

    List<Friend> findByFriendRequestSenderOrFriendRequestReceiver(ApplicationUser sender, ApplicationUser receiver);

    Friend findByFriendRequestSenderAndFriendRequestReceiver(ApplicationUser sender, ApplicationUser receiver);

//    f.asuelimen@semicolon.com
}
