package com.astrategy.pokemine.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

import com.astrategy.pokemine.entities.CustomUserDetails;
import com.astrategy.pokemine.entities.User;
import com.astrategy.pokemine.services.JwtUtil;
import com.astrategy.pokemine.services.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.astrategy.pokemine.entities.UserCollection;
import com.astrategy.pokemine.services.UserCollectionService;

@RestController
@RequestMapping("collection")
public class UserCollectionController {

	@Autowired
	private UserCollectionService service; // Service for managing user collections

	@Autowired
	private UserService userService; // Service for user-related operations


	// Endpoint to retrieve the user's card collection
	@GetMapping("")
	public ResponseEntity<?> getCards(@AuthenticationPrincipal UserDetails userDetails) {
		User user = userService.findByUsername(userDetails.getUsername()); // Retrieve the user by username
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if user does not exist
		}
		List<UserCollection> collection = service.getUserCollection(user.getId()); // Get user's card collection
		if (collection == null || collection.isEmpty()) {
			return ResponseEntity.ok(Collections.singletonMap("message", "No cards found for this user.")); // Return message if collection is empty
		}
		return ResponseEntity.ok(collection); // Return the user's card collection
	}

	// Endpoint to add a card to the user's collection
	@PostMapping("add")
	public ResponseEntity<String> addCard(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String cardId) {
		try {
			User u = userService.findByUsername(userDetails.getUsername()); // Retrieve the user by username
			service.addCardToCollection(u.getId(), cardId); // Add the card to the user's collection
			return ResponseEntity.ok("Card added to collection"); // Return success message
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // Return error message in case of an exception
		}
	}

	// Endpoint to remove a card from the user's collection
	@PostMapping("remove")
	public ResponseEntity<String> removeCard(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String cardId) {
		User u = userService.findByUsername(userDetails.getUsername()); // Retrieve the user by username
		try {
			service.removeCardToCollection(u.getId(), cardId); // Remove the card from the user's collection
			return ResponseEntity.ok("Card removed from collection"); // Return success message
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Return 404 with error message if the card is not found
		}
	}

}
