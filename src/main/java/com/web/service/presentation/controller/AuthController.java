package com.web.service.presentation.controller;

import com.web.service.application.dto.LoginRequestDTO;
import com.web.service.presentation.viewModel.ApiResponse;
import com.web.service.presentation.viewModel.JwtResponse;
import com.web.service.application.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {


    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            JwtResponse response  = authService.login(loginRequestDTO);
            return ResponseEntity.ok().body(new ApiResponse("Login com sucesso!", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Login com erro!", e.getMessage()));
        }
    }


    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestHeader("Authorization") String token) {
        try {
            // Remove o prefixo "Bearer " do token
            String jwtToken = token.replace("Bearer ", "");

            // Chama o serviço de logout, passando o token
            authService.logout(jwtToken);

            return ResponseEntity.ok().body(new ApiResponse("Logout realizado com sucesso!", null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse("Erro ao realizar logout", null));
        }
    }
}
