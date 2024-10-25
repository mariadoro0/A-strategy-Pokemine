package com.astrategy.pokemine.controllers;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.astrategy.pokemine.services.CardService;
@RestController // Indicates that this class is a REST controller
@RequestMapping("cards") // Maps HTTP requests to /cards URL path
public class CardController {
	@Autowired
	private CardService service;
	
	// HTTP GET method to retrieve cards based on various optional filters
	@GetMapping
	public ResponseEntity<?> getCardByFilters(
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String series,
			@RequestParam(required = false) String artist,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String set,
			@RequestParam(required = false) String generation,
			@RequestParam(required = false) String rarity, 
			@RequestParam(required = false) String supertype,
			@RequestParam(required = false, defaultValue = "1") int page
			) {
		try {
			// Calls the service method to get filtered cards and returns them in the response
			return new ResponseEntity<>(service.getByFilters(id, name, series, artist, type, set, generation, rarity, supertype, page), HttpStatus.OK);
		} catch (IllegalArgumentException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}	
	
		
	

}