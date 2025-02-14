package com.web.service.domain.validation;

import com.web.service.domain.exception.AlreadyExistsException;
import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.exception.ValidationException;
import com.web.service.domain.repository.RoleRepository;
import com.web.service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDataValidationImpl implements UserDataValidation {

    // Expressão regular para validar o formato do email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void validateEmail(String email) {
        if (email== null || email.isEmpty()) {
            throw new ValidationException("Email não pode ser nulo ou vazio.");
        }
        if(userRepository.existsByEmail(email)){
            throw new AlreadyExistsException("Credencial de Cadastro Inválida!");
        }
        // Verifica se o email está no formato correto
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new ValidationException("O email fornecido é inválido.");
        }
    }

    @Override
    public void validatePassword(String password) {
        if (password== null || password.isEmpty()) {
            throw new ValidationException("Senha não pode ser nulo ou vazio.");
        }
        if(password.length() < 4) {
            throw new ValidationException("Senha deve conter mais de 4 caracteres.");
        }
    }

    @Override
    public void validateUsername(String username) {
        if (username== null || username.isEmpty()) {
            throw new ValidationException("Nome do usuario não pode ser nulo ou vazio.");
        }
        if(username.length() < 5) {
            throw new ValidationException("Nome do usuario deve conter mais de 6 caracteres.");
        }
    }

    @Override
    public void validateRole(Long roleId) {
        if(roleId == null || roleId <= 0) throw new  ValidationException("Role deve ser Informado");
        if(roleRepository.findById(roleId).isEmpty()) throw new EntityNotFoundException("Role não encontrado!");
    }
}
