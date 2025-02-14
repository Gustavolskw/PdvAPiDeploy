package com.web.service.presentation.controller;

import com.web.service.domain.exception.AlreadyExistsException;
import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.exception.ListEmptyException;
import com.web.service.application.dto.UserRegisterDTO;
import com.web.service.domain.exception.ValidationException;
import com.web.service.presentation.viewModel.ApiResponse;
import com.web.service.presentation.viewModel.UserResponseDTO;
import com.web.service.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
@CrossOrigin()
public class UserController {
    private final UserService userService;


    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
            UserResponseDTO userResponseDTO = userService.createUser(userRegisterDTO);
            return ResponseEntity.ok().body(new ApiResponse("User Created Sucessfully", userResponseDTO));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllUsers() {
        return ResponseEntity.ok().body(new ApiResponse("All Users Sucessfully", userService.findAll().stream().map(UserResponseDTO::new).toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(new ApiResponse("User Found", new UserResponseDTO(userService.findById(id))));
    }



}
