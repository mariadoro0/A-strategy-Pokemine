package com.astrategy.pokemine.repos;

import com.astrategy.pokemine.entities.Deck;
import com.astrategy.pokemine.entities.DeckCard;
import com.astrategy.pokemine.entities.DeckCardId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeckCardDAO extends JpaRepository<DeckCard, DeckCardId> {
    List<DeckCard> findByDeck(Deck deck);
}