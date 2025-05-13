package com.example.jwtoken.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.jwtoken.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
	User findByEmailAndPassword(String email, String password);

	Optional<User> findByEmail(String email);

}
