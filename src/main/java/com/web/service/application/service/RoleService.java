package com.web.service.application.service;

import com.web.service.domain.exception.AlreadyExistsException;
import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.exception.ListEmptyException;
import com.web.service.domain.exception.UnprocessableAction;
import com.web.service.domain.model.Role;
import com.web.service.domain.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> findAll() {
        List<Role> roles =  roleRepository.findAll();
        if(roles.isEmpty())throw new ListEmptyException("Roles List is Empty");
        return roles;
    }

    public Role findById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if(role.isPresent())return role.get();
        throw new EntityNotFoundException("Role Not Found");
    }
    @Transactional
    public Role create(String role) {
        if(roleRepository.findByName(role).isPresent())throw new AlreadyExistsException("Role Name already Exists");
        Role roleEntity = new Role();
        roleEntity.setName(role);
        return roleRepository.save(roleEntity);
    }
    @Transactional
    public Role update(Long id, String roleName) {
        Optional<Role> roleEntity = roleRepository.findById(id);
        if(roleEntity.isEmpty())throw new EntityNotFoundException("Role Not Found");
        if(roleRepository.findByName(roleName).isPresent())throw new AlreadyExistsException("Role Name already Exists");
        roleEntity.get().setName(roleName);
        return roleRepository.save(roleEntity.get());
    }
    @Transactional
    public void delete(Long id) {
        Optional<Role> roleEntity = roleRepository.findById(id);
        if(roleEntity.isEmpty())throw new EntityNotFoundException("Role Not Found");
        if(roleEntity.get().getId().equals(1L))throw new UnprocessableAction("Role Is not able to delete");
        roleRepository.deleteById(id);
    }
}
