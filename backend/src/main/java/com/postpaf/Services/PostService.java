package com.postpaf.Services;

import com.postpaf.Dtos.PostDTO;
import com.postpaf.Models.Post;
import com.postpaf.Models.User;
import com.postpaf.Repositories.PostRepository;
import com.postpaf.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<PostDTO> getPostById(Long id) {
        return postRepository.findById(id)
                .map(this::convertToDto);
    }

    public List<PostDTO> getPostsByUserId(Integer userId) {
        return postRepository.findByIdUser(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<PostDTO> searchPostsByTitle(String keyword) {
        return postRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<PostDTO> createPost(PostDTO.PostCreateDTO postCreateDTO) {
        // Vérifier si l'utilisateur existe
        if (!userRepository.existsById(postCreateDTO.getIdUser())) {
            throw new IllegalArgumentException("L'utilisateur n'existe pas");
        }

        Post post = new Post();
        post.setIdUser(postCreateDTO.getIdUser());
        post.setTitle(postCreateDTO.getTitle());
        post.setContenu(postCreateDTO.getContenu());
        post.setCreationDate(LocalDateTime.now());

        Post savedPost = postRepository.save(post);
        return Optional.of(convertToDto(savedPost));
    }

    public Optional<PostDTO> updatePost(Long id, PostDTO.PostUpdateDTO postUpdateDTO) {
        return postRepository.findById(id)
                .map(post -> {
                    if (postUpdateDTO.getTitle() != null) {
                        post.setTitle(postUpdateDTO.getTitle());
                    }
                    if (postUpdateDTO.getContenu() != null) {
                        post.setContenu(postUpdateDTO.getContenu());
                    }

                    Post updatedPost = postRepository.save(post);
                    return convertToDto(updatedPost);
                });
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Méthode pour convertir une entité Post en DTO
    private PostDTO convertToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setIdUser(post.getIdUser());
        postDTO.setTitle(post.getTitle());
        postDTO.setContenu(post.getContenu());
        postDTO.setCreationDate(post.getCreationDate());

        // Récupérer le pseudo de l'utilisateur
        Optional<User> userOpt = userRepository.findById(post.getIdUser());
        userOpt.ifPresent(user -> postDTO.setUserPseudo(user.getPseudo()));

        return postDTO;
    }
}
