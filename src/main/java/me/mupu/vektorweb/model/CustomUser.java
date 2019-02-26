package me.mupu.vektorweb.model;

import lombok.Getter;
import me.mupu.vektorweb.dao.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUser implements UserDetails {

    //todo implement

    @Getter
    private final User user;

    private final Collection<GrantedAuthority> collection;
    public CustomUser(User userdata, Collection<GrantedAuthority> collection) {
        this.user = userdata;
        this.collection = collection;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
