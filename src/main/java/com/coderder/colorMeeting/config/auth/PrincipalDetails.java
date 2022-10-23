package com.coderder.colorMeeting.config.auth;

import com.coderder.colorMeeting.model.UserTest;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {

    private UserTest userTest;

    public PrincipalDetails(UserTest userTest) {
        this.userTest = userTest;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        userTest.getRoleList().forEach(r -> {
            authorities.add(()->{ return r;});
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return userTest.getPassword();
    }

    @Override
    public String getUsername() {
        return userTest.getUsername();
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
