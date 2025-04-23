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
    }

    @Test
    @DisplayName("Test recuperation de tous les posts")
    void testGetAllPosts() {

        // Créer des utilisateurs pour les posts
        User user1 = new User();
        user1.setId(1L);
        user1.setPseudo("User1");
        
        User user2 = new User();
        user2.setId(2L);
        user2.setPseudo("User2");

        // Créer des posts avec toutes les propriétés nécessaires
        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Test Post 1");
        post1.setIdUser(1L); // Associer au User1
        post1.setContenu("Contenu du post 1");
        post1.setCreationDate(LocalDateTime.now());

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Test Post 2");
        post2.setIdUser(2L); // Associer au User2
        post2.setContenu("Contenu du post 2");
        post2.setCreationDate(LocalDateTime.now());

        // Configurer les mocks
        when(postRepository.findAll()).thenReturn(Arrays.asList(post1, post2));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));

        // Act
        List<PostDTO> posts = postService.getAllPosts();

        // Assert
        assertEquals(2, posts.size());
        assertEquals("Test Post 1", posts.get(0).getTitle());
        assertEquals("Test Post 2", posts.get(1).getTitle());
    }
}