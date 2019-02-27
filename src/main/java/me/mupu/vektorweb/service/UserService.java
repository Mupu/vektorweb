package me.mupu.vektorweb.service;

import me.mupu.vektorweb.persistence.dao.User;
import me.mupu.vektorweb.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public User findUser(String name){
        return repository.findFirstByUsername(name);
    }

}
