package com.web.service.presentation.controller;

import com.web.service.application.service.RoleService;
import com.web.service.domain.exception.AlreadyExistsException;
import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.exception.ListEmptyException;
import com.web.service.presentation.viewModel.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/role")
@Slf4j
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<ApiResponse> GetAllRoles() {
        return ResponseEntity.ok().body(new ApiResponse("Success",roleService.findAll()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> GetRoleById(@PathVariable("id") Long id) {
            return ResponseEntity.ok().body(new ApiResponse("Success",roleService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> CreateRole(@RequestBody String role) {
            return ResponseEntity.ok().body(new ApiResponse("Success",roleService.create(role)));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> UpdateRole(@PathVariable("id") Long id, @RequestBody String role) {
            return ResponseEntity.ok().body(new ApiResponse("Success",roleService.update(id, role)));
    }
}
