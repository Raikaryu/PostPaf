package com.postpaf.Repositories;

import com.postpaf.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPseudo(String pseudo);

    boolean existsByEmail(String email);

    boolean existsByPseudo(String pseudo);
}
