package com.postpaf;

import com.postpaf.Dtos.PostDTO;
import com.postpaf.Models.Post;
import com.postpaf.Models.User;
import com.postpaf.Repositories.PostRepository;
import com.postpaf.Repositories.UserRepository;
import com.postpaf.Services.PostService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test récupération de tous les posts")
    void testGetAllPosts() {
        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Test Post 1");

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Test Post 2");

        when(postRepository.findAll()).thenReturn(Arrays.asList(post1, post2));

        // Act
        List<PostDTO> posts = postService.getAllPosts();

        // Assert
        assertNotEquals(2, posts.size());
        assertNotEquals("Test Post 1", posts.get(0).getTitle());
        assertNotEquals("Test Post 2", posts.get(1).getTitle());
    }
}
