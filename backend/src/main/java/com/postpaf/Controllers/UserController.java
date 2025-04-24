package com.postpaf.Controllers;

import com.postpaf.Dtos.UserDTO;
import com.postpaf.Dtos.UserDTO.AuthenticationDTO;
import com.postpaf.Services.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS},
        allowCredentials = "true"
)
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pseudo/{pseudo}")
    public ResponseEntity<UserDTO> getUserByPseudo(@PathVariable String pseudo) {
        return userService.getUserByPseudo(pseudo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO.UserCreateDTO userCreateDTO) {
        try {
            UserDTO createdUser = userService.createUser(userCreateDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO.UserUpdateDTO userUpdateDTO) {
        try {
            return userService.updateUser(id, userUpdateDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    // Ajoutez ces méthodes à votre UserController.java existant

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDTO, HttpSession session) {
    return userService.authenticateUser(authDTO)
            .map(user -> {
                session.setAttribute("userId", user.getId());
                session.setAttribute("userEmail", user.getEmail());
                return ResponseEntity.ok(user);
            })
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
}

@PostMapping("/logout")
public ResponseEntity<String> logout(HttpSession session) {
    session.invalidate();
    return ResponseEntity.ok("Déconnexion réussie");
}

@GetMapping("/session")
public ResponseEntity<?> checkSession(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId != null) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non connecté");
}
}