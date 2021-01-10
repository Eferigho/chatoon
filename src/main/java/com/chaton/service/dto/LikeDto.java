package com.chaton.service.dto;

import com.chaton.model.post.Choice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {

    private Choice choice;
    private String likeAuthor;
    private Long postId;
    private Long likeId;
}
