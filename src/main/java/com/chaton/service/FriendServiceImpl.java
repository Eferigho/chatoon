package com.chaton.service;

import com.chaton.model.user.Friend;
import com.chaton.model.user.FriendshipStatus;
import com.chaton.web.config.ApplicationUser;
import com.chaton.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class FriendServiceImpl implements FriendService {
    Logger logger = Logger.getLogger(FriendServiceImpl.class.getName());

    @Autowired
    FriendRepository friendRepository;

    @Override
    public Friend createFriendship(ApplicationUser friendRequestSender, ApplicationUser friendRequestReceiver) throws Exception {
        Friend newFriend = new Friend();
        if (myFriend(Optional.of(friendRequestSender),Optional.of(friendRequestReceiver)) != null){
            logger.info(friendRequestSender.getUsername() +" is already a friend with "+ friendRequestReceiver.getUsername());
            return  newFriend;
        }else {
            newFriend.setFriendRequestSender(friendRequestSender);
            newFriend.setFriendRequestReceiver(friendRequestReceiver);
            return friendRepository.save(newFriend);
        }
    }

    @Override
    public List<Friend> myFriendship(ApplicationUser user) {
        return friendRepository.findByFriendRequestSenderOrFriendRequestReceiver(user,user);

    }

    @Override
    public Friend updateFriend(Optional<ApplicationUser> currentUser, Optional<ApplicationUser> receiver, FriendshipStatus status) throws Exception, NullPointerException {
        try {
            List<Friend> myFriend = friendRepository.findByFriendRequestSenderOrFriendRequestReceiver(currentUser.orElseThrow(),currentUser.orElseThrow());
            myFriend.forEach(friend -> {

                if (currentUser.get().getUsername().equals(friend.getFriendRequestReceiver().getUsername())
                        && friend.getStatus().equals(FriendshipStatus.PENDING)){
                    logger.info(" This is the current user "+currentUser.get().getUsername());
                    logger.info("This user "+friend.getFriendRequestReceiver().getUsername()+" receive the friend request");
                    friend.setStatus(status);
                    friendRepository.save(friend);

                }
                else if (friend.getStatus().equals( FriendshipStatus.ACCEPTED)){
                    logger.info(" This is the current user "+currentUser.get().getUsername());
                    friend.setStatus(status);
                    friendRepository.save(friend);
                }
                else  if (friend.getStatus().equals( FriendshipStatus.BLOCKED) || friend.getStatus().equals( FriendshipStatus.REJECTED)){
                    logger.info(" This is the current user "+currentUser.get().getUsername());
                    friend.setStatus(status);
                    friendRepository.save(friend);
                }

            });

        }catch (Exception ex){
            ex.getMessage();
        }
        return new Friend();
    }

    @Override
    public Friend myFriend(Optional<ApplicationUser> sender, Optional<ApplicationUser> receiver) throws Exception {
        return friendRepository.findByFriendRequestSenderAndFriendRequestReceiver(sender.orElseThrow(),receiver.orElseThrow());
    }


    @Override
    public void deleteFriendship(ApplicationUser sender, ApplicationUser receiver) {
        friendRepository.deleteByFriendRequestSenderOrFriendRequestReceiver(sender,receiver);
        logger.info(sender.getUsername() + "friendship with "+ receiver.getUsername()+ " deleted successfully!!!");
    }
}
