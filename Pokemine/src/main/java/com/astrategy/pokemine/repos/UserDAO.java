package com.astrategy.pokemine.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.astrategy.pokemine.entities.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer>{
	
	// Method to find a User by their email address.
	User findByEmail(String email);
	User findByUsername(String username);
	// Method to delete a User by their ID.
	void deleteById(int id);	
	
	// Method to check if a User exists by their email address.
	boolean existsByEmail(String email);
    
	// Method to check if a User exists by their username.
	boolean existsByUsername(String username);
	
	
	
}
