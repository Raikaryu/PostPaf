package com.postpaf;

import com.postpaf.Dtos.UserDTO;
import com.postpaf.Models.User;
import com.postpaf.Repositories.UserRepository;
import com.postpaf.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test création d'un utilisateur")
    void testCreateUser() {
        // Arrange
        UserDTO.UserCreateDTO userCreateDTO = new UserDTO.UserCreateDTO();
        userCreateDTO.setPseudo("Arthouuur !!");
        userCreateDTO.setEmail("Arthouuur@exemple.fr");
        userCreateDTO.setPassword("1234");
        userCreateDTO.setBio("Roy de Kamelott");
        userCreateDTO.setImage("http");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setPseudo("Arthouuur !!");
        savedUser.setEmail("Arthouuur@exemple.fr");
        savedUser.setBio("Roy de Kamelott");
        savedUser.setImage("http");
        savedUser.setCreationDate(LocalDateTime.now());

        // Configuration des mocks
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByPseudo(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserDTO createdUser = userService.createUser(userCreateDTO);

        // Assert
        assertNotNull(createdUser);
        assertEquals(1L, createdUser.getId());
        assertEquals("Arthouuur !!", createdUser.getPseudo());
        assertEquals("Arthouuur@exemple.fr", createdUser.getEmail());
        assertEquals("Roy de Kamelott", createdUser.getBio());
        assertEquals("http", createdUser.getImage());
        assertNotNull(createdUser.getCreationDate());

        // Vérification des appels aux mocks
        verify(userRepository).existsByEmail("Arthouuur@exemple.fr");
        verify(userRepository).existsByPseudo("Arthouuur !!");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Test création d'un utilisateur avec email déjà existant")
    void testCreateUserWithExistingEmail() {
        // Arrange
        UserDTO.UserCreateDTO userCreateDTO = new UserDTO.UserCreateDTO();
        userCreateDTO.setPseudo("Arthouuur !!");
        userCreateDTO.setEmail("Arthouuur@exemple.fr");
        userCreateDTO.setPassword("1234");
        userCreateDTO.setBio("Roy de Kamelott");
        userCreateDTO.setImage("http");

        // Configuration des mocks
        when(userRepository.existsByEmail("Arthouuur@exemple.fr")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userCreateDTO);
        });

        // Vérification que save n'a pas été appelé
        verify(userRepository, never()).save(any(User.class));
    }
    @Test
    @DisplayName("Test création d'un utilisateur avec pseudo déjà existant")
    void testCreateUserWithExistingPseudo() {

        // Arrange
        UserDTO.UserCreateDTO userCreateDTO = new UserDTO.UserCreateDTO();
        userCreateDTO.setPseudo("Arthouuur !!");
        userCreateDTO.setEmail("Arthouuur@exemple.fr");
        userCreateDTO.setPassword("1234");
        userCreateDTO.setBio("Roy de Kamelott");
        userCreateDTO.setImage("http");

        // Configuration des mocks
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByPseudo("Arthouuur !!")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userCreateDTO);
        });
        
        // Vérification que save n'a pas été appelé
        verify(userRepository, never()).save(any(User.class));
    }
}






