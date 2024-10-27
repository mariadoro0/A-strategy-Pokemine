package com.astrategy.pokemine.controllers;

import com.astrategy.pokemine.entities.CustomUserDetails;
import com.astrategy.pokemine.entities.Deck;
import com.astrategy.pokemine.entities.User;
import com.astrategy.pokemine.services.DeckService;
import com.astrategy.pokemine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController // Indicates that this class is a REST controller
@RequestMapping("decks") // Maps HTTP requests to /decks URL path
public class DeckController {
    @Autowired
    private DeckService deckService;
    @Autowired
    private UserService userService;

    // HTTP GET method to retrieve all decks for a specific user
    @GetMapping("")
    public ResponseEntity<?> getUserDecks(@AuthenticationPrincipal CustomUserDetails userDetails) {

        User u =userService.findByUsername(userDetails.getUsername());
        if(u != null) {
            return new ResponseEntity<>(deckService.getDecksByUser(u.getId()), HttpStatus.OK); // Returns the list of decks for the user
        }else {
            return new ResponseEntity<>("Not Auth found",HttpStatus.BAD_REQUEST);
        }
    }
    // Returns the list of decks for the user
    @PostMapping("newdeck")
    public ResponseEntity<String> createDeck(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam String name, @RequestParam String description){
        User u =userService.findByUsername(userDetails.getUsername());
        try {
            deckService.createDeck(u.getId(), name, description); // Calls the service to create a new deck
            return new ResponseEntity<>("New deck created succesfully.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Returns error message if an exception occurs
        }
    }
    // HTTP GET method to retrieve cards in a specific deck
    @ResponseBody // Indicates that the method's return value should be written directly to the HTTP response body
    @GetMapping("{deckId}")
    public ResponseEntity<?> getDeckCards(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int deckId) {
        User u =userService.findByUsername(userDetails.getUsername());
        try {

          //  Map<String, Integer> deckCards = deckService.getDeckCardsByDeckId(u.getId(), deckId); // Retrieves cards of the specified deck
            return new ResponseEntity<>(deckService.getDeckCardsByDeckId(u.getId(), deckId), HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
     // HTTP GET method to validate a specific deck
    @GetMapping("{deckId}/validate")
    public ResponseEntity<String> validateDeck(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int deckId) {
     //   User u =userService.findByUsername(userDetails.getUsername());
    	try {
            String valid = deckService.validateDeck(deckId); // Validates the specified deck
            return new ResponseEntity<>(valid, HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); 
        }
       
    }
     // HTTP POST method to add a card to a specific deck
    @PostMapping("{deckId}/add")
    public ResponseEntity<String> addCardToDeck(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int deckId, @RequestParam String cardId) {
        User u =userService.findByUsername(userDetails.getUsername());
        try {
            deckService.addCardToDeck(u.getId(), deckId, cardId); // Calls service to add the specified card to the deck
            return new ResponseEntity<>("Card added to the deck", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);// Returns error message if an exception occurs
        }
    }
    // HTTP POST method to add a card to a specific deck
    @PostMapping("{deckId}/remove")
    public ResponseEntity<String> removeCardFromDeck(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int deckId, @RequestParam String cardId) {
        User u =userService.findByUsername(userDetails.getUsername());
        try {
            deckService.removeCardFromDeck(u.getId(), deckId, cardId); // Calls service to remove the specified card from the deck
            return new ResponseEntity<>("Card removed from the deck", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Returns error message if an exception occurs
        }
    }
    // HTTP GET method to delete a specific deck
    @GetMapping("deletedeck")
    public ResponseEntity<String> deleteDeck(@AuthenticationPrincipal UserDetails userDetails, @RequestParam int deckId) {
        User user =userService.findByUsername(userDetails.getUsername());
        try {
            deckService.deleteDeck(deckId); // Calls service to delete the specified deck
            return new ResponseEntity<>("Deck deleted.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
