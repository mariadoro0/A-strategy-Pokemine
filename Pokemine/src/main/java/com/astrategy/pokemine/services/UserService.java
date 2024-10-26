package com.astrategy.pokemine.services;

import java.util.List;
import java.util.Optional;

import com.astrategy.pokemine.entities.User;

public interface UserService {

	 // Adds a new user to the system
	void addUser(User user);
	User findByUsername(String username);
	// Retrieves a user by their email address
	User getByEmail(String email);

	// Retrieves a user by their unique user ID, wrapped in an Optional
	Optional<User> getUserById(int userId);
	
	// Deletes a user by their unique user ID
	void deleteById(int id);
	void deactivateUser(int userId);
}
