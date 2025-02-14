package com.web.service.infra.config;

import com.web.service.domain.model.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    public static AuthUserDetails buildUserDetails(User user) {

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().getName()));

        return new AuthUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
