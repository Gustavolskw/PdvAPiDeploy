package com.web.service.service;

import com.web.service.application.dto.UserRegisterDTO;
import com.web.service.application.service.UserService;
import com.web.service.domain.exception.ValidationException;
import com.web.service.domain.model.Role;
import com.web.service.domain.model.User;
import com.web.service.domain.repository.RoleRepository;
import com.web.service.domain.repository.UserRepository;
import com.web.service.domain.validation.UserDataValidation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserDataValidation userDataValidation;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Nested
    class CreateUser{

        @Test
        @DisplayName("Should Create a User with Success")
        public void shouldCreateUser(){

            /// ARRANGE
            Role roleTestGuide  = new Role(2L, "GUIDE");
            doReturn(Optional.of(roleTestGuide)).when(roleRepository).findById(2L);
           User user = new User("Gustavo", passwordEncoder.encode("123456"), "Gustavo@email.com", roleTestGuide, true);
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new UserRegisterDTO("Gustavo", "Gustavo@email.com", "1234567", 2);

            /// ACT
            var result = userService.createUser(input);

            /// Asserts
            assertNotNull(result);
            assertEquals(input.email(), userArgumentCaptor.getValue().getEmail());
            assertEquals(input.username(), userArgumentCaptor.getValue().getUsername());
            assertEquals(true, userArgumentCaptor.getValue().getStatus());
            assertEquals(roleTestGuide.getName(), userArgumentCaptor.getValue().getRole().getName());
            verify(userRepository, times(1)).save(userArgumentCaptor.capture());
            verify(roleRepository, times(1)).findById(2L);
        }

        @Test
        @DisplayName("Should throw Validation Error")
        public void shouldThrowValidationError(){


            // Explicitly mock the validation methods to throw an exception
            doThrow(new ValidationException("Invalid email"))
                    .when(userDataValidation)
                    .validateEmail(anyString());


            // Prepare input with potentially invalid data
            var input = new UserRegisterDTO("Gustavo", "Gustavoodnfidsni", "122323", 2);

            // Act & Assert
            assertThrows(ValidationException.class, () -> {
                // Add some logging or breakpoint here to see what's happening
                System.out.println("Attempting to create user with input: " + input);
                userService.createUser(input);
            });

            // Verify that validation methods were called
            verify(userDataValidation, times(1)).validateEmail(input.email());
            assertThrowsExactly(ValidationException.class, () -> {
                userDataValidation.validateEmail(input.email());
            });
            verify(userDataValidation, times(0)).validateUsername(input.username());
            verify(userDataValidation, times(0)).validatePassword(input.password());
        }
    }


}
