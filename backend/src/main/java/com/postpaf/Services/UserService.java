package com.postpaf.Services;

import com.postpaf.Dtos.UserDTO;
import com.postpaf.Repositories.UserRepository;
import com.postpaf.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Méthodes existantes...

    public UserDTO createUser(UserDTO.UserCreateDTO userCreateDTO) {
        // Vérifier si l'email ou le pseudo existe déjà
        if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }
        if (userRepository.existsByPseudo(userCreateDTO.getPseudo())) {
            throw new IllegalArgumentException("Pseudo déjà utilisé");
        }

        User user = new User();
        user.setPseudo(userCreateDTO.getPseudo());
        user.setEmail(userCreateDTO.getEmail());
        
        // Hachage du mot de passe avant de l'enregistrer
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        
        user.setBio(userCreateDTO.getBio());
        user.setImage(userCreateDTO.getImage());
        user.setCreationDate(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    // Méthode utilitaire pour vérifier un mot de passe (à utiliser lors de l'authentification)
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // Autres méthodes existantes...
    
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto);
    }

    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToDto);
    }

    public Optional<UserDTO> getUserByPseudo(String pseudo) {
        return userRepository.findByPseudo(pseudo)
                .map(this::convertToDto);
    }

    public Optional<UserDTO> updateUser(Long id, UserDTO.UserUpdateDTO userUpdateDTO) {
        return userRepository.findById(id)
                .map(user -> {
                    // Vérifier si le nouvel email existe déjà pour un autre utilisateur
                    if (userUpdateDTO.getEmail() != null &&
                            !user.getEmail().equals(userUpdateDTO.getEmail()) &&
                            userRepository.existsByEmail(userUpdateDTO.getEmail())) {
                        throw new IllegalArgumentException("Email déjà utilisé");
                    }

                    // Vérifier si le nouveau pseudo existe déjà pour un autre utilisateur
                    if (userUpdateDTO.getPseudo() != null &&
                            !user.getPseudo().equals(userUpdateDTO.getPseudo()) &&
                            userRepository.existsByPseudo(userUpdateDTO.getPseudo())) {
                        throw new IllegalArgumentException("Pseudo déjà utilisé");
                    }

                    // Mettre à jour les champs
                    if (userUpdateDTO.getPseudo() != null) {
                        user.setPseudo(userUpdateDTO.getPseudo());
                    }
                    if (userUpdateDTO.getEmail() != null) {
                        user.setEmail(userUpdateDTO.getEmail());
                    }
                    if (userUpdateDTO.getBio() != null) {
                        user.setBio(userUpdateDTO.getBio());
                    }
                    if (userUpdateDTO.getImage() != null) {
                        user.setImage(userUpdateDTO.getImage());
                    }
                    // Si le mot de passe est mis à jour
                    if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
                    }

                    User updatedUser = userRepository.save(user);
                    return convertToDto(updatedUser);
                });
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Méthode pour convertir une entité User en DTO
    private UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPseudo(user.getPseudo());
        userDTO.setEmail(user.getEmail());
        userDTO.setBio(user.getBio());
        userDTO.setImage(user.getImage());
        userDTO.setCreationDate(user.getCreationDate());
        return userDTO;
    }
    // Ajoutez cette méthode à votre UserService.java existant

    public Optional<UserDTO> authenticateUser(UserDTO.AuthenticationDTO authDTO) {
        return userRepository.findByEmail(authDTO.getEmail())
            .filter(user -> passwordEncoder.matches(authDTO.getPassword(), user.getPassword()))
            .map(this::convertToDto);
}
}