package com.astrategy.pokemine.controllers;



import com.astrategy.pokemine.entities.CustomUserDetails;
import com.astrategy.pokemine.entities.Deck;
import com.astrategy.pokemine.entities.User;
import com.astrategy.pokemine.repos.DeckCardDAO;
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
@RequestMapping("deckcard") // Maps HTTP requests to /decks URL path
public class DeckCard {
    @Autowired
    private DeckCardDAO deckService;
    @Autowired
    private UserService userService;

    @Autowired
    private DeckService decks;
    // HTTP GET method to retrieve all decks for a specific user
    @GetMapping("{deckId}")
    public ResponseEntity<?> getUserDecks(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int deckId ) {

        User u =userService.findByUsername(userDetails.getUsername());
        Deck deck = decks.findDeckById(deckId);
        if(u != null) {
            return new ResponseEntity<>(deckService.findByDeck(deck), HttpStatus.OK); // Returns the list of decks for the user
        }else {
            return new ResponseEntity<>("Not Auth found",HttpStatus.BAD_REQUEST);
        }
    }
    // Returns the list of decks for the user

}
