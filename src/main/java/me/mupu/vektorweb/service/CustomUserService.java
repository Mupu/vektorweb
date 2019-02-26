package me.mupu.vektorweb.service;

import me.mupu.vektorweb.dao.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // todo implement
        return null;
    }

    public User findUserByUsername(String username) {
        return null;//todo implement
    }

    public User findUserByEmail(String email) {
        return null; //todo implement
    }

    public User findUserByConfimationToken(String token) {
        return null; // todo implement
    }

    public User findUserByResetPasswordToken(String token) {
        return null; // todo implement
    }

}
