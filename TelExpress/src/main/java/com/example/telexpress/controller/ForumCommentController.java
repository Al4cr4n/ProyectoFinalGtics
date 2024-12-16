package com.example.telexpress.controller;

import com.example.telexpress.dto.CommentRequest;
import com.example.telexpress.entity.Comment;
import com.example.telexpress.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class ForumCommentController {
    @Autowired
    private final CommentService commentService;

    public ForumCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest commentRequest) {
        Comment newComment = commentService.createComment(
                commentRequest.getContent(),
                commentRequest.getPostId(),
                commentRequest.getUserId()
        );
        return ResponseEntity.ok(newComment);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Integer postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
