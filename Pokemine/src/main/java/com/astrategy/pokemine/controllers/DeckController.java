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

@RestController
@RequestMapping("decks")
public class DeckController {
    @Autowired
    private DeckService deckService;
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getUserDecks(@AuthenticationPrincipal CustomUserDetails userDetails) {

        User u =userService.findByUsername(userDetails.getUsername());
        if(u != null) {
            return new ResponseEntity<>(deckService.getDecksByUser(u.getId()), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Not Auth found",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("newdeck")
    public ResponseEntity<String> createDeck(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam String name, @RequestParam String description){
        User u =userService.findByUsername(userDetails.getUsername());
        try {
            deckService.createDeck(u.getId(), name, description);
            return new ResponseEntity<>("Nuovo mazzo creato con successo.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @GetMapping("{deckId}")
    public ResponseEntity<?> getDeckCards(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int deckId) {
        User u =userService.findByUsername(userDetails.getUsername());
        try {
            Map<String, Integer> deckCards = deckService.getDeckCardsByDeckId(u.getId(), deckId);
            return new ResponseEntity<>(deckCards, HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{deckId}/validate")
    public ResponseEntity<String> validateDeck(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int deckId) {
     //   User u =userService.findByUsername(userDetails.getUsername());
    	try {
            String valid = deckService.validateDeck(deckId);
            return new ResponseEntity<>(valid, HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
       
    }

    @PostMapping("{deckId}/add")
    public ResponseEntity<String> addCardToDeck(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int deckId, @RequestParam String cardId) {
        User u =userService.findByUsername(userDetails.getUsername());
        try {
            deckService.addCardToDeck(u.getId(), deckId, cardId);
            return new ResponseEntity<>("Carta aggiunta al mazzo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("{deckId}/remove")
    public ResponseEntity<String> removeCardFromDeck(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable int deckId, @RequestParam String cardId) {
        User u =userService.findByUsername(userDetails.getUsername());
        try {
            deckService.removeCardFromDeck(u.getId(), deckId, cardId);
            return new ResponseEntity<>("Carta rimossa dal mazzo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("deletedeck")
    public ResponseEntity<String> deleteDeck(@AuthenticationPrincipal UserDetails userDetails, @RequestParam int deckId) {
        User user =userService.findByUsername(userDetails.getUsername());
        try {
            deckService.deleteDeck(deckId);
            return new ResponseEntity<>("Mazzo eliminato.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}