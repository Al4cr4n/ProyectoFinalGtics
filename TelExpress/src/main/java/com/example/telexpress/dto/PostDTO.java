package com.example.telexpress.dto;

import com.example.telexpress.entity.Comment;
import com.example.telexpress.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PostDTO {
    private Post post;
    private List<Comment> list;


}
