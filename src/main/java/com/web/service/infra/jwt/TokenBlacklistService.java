package com.web.service.infra.jwt;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistService {

    private final Set<String> blacklistedTokens = new HashSet<>(); // Usando um conjunto em memória como exemplo

    // Método para verificar se o token está na blacklist
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    // Método para adicionar um token à blacklist
    public void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }
}
