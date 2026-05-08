package com.tmdtud.cuahang.api.auth.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tmdtud.cuahang.api.employer.model.Employers;

import lombok.Getter;

@Getter
public class EmployerDetails implements UserDetails {
 
    private Employers employer;

    public EmployerDetails(Employers user) {
        this.employer = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_STAFF"));
    }

    @Override
    public String getPassword() {
        return employer.getPassword();
    }

    @Override
    public String getUsername() {
        return employer.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
