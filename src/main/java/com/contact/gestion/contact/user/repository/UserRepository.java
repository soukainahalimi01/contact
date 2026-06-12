package com.contact.gestion.contact.user.repository;

import com.contact.gestion.contact.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}