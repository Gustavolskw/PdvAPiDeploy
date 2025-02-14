package com.web.service.application.service;

import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.model.Role;
import com.web.service.domain.model.User;
import com.web.service.domain.exception.AlreadyExistsException;
import com.web.service.domain.exception.ListEmptyException;
import com.web.service.domain.repository.RoleRepository;
import com.web.service.domain.repository.UserRepository;
import com.web.service.application.dto.UserRegisterDTO;
import com.web.service.domain.validation.UserDataValidation;
import com.web.service.presentation.viewModel.UserResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDataValidation userDataValidation;


    public List<User> findAll() throws ListEmptyException {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            // Throwing the ListEmptyException directly
            throw new ListEmptyException("User List is empty");
        }
        // Returning mapped list of UserResponseDTOs
        return userList;
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new EntityNotFoundException("User Not Found");
        return user.get();
    }

    public List<UserResponseDTO> findByUsername(String username) {
        // Verifica se há usuários correspondentes ao username
        List<User> userList = userRepository.findByUsernameLike(username);

        // Se não encontrar usuários, lança a exceção
        if (userList.isEmpty()) {
            throw new EntityNotFoundException("User Not Found");
        }

        // Converte a lista de usuários para uma lista de UserResponseDTO
        return userList.stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    public UserResponseDTO createUser(UserRegisterDTO userRegisterDTO) {
            userDataValidation.validateEmail(userRegisterDTO.email());
            userDataValidation.validateUsername(userRegisterDTO.username());
            userDataValidation.validatePassword(userRegisterDTO.password());
            Optional<UserResponseDTO> userRegistered = Optional.of(userRegisterDTO)
                    .filter(user-> !userRepository.existsByEmail(user.email()))
                    .map(user->{
                        User newUser = buildUser(user);
                        userRepository.save(newUser);
                        return new UserResponseDTO(newUser);
                    });
            return userRegistered.orElseThrow(()->new AlreadyExistsException("User Already Exists"));
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRegisterDTO userRegisterDTO) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new EntityNotFoundException("User Not Found");
        User userUpdate  = user.get();
        userDataValidation.validateEmail(userRegisterDTO.email());
        userDataValidation.validateUsername(userRegisterDTO.username());
        userDataValidation.validatePassword(userRegisterDTO.password());
        userUpdate.setEmail(userRegisterDTO.email());
        userUpdate.setPassword(passwordEncoder.encode(userRegisterDTO.password()));
        userUpdate.setUsername(userRegisterDTO.username());

        return new UserResponseDTO(userUpdate);
    }

    public User buildUser(UserRegisterDTO userRegister){
        Role role = roleRepository.findById(Long.valueOf(userRegister.role())).orElse(null);
        User userEntity = new User();
        userEntity.setEmail(userRegister.email());
        userEntity.setPassword(passwordEncoder.encode(userRegister.password()));
        userEntity.setUsername(userRegister.username());
        userEntity.setRole(role != null ? role : getSecondaryRole());
        userEntity.setStatus(true);
        return userEntity;
    }


    private Role getSecondaryRole(){
        Optional<Role> role = roleRepository.findById(2L);
        if(role.isEmpty()) throw new EntityNotFoundException("Role Not Found");
        return role.get();
    }
}
