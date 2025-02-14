package com.web.service.domain.validation;
public interface UserDataValidation {

    void validateEmail(String email);
    void validatePassword(String password);
    void validateUsername(String username);
    void validateRole(Long roleId);
}
