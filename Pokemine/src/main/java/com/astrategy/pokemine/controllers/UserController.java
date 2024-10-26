package com.astrategy.pokemine.controllers;

import com.astrategy.pokemine.services.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.astrategy.pokemine.entities.User;
import com.astrategy.pokemine.services.UserService;


@RestController // Indicates that this class is a REST controller
@RequestMapping("users") // Maps all requests starting with "/users" to this controller
public class UserController {
	 
	@Autowired // Automatically injects the UserService bean
	private UserService userService;
  	@Autowired
  	private PasswordEncoder passEncode;
  	@Autowired
  	private AuthenticationManager authenticationManager;

  	@Autowired
	private JwtUtil jwtUtil;
    @PostMapping("signup")
    public ResponseEntity<String> add(@RequestBody User user) {
        try {
            user.setPassword(passEncode.encode(user.getPassword()));
            userService.addUser(user); // Calls the service to add the new user
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

	
	// DELETE method to delete a user account
	@DeleteMapping("deleteaccount")
	public ResponseEntity<String> delete(@AuthenticationPrincipal UserDetails userDetails) {
		User u =userService.findByUsername(userDetails.getUsername());
	    try {
	        userService.deleteById(u.getId()); // Calls the service to delete the user by ID
	        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	    } catch (Exception e) {
		// Return error message with a 400 BAD REQUEST if an exception occurs
	        return new ResponseEntity<>("Error: ID not present - " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}

	// GET method to delete a user account
	@GetMapping("deactivate")
	public ResponseEntity<String> deactivate(@AuthenticationPrincipal UserDetails userDetails) {
		User u =userService.findByUsername(userDetails.getUsername());
		try {
			userService.deactivateUser(u.getId()); // Calls the service to deactivate the user by ID
			return new ResponseEntity<>("Account deactivated successfully.", HttpStatus.OK);
		} catch (Exception e) {
			// Return error message with a 400 BAD REQUEST if an exception occurs
			return new ResponseEntity<>("Error: ID not present - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
		
}


