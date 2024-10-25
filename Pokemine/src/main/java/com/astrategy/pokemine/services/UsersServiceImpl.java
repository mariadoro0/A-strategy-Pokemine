package com.astrategy.pokemine.services;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.astrategy.pokemine.entities.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.astrategy.pokemine.entities.User;
import com.astrategy.pokemine.repos.UserDAO;


@Service
public class UsersServiceImpl implements UserService, UserDetailsService {
	@Autowired
	private UserDAO dao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user= dao.findByUsername(username);
      return new CustomUserDetails(user);
  }

 	@Override
	public void addUser(User user) {

		boolean existingUserByEmail = dao.existsByEmail(user.getEmail());
		if(existingUserByEmail) {
			throw new RuntimeException("Un utente esiste già con questa email");
		}

		boolean existingUserByUsername = dao.existsByUsername(user.getUsername());
		if(existingUserByUsername) {
			throw new RuntimeException("Un utente esiste già con questa email");
		}

		dao.save(user);

	}


	// Method to retrieve a user by their email address
	@Override
	public User getByEmail(String email) {

		return dao.findByEmail(email);
	}

	@Override
	public User findByUsername(String username) {
		return dao.findByUsername(username);
	}



	// Method to retrieve a user by their ID
	@Override
	public Optional<User> getUserById(int userId) {
		return dao.findById(userId);
	}



	// Method to delete a user by their ID, with a check if the user exists
	@Override
	public void deleteById(int id) {

		 Optional<User> existingUser = dao.findById(id);

		   if (existingUser.isEmpty()) {

		        throw new RuntimeException("L'utente non esiste e non può essere cancellato");

		    }

		   dao.deleteById(id);
	}

	@Override
	public void deactivateUser(int userId) {

	}


}
