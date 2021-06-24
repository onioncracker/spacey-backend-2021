package com.heroku.spacey.entity;

import com.heroku.spacey.utils.Status;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Data
public class User implements UserDetails, Serializable {
    private Long userId;
    private Long tokenId;
    private String email;
    private String firstName;
    private String lastName;
    private String userRole;
    private long roleId;
    private long statusId;
    private String phoneNumber;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole));
    }

    @Override
    public String getUsername() {
        return email;
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
        return statusId == Status.ACTIVATED.getValue();
    }
}
