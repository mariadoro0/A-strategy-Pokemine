package com.astrategy.pokemine.controllers;

import java.security.Principal;
import java.util.List;

import com.astrategy.pokemine.entities.CustomUserDetails;
import com.astrategy.pokemine.entities.User;
import com.astrategy.pokemine.services.UserService;
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
    

	@GetMapping("")
	//@PreAuthorize("authentication.principal instanceof T(com.astrategy.pokemine.entities.CustomUserDetails) and authentication.principal.id == #userId")
	public ResponseEntity<?> getCards(@AuthenticationPrincipal UserDetails userDetails) {

		 User u =userService.findByUsername(userDetails.getUsername());
		List<UserCollection> collection = service.getUserCollection(u.getId());
		return ResponseEntity.ok(collection);
		 /*if(userService.checkAuthorization(userId, principal)){
			List<UserCollection> collection = service.getUserCollection(userId);
			if (collection == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Restituisce 404 se non trovata e potremmo anche personalizzare la pagina 404
			}
			return new ResponseEntity<>(collection, HttpStatus.OK); // Restituisce 200 con la collezione
		} else {
			 return new ResponseEntity<>("Non puoi accedere ai dati di altri utenti.",HttpStatus.FORBIDDEN);
		 } */

	}

	@PostMapping("add")
	public ResponseEntity<String> addCard(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam String cardId) {
		 try {

			User u =userService.findByUsername(userDetails.getUsername());
			 service.addCardToCollection(u.getId(),cardId);
			 return ResponseEntity.ok("Carta aggiunta alla collezione");
		 } catch(Exception e){
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("iii");
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
