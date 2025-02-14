package com.web.service.domain.repository;

import com.web.service.domain.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(@NotBlank String email);


    List<User> findByUsernameLike(String username);
}
