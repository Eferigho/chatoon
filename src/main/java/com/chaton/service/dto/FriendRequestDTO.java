package com.chaton.service.dto;

import com.chaton.model.user.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FriendRequestDTO {
    private String friendRequestSender;
    private String friendRequestReceiver;
    private FriendshipStatus status;
    private  Integer index;
    private String username;


}
