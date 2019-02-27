package me.mupu.vektorweb.persistence.repository;

import me.mupu.vektorweb.persistence.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByUsername(String username);

}
