package com.astrategy.pokemine.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {
    private final User user; // The User object representing the authenticated user

    public CustomUserDetails(User user) {
        this.user = user; // Constructor to initialize the CustomUserDetails with a User object
    }

    // Getter for the user ID, allows access to the authenticated user's ID
    public int getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Returns an empty list of authorities; modify as needed to include user roles/permissions
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Returns the user's password
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // Returns the user's username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Indicates whether the account is non-expired; here it is always true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Indicates whether the account is non-locked; here it is always true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Indicates whether the credentials are non-expired; here it is always true
    }

    @Override
    public boolean isEnabled() {
        return true; // Indicates whether the account is enabled; here it is always true
    }

}
