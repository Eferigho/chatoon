package com.chaton.service;

import com.chaton.model.user.Friend;
import com.chaton.model.user.FriendshipStatus;
import com.chaton.web.config.ApplicationUser;

import java.util.List;
import java.util.Optional;

public interface FriendService {

    Friend createFriendship(ApplicationUser friendRequestSender, ApplicationUser friendRequestReceiver) throws Exception;

    List<Friend> myFriendship(ApplicationUser user);

    Friend updateFriend(Optional<ApplicationUser> sender, Optional<ApplicationUser> receiver, FriendshipStatus status) throws Exception;

    Friend myFriend(Optional<ApplicationUser> sender, Optional<ApplicationUser> receiver) throws Exception;
    void deleteFriendship(ApplicationUser sender, ApplicationUser receiver);

}
