package com.astrategy.pokemine.controllers;

import com.astrategy.pokemine.entities.CustomUserDetails;
import com.astrategy.pokemine.services.JwtUtil;
import com.astrategy.pokemine.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astrategy.pokemine.entities.User;
import com.astrategy.pokemine.services.UserService;


@RestController
@RequestMapping("users")
public class UserController {
	 
	@Autowired
	private UserService userService;
	@Autowired
	private UsersServiceImpl userimp;
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
            userService.addUser(user);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

	
	
	@GetMapping("deleteaccount")
	public ResponseEntity<String> delete(@AuthenticationPrincipal UserDetails userDetails) {
		User u =userService.findByUsername(userDetails.getUsername());
	    try {
	        userService.deleteById(u.getId());
	        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Error: ID not present - " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
		
}


