package com.chaton.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String commentAuthor;
    private  Long postId;
    private  String commentBody;
    private Long commentId;
}
