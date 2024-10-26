package com.astrategy.pokemine.services;

import com.astrategy.pokemine.entities.*;
import com.astrategy.pokemine.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeckServiceImpl implements DeckService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private DeckDAO deckDAO;
    @Autowired
    private DeckCardDAO deckCardDAO;
    @Autowired
    private CardDAO cardDAO;
    @Autowired
    private UserCollectionDAO userCollectionDAO;

    @Override
    public void createDeck(int userId, String deckName, String deckDescription) {
        // searching for the user with the specified id
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        // instantiate new deck
        Deck deck = new Deck(user, deckName, deckDescription);
        //saving deck
        deckDAO.save(deck);
    }

    @Override
    public void addCardToDeck(int userId, int deckId, String cardId) {
    	// Validate user, card, and deck existence
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId+" not found."));
        Card card = cardDAO.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card with id: " + cardId+" not found."));
        Deck deck = deckDAO.findById(deckId)
                .orElseThrow(() -> new IllegalArgumentException("Deck with id: " + deckId+" not found."));
        
        // Ensure the deck belongs to the user
        if(!user.equals(deck.getUser())){
            throw new IllegalArgumentException("The deck does not belong to the selected user");
        }
        
        // Verify the user owns the card in their collection
        UserCollection userCollection = userCollectionDAO.findById(new UserCollectionId(userId,cardId))
                .orElseThrow(() -> new IllegalArgumentException("The card is not present in the user's collection."));
        int owned = userCollection.getQuantity();

        // Check if the card is a Basic Energy card
        boolean isBasicEnergy = card.getSupertype().equals("Energy") && card.getSubtypes().stream()
                .anyMatch(subtype -> subtype.getName().equals("Basic"));
        
        // Handle adding the card to the deck (updating quantity if it already exists)
        DeckCardId id = new DeckCardId(deck.getId(), card.getId());
        Optional<DeckCard> record = deckCardDAO.findById(id);
        DeckCard cardToAdd;

        if (record.isPresent()) {
            cardToAdd = record.get();

           // Limit to 4 copies for non-Basic Energy cards
            if (!isBasicEnergy && cardToAdd.getQuantity() >= 4) {
                throw new IllegalArgumentException("You cannot add more than 4 copies of the same non-Basic Energy card.");
            }
            
            // Check that the user owns enough copies of the card
            if(cardToAdd.getQuantity()+1>owned){
                throw new IllegalArgumentException("You do not own enough copies of this card in your collection.");

            }
            
            // Increment the card quantity in the deck
            cardToAdd.setQuantity(cardToAdd.getQuantity() + 1);
        } else {
        	// Add the card to the deck with quantity 1 if it doesn't exist yet
            cardToAdd = new DeckCard();
            cardToAdd.setCard(card);
            cardToAdd.setDeck(deck);
            cardToAdd.setId(id);
            cardToAdd.setQuantity(1);
        }
        
        // Save the updated or new DeckCard entity
        deckCardDAO.save(cardToAdd);

    }

    @Override
    public void removeCardFromDeck(int userId, int deckId, String cardId) {
    	
    	// Validate user, card, and deck existence
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId+" not found."));
        Card card = cardDAO.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card with id: " + cardId+" not found."));
        Deck deck = deckDAO.findById(deckId)
                .orElseThrow(() -> new IllegalArgumentException("Deck with id: " + deckId+" not found."));
     // Ensure the deck belongs to the user
        if(!user.equals(deck.getUser())){
            throw new IllegalArgumentException("This deck does not belong to the selected user.");
        }
        DeckCardId id = new DeckCardId(deckId,cardId);
        Optional<DeckCard> record = deckCardDAO.findById(id);
        DeckCard cardToAdd;
        // If the card quantity is 1, deletes the record
        if (record.isPresent()) {
            cardToAdd = record.get();
            if (cardToAdd.getQuantity() == 1){
                deckCardDAO.delete(cardToAdd);
            }else
            // If the quantity is more than 1, subtracts 1
            {
                cardToAdd.setQuantity(cardToAdd.getQuantity() - 1);
                deckCardDAO.save(cardToAdd);
            }
        } else {
            throw new IllegalArgumentException("The selected card is not present in the deck.");
        }
    }

    @Override
    public List<Deck> getDecksByUser(int userId) {
    	// Fetch all decks associated with the user
        return deckDAO.findByUserId(userId);
    }

    @Override
    public String validateDeck(int deckId) {
        Deck deck = findDeckById(deckId);
        //if there are 60 cards and at least one Basic Pokémon, it is valid
        // Instantiates a list of error strings in order to display the user eventual problems with the deck
        List<String> errors = new ArrayList<>();
        if(deck == null){
            throw new IllegalArgumentException("Deck not found.");
        }
        if (deckCount(deck)<60) {
        	errors.add("You don't have enough cards.");
        }
        if (!checkBaseCard(deck)) {
        	errors.add("Card of type Pokemon Basic not found.");
        }
        if(!errors.isEmpty()){
            throw new IllegalArgumentException(String.join("\n", errors));
        } else {
            return "Deck is valid and ready to play!";
        }

    }
    // this method checks the number of cards in the deck: to be valid it must be 60
    public int deckCount(Deck deck){
        int sum = deck.getDeckCards().stream()
                .mapToInt(DeckCard::getQuantity)
                .sum();
        return sum;
    }

    // this method checks if there is at least one Basic Pokémon in the deck
    public boolean checkBaseCard(Deck deck){
        return deck.getDeckCards().stream()
                .map(DeckCard::getCard)
                .filter(card -> card.getSupertype().equals("Pokémon"))
                .flatMap(card -> card.getSubtypes().stream())
                .anyMatch(subtype -> subtype.getName().equals("Basic"));
    }

    @Override
    public Map<String, Integer> getDeckCardsByDeckId(int userId,int deckId) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " not found."));
        
        Deck deck = findDeckById(deckId);
        if (!user.equals(deck.getUser())) {
            throw new IllegalArgumentException("This deck does not belong to the selected user.");
        }
        
     // Retrieve all DeckCard entities associated with this deck
        List<DeckCard> deckCards = deckCardDAO.findByDeck(deck);

     // Convert DeckCard entities into a map of Card -> Quantity
        return deckCards.stream()
                .collect(Collectors.toMap(deckCard -> deckCard.getCard().getId(), DeckCard::getQuantity));
    }

    @Override
    public Deck findDeckById(int deckId) {
        return deckDAO.findById(deckId)
                .orElseThrow(() -> new IllegalArgumentException("Deck not found with id: " + deckId));
    }

    @Override
    public void deleteDeck(int deckId) {
        Deck deck = deckDAO.findById(deckId).orElseThrow(()->new IllegalArgumentException("Deck not found with id: " + deckId));
        deckDAO.delete(deck);
    }
}
