package com.chaton.model.user;

import com.chaton.repository.AddressRepository;
import com.chaton.repository.FriendRepository;
import com.chaton.repository.UserRepository;
import com.chaton.web.config.ApplicationUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Sql(scripts = {"classpath:db\\insert_data.sql"})
class UserTest {
    Logger logger = Logger.getLogger(UserTest.class.getName());

    ApplicationUser user;


    @MockBean
    AddressRepository addressRepository;

    @Autowired
    AddressRepository addressRepo;


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepository concreteUserRepository;

    @Autowired
    FriendRepository friendRepository;

    @BeforeEach
    void setUp() {
        user = new ApplicationUser();
    }

    @Test
    @Rollback(value = false)
    void createUserWithAddressTest(){
        Address myAddress = new Address();

        myAddress.setHouseNumber("15");
        myAddress.setStreetName("Oshodi");
        myAddress.setCity("Isolo");
        myAddress.setState("Lagos");
        myAddress.setCountry("Nigeria");
        addressRepo.save(myAddress);

        user.setUsername("francis@gmail.com");
        user.setPassword("dewew2ef3");
        user.setProfileName("Francis");
        user.setGender(Gender.MALE);


        userRepository.save(user);


        assertThat(addressRepository).isNotNull();
        assertThat(userRepository).isNotNull();
    }

    @Test
    void confirmUserStateTest(){

        ApplicationUser newUser = userRepository.findById(3L).orElseThrow();


    }

    @Test
    void confirmUserAddressTest(){
        Address myAddress = new Address();
        myAddress.setId(2L);
        myAddress.setHouseNumber("10");
        myAddress.setStreetName("Onike");
        myAddress.setCity("Sabo");
        myAddress.setState("Lagos");
        myAddress.setCountry("Nigeria");

        ApplicationUser newUser = userRepository.findById(3L).orElseThrow();

    }

    @Test
    void updateUserAddress(){
        ApplicationUser myUser = userRepository.findById(3L).orElseThrow();


        userRepository.save(myUser);


    }

    @Test
    void createFriendship(){
        Optional<ApplicationUser> friendRequestSender = userRepository.findByUsername("francis@gmail.com");
        Optional<ApplicationUser> friendRequestReceiver = userRepository.findByUsername("chris@chaton.com");

        Friend myFriend = new Friend();
        myFriend.setFriendRequestSender(friendRequestSender.orElseThrow());
        myFriend.setFriendRequestReceiver(friendRequestReceiver.orElseThrow());


        if(friendRequestReceiver.get().getId().equals(friendRequestSender.get().getId())){
            logger.info("You can not send friend request to yourself");
        }
        else {
            friendRepository.save(myFriend);
            logger.info("Friend request sent successfully!!!");
        }

        assertThat(friendRepository).isNotNull();
    }

    @Test
    void getAllFriendRequestSend(){
        ApplicationUser myUser = userRepository.findById(5L).orElseThrow();
        List<Friend> all_friends_send =
                friendRepository.findByFriendRequestSender(myUser);

        logger.info(String.valueOf(all_friends_send.stream()));
    }

    @Test
    void getAllFriendRequestReceive(){

        ApplicationUser myUser = userRepository.findById(5L).orElseThrow();
        List<Friend> all_friends_receive = friendRepository.findByFriendRequestSender(myUser);

    }


    @Test
    void getTotalNumberOfFriends(){
        Optional<ApplicationUser> myUser = Optional.ofNullable(userRepository.findById(4L).orElseThrow());
        List<Friend> all_friends_receive =
                friendRepository.findByFriendRequestSender(myUser.orElseThrow());
        logger.info(myUser.get().getUsername()+" your total friend(s) : "+all_friends_receive.stream().count()+"\n" );
    }

    @Test
    void acceptAllRequestFriendship(){
        Long currentUserId = 4L;
        ApplicationUser currentUser = userRepository.findById(currentUserId).orElseThrow();
        List<Friend> friendRequest =
                friendRepository.findByFriendRequestReceiverAndStatus(currentUser,FriendshipStatus.PENDING);
        friendRequest.forEach(friend ->{
            friend.setStatus(FriendshipStatus.ACCEPTED);
            friendRepository.save(friend);
            logger.info(friend.getFriendRequestReceiver().getUsername() +"  request have been  " + FriendshipStatus.ACCEPTED);
        });
//
    }

    @Test
    void findFriendBySenderOrReceiver(){
        ApplicationUser user = userRepository.findByUsername("Francis").orElseThrow();
        Friend friend = friendRepository.findByFriendRequestSenderAndFriendRequestReceiver(user,user);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void deleteByUsername(){
        userRepository.deleteByUsername("Francis");
    }
}