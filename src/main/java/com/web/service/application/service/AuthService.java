package com.web.service.application.service;

import com.web.service.domain.repository.UserRepository;
import com.web.service.application.dto.LoginRequestDTO;
import com.web.service.presentation.viewModel.JwtResponse;
import com.web.service.infra.config.AuthUserDetails;
import com.web.service.infra.jwt.JwtUtils;
import com.web.service.infra.jwt.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final TokenBlacklistService tokenBlacklistService;
    private final UserRepository userRepository;


    public JwtResponse login(LoginRequestDTO userLoginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.email(), userLoginDTO.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jtwToken = jwtUtils.generateTokenForUser(authentication);

        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();

        return new JwtResponse(userDetails.getId(), jtwToken);
    }

    public void logout(String jwtToken) {
        // Adiciona o token à blacklist
        tokenBlacklistService.addToBlacklist(jwtToken);

        // Limpa a autenticação no SecurityContext
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
