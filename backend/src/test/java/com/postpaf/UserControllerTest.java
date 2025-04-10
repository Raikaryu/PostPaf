package com.postpaf.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postpaf.Dtos.UserDTO;
import com.postpaf.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    // Mock du service utilisateur pour simuler son comportement
    @Mock
    private UserService userService;

    // Injection du mock dans le contrôleur à tester
    @InjectMocks
    private UserController userController;

    // Pour effectuer des requêtes HTTP simulées
    private MockMvc mockMvc;

    // Pour convertir des objets en JSON et vice versa
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // Initialisation avant chaque test
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    /**
     * Test 1: Récupérer tous les utilisateurs
     * Ce test vérifie que la méthode getAllUsers renvoie bien la liste des utilisateurs
     * et un statut HTTP 200 (OK)
     */
    @Test
    public void testGetAllUsers() throws Exception {
        // GIVEN - Préparation des données de test
        UserDTO user1 = new UserDTO();
        user1.setId(1);
        user1.setPseudo("user1");
        user1.setEmail("user1@example.com");

        UserDTO user2 = new UserDTO();
        user2.setId(2);
        user2.setPseudo("user2");
        user2.setEmail("user2@example.com");

        List<UserDTO> userList = Arrays.asList(user1, user2);

        // Configuration du mock: quand getAllUsers est appelé, il retourne notre liste de test
        when(userService.getAllUsers()).thenReturn(userList);

        // WHEN & THEN - Exécution et vérification
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())  // Vérifie que le statut HTTP est 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Vérifie le type de contenu
                .andExpect(jsonPath("$.length()").value(2))  // Vérifie qu'il y a bien 2 éléments
                .andExpect(jsonPath("$[0].id").value(1))  // Vérifie l'ID du premier utilisateur
                .andExpect(jsonPath("$[0].pseudo").value("user1"))  // Vérifie le pseudo du premier utilisateur
                .andExpect(jsonPath("$[1].id").value(2));  // Vérifie l'ID du second utilisateur
    }

    /**
     * Test 2: Récupérer un utilisateur par son ID
     * Ce test vérifie à la fois le cas où l'utilisateur existe (200 OK)
     * et le cas où il n'existe pas (404 Not Found)
     */
    @Test
    public void testGetUserById() throws Exception {
        // GIVEN - Préparation des données de test
        UserDTO user = new UserDTO();
        user.setId(1);
        user.setPseudo("testUser");
        user.setEmail("test@example.com");

        // Configuration du mock pour un ID existant
        when(userService.getUserById(1)).thenReturn(Optional.of(user));

        // Configuration du mock pour un ID inexistant
        when(userService.getUserById(999)).thenReturn(Optional.empty());

        // WHEN & THEN - Test avec un ID existant
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())  // Vérifie que le statut HTTP est 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pseudo").value("testUser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        // WHEN & THEN - Test avec un ID inexistant
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());  // Vérifie que le statut HTTP est 404 Not Found
    }

    /**
     * Test 3: Créer un nouvel utilisateur
     * Ce test vérifie la création d'un utilisateur avec succès (201 Created)
     * et la gestion d'erreur (400 Bad Request)
     */
    @Test
    public void testCreateUser() throws Exception {
        // GIVEN - Préparation des données de test
        UserDTO.UserCreateDTO createDTO = new UserDTO.UserCreateDTO();
        createDTO.setPseudo("newUser");
        createDTO.setEmail("newuser@example.com");
        createDTO.setPassword("password123");

        UserDTO createdUser = new UserDTO();
        createdUser.setId(3);
        createdUser.setPseudo("newUser");
        createdUser.setEmail("newuser@example.com");

        // Configuration du mock pour une création réussie
        when(userService.createUser(any(UserDTO.UserCreateDTO.class))).thenReturn(createdUser);

        // Configuration du mock pour une erreur de validation (email déjà utilisé par exemple)
        when(userService.createUser(eq(createDTO))).thenThrow(new IllegalArgumentException("Email déjà utilisé"));

        // WHEN & THEN - Test de création réussie (avec un objet différent pour éviter le conflit avec le mock précédent)
        UserDTO.UserCreateDTO anotherCreateDTO = new UserDTO.UserCreateDTO();
        anotherCreateDTO.setPseudo("anotherUser");
        anotherCreateDTO.setEmail("another@example.com");
        anotherCreateDTO.setPassword("password123");

        when(userService.createUser(eq(anotherCreateDTO))).thenReturn(createdUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(anotherCreateDTO)))
                .andExpect(status().isCreated())  // Vérifie que le statut HTTP est 201 Created
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.pseudo").value("newUser"));

        // WHEN & THEN - Test de gestion d'erreur
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest());  // Vérifie que le statut HTTP est 400 Bad Request
    }
}