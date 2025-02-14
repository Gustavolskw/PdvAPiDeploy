package com.web.service.domain.setup;

import com.web.service.domain.model.Role;
import com.web.service.domain.model.User;
import com.web.service.domain.repository.RoleRepository;
import com.web.service.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Transactional
@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            createDefaultRoleIfNotExits();
            createDefaultAdminIfNotExits();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void createDefaultRoleIfNotExits(){
        if(roleRepository.findByName("ADMIN").isEmpty()){
            Role roleAdmin = new Role("ADMIN");
            roleRepository.save(roleAdmin);
        }
        if(roleRepository.findByName("GUIDER").isEmpty()){
            Role roleUser = new Role("GUIDER");
            roleRepository.save(roleUser);
        }
    }

    private void createDefaultAdminIfNotExits(){
        Optional<User> userEmail  = Optional.ofNullable(userRepository.findByEmail("admin@gmail.com"));
        Optional<Role> role = roleRepository.findById(1L);
        if(role.isPresent()){
            if(userEmail.isEmpty()){
                User user = new User();
                user.setPassword(passwordEncoder.encode("123456"));
                user.setUsername("admin");
                user.setEmail("admin@gmail.com");
                user.setStatus(true);
                user.setRole(role.get());
                userRepository.save(user);
            }
        }

    }


}
