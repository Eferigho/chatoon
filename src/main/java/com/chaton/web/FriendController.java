package com.chaton.web;

import com.chaton.model.user.Friend;
import com.chaton.web.config.ApplicationUser;
import com.chaton.service.FriendService;
import com.chaton.web.config.UserService;
import com.chaton.service.dto.FriendRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/")
public class FriendController {
    Logger logger = Logger.getLogger(FriendController.class.getName());
    @Autowired
    FriendService friendService;

    @Autowired
    UserService userService;

    @GetMapping("/{username}/friends")
    List<Friend> allMyFriends(@PathVariable String username)throws Exception{
        Optional<ApplicationUser> myUser = userService.findUserByUsername(username);
        return friendService.myFriendship(myUser.orElseThrow());

    }

    @PostMapping("/{username}/friend")
    ResponseEntity<Friend> newFriendship( @RequestBody FriendRequestDTO friendRequestDTO) throws Exception{

            Optional<ApplicationUser> sender = userService.findUserByUsername(friendRequestDTO.getFriendRequestSender());
            Optional<ApplicationUser> receiver = userService.findUserByUsername(friendRequestDTO.getFriendRequestReceiver());

        if (sender.isPresent() && receiver.isPresent()){
                Friend newFriend= friendService.createFriendship(sender.orElseThrow(),receiver.orElseThrow());
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{username}/friend")
    ResponseEntity<Friend> updateFriendship(@RequestBody FriendRequestDTO friendRequestDTO)throws Exception{
        Optional<ApplicationUser> sender = userService.findUserByUsername(friendRequestDTO.getFriendRequestSender());
        Optional<ApplicationUser> receiver = userService.findUserByUsername(friendRequestDTO.getFriendRequestReceiver());

        if(sender.isPresent() && receiver.isPresent()){
                Friend updateFriendship = friendService.updateFriend(sender, receiver,friendRequestDTO.getStatus());
                return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{username}/friend")
    ResponseEntity<?> deleteFriendship(@RequestBody FriendRequestDTO friendRequestDTO) throws Exception {

        Optional<ApplicationUser> sender = userService.findUserByUsername(friendRequestDTO.getFriendRequestSender());
        Optional<ApplicationUser> receiver = userService.findUserByUsername(friendRequestDTO.getFriendRequestReceiver());

        if (sender.isPresent() && receiver.isPresent()){
            Friend myFriend = friendService.myFriend(sender,receiver);

                logger.info(myFriend.getFriendRequestSender().getUsername());

            if (myFriend != null) {
                friendService.deleteFriendship(sender.orElseThrow(),receiver.orElseThrow());
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }
        else {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
