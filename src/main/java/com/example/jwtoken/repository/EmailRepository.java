package com.example.jwtoken.repository;

import com.example.jwtoken.entity.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends CrudRepository<Email, Long> {

    Optional<Email> findByEmail(String email);
}
