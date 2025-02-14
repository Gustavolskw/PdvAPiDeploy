package com.web.service.infra.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.service.presentation.viewModel.ApiResponse;
import com.web.service.infra.config.AuthUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class AuthTokenFilter extends OncePerRequestFilter {


    private   JwtUtils jwtUtils;


    private  AuthUserDetailsService userDetailsService;


    private  TokenBlacklistService tokenBlacklistService; // Serviço para verificar blacklist

    public AuthTokenFilter(JwtUtils jwtUtils, AuthUserDetailsService userDetailsService, TokenBlacklistService tokenBlacklistService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public AuthTokenFilter(){

    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

            // Verifica se o JWT está presente e é válido
            if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {

                // Verifica se o token está na blacklist
                if (tokenBlacklistService.isTokenBlacklisted(jwt)) {
                    setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, "Token revogado", "Token foi revogado. Faça login novamente.");
                    return;
                }

                // Se o token for válido e não estiver na blacklist, obtenha o username
                String username = jwtUtils.getUsernameFromToken(jwt);

                // Carrega os detalhes do usuário
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Cria o objeto de autenticação
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Define o contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (JwtException e) {
            // Se o token for inválido ou expirado, retorna erro 401
            setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, e.getMessage(), "Token inválido ou expirado. Faça login e tente novamente.");
            return;
        } catch (Exception e) {
            // Para outros erros internos
            setErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response, e.getMessage(), "Erro interno no servidor.");
            return;
        }

        // Continua o processamento da requisição
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    // Método auxiliar para configurar a resposta de erro em formato JSON.
    private void setErrorResponse(int status, HttpServletResponse response, String technicalMessage, String userMessage) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        ApiResponse apiResponse = new ApiResponse(userMessage, technicalMessage);
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }
}
