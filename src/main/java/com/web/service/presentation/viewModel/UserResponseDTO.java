package com.web.service.presentation.viewModel;

import com.web.service.domain.model.User;

public record UserResponseDTO(Long id, String username, String email, Boolean status,  String role) {
    public UserResponseDTO(User user){
        this(user.getId(), user.getUsername(), user.getEmail(), user.getStatus(), user.getRole().getName() );
    }
}
