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
    private UserCollectionService service;
	@Autowired
	private UserService userService;
    @Autowired
	private JwtUtil jwtUtil;

	@GetMapping("")
	//@PreAuthorize("authentication.principal instanceof T(com.astrategy.pokemine.entities.CustomUserDetails) and authentication.principal.id == #userId")
	public ResponseEntity<?> getCards(@AuthenticationPrincipal UserDetails userDetails) {


			User user =userService.findByUsername(userDetails.getUsername());
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Restituisce 404 se l'utente non esiste
			}
			List<UserCollection> collection = service.getUserCollection(user.getId());
			if (collection == null || collection.isEmpty()) {
				return ResponseEntity.ok(Collections.singletonMap("message", "No cards found for this user."));
			}
			return ResponseEntity.ok(collection);
	}

	@PostMapping("add")
	public ResponseEntity<String> addCard(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String cardId) {
		 try {

			User u =userService.findByUsername(userDetails.getUsername());
			 service.addCardToCollection(u.getId(),cardId);
			 return ResponseEntity.ok("Carta aggiunta alla collezione");
		 } catch(Exception e){
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		 }
	}

	 @PostMapping("remove")
	public ResponseEntity<String> removeCard(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String cardId) {

		 User u =userService.findByUsername(userDetails.getUsername());
		 try {
			 service.removeCardToCollection(u.getId(),cardId);
			 return ResponseEntity.ok("Carta rimossa dalla collezione");
		 } catch(Exception e){
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		 }
	    }

}
