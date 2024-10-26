package com.astrategy.pokemine.services;

import java.util.List;
import java.util.Optional;

import com.astrategy.pokemine.entities.*;
import com.astrategy.pokemine.repos.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserCollectionServiceImp implements UserCollectionService {

	@Autowired
	private UserCollectionDAO dao;
	@Autowired 
	private UserDAO userDAO;
	@Autowired 
	private CardDAO carddao;
	@Autowired
	private DeckDAO deckDAO;
	@Autowired
	private DeckCardDAO deckCardDAO;

	// Method to add a card to the user's collection
	@Override
	public void addCardToCollection(int userId, String cardId) {
		// Fetch the user by userId or throw an exception if not found
	    User user = userDAO.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User with id: " + userId+" not found."));
	 // Fetch the card by cardId or throw an exception if not found
	    Card card = carddao.findById(cardId)
				.orElseThrow(() -> new IllegalArgumentException("Card with id: " + cardId+" not found."));
	 // Create a composite key (UserCollectionId) for the user and card
		UserCollectionId uid = new UserCollectionId(userId, cardId);

		// Try to find the existing entry in the user's collection
	    Optional<UserCollection> optionalUserCollection = dao.findById(uid);
	    
	    // If the card is already in the user's collection, increase the quantity
	    if (optionalUserCollection.isPresent()) {
	        UserCollection userCollection = optionalUserCollection.get();
	        userCollection.setQuantity(userCollection.getQuantity() + 1);
	        dao.save(userCollection);
	    } else {
	    	// If the card is not in the collection, create a new entry
	        UserCollection userCollection = new UserCollection();
	        userCollection.setId(uid);
	        userCollection.setUser(user);
	        userCollection.setCard(card);
	        userCollection.setQuantity(1);
	        dao.save(userCollection);
	    }
	}

	@Override
	public void removeCardToCollection(int userId, String cardId) {
		// Fetch the user by userId or throw an exception if not found
		User user = userDAO.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User with id: " + userId+" not found."));
		// Fetch the card by cardId or throw an exception if not found
		Card card = carddao.findById(cardId)
				.orElseThrow(() -> new IllegalArgumentException("Card with id: " + cardId+" not found."));
		// Create a composite key (UserCollectionId) for the user and card
		UserCollectionId cid = new UserCollectionId(userId, cardId);
		
		// Try to find the existing entry in the user's collection
		Optional<UserCollection> optionalUserCollection = dao.findById(cid);
		
		// If the card exists in the collection
		if (optionalUserCollection.isPresent()) {
			UserCollection userCollection = optionalUserCollection.get();
			// If the user has more than one of the card, reduce the quantity by 1
			if (userCollection.getQuantity() > 1) {
				userCollection.setQuantity(userCollection.getQuantity() - 1);
				dao.save(userCollection);
				// If the card is present in any deck, its quantity can't be bigger
				// than the quantity in the collection
				List<Deck> userDecks = deckDAO.findByUserId(userId);
				for (Deck deck : userDecks) {
					List<DeckCard> cardsInDeck = deckCardDAO.findByDeck(deck);
					for (DeckCard deckCard : cardsInDeck) {
						if (deckCard.getCard().equals(card)) {
							// if the quantity in the deck is greater than the collection
							// set the deck quantity to the collection quantity
							// so the user still has the maximum amount available
							if(deckCard.getQuantity()>userCollection.getQuantity()){
								deckCard.setQuantity(userCollection.getQuantity());
								deckCardDAO.save(deckCard);
							}
						}
					}
				}
			} else {
				// If the quantity is 1 or less, remove the card from the collection
				dao.delete(userCollection);
				// if the card gets removed from the collection, it must be also removed
				// from the decks
				List<Deck> userDecks = deckDAO.findByUserId(userId);
				for (Deck deck : userDecks) {
					List<DeckCard> cardsInDeck = deckCardDAO.findByDeck(deck);
					for (DeckCard deckCard : cardsInDeck) {
						if (deckCard.getCard().equals(card)) {
							deckCardDAO.delete(deckCard);
						}
					}
				}
			}


		} else {
			throw new RuntimeException("The card does not exist in the collection.");
		}
	}

	@Override
	public List<UserCollection> getUserCollection(int userId) {
		// Fetch the user by userId or throw an exception if not found
		User user = userDAO.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User with id: " + userId+" not found."));
		// Retrieve the user's collection from the database
			return dao.findByUser(user);
	}
}

