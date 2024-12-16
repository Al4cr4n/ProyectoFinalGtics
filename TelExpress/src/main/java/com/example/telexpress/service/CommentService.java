package com.example.telexpress.service;

import com.example.telexpress.entity.Comment;
import com.example.telexpress.entity.Post;
import com.example.telexpress.entity.Usuario;
import com.example.telexpress.repository.CommentRepository;
import com.example.telexpress.repository.PostRepository;
import com.example.telexpress.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository; // Asegúrate de tener este repositorio
    private final UsuarioRepository usuarioRepository; // Para obtener usuarios

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UsuarioRepository usuarioRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Comment createComment(String content, Integer postId, Integer userId) {
        Post post = postRepository.findById(postId);

        Usuario user = usuarioRepository.findById(userId) ;

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setPost(post);
        comment.setUsuario(user);

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(Integer postId) {
        return commentRepository.findByPostId(postId);
    }
}
