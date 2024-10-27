package com.astrategy.pokemine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "deck_cards")
public class DeckCard {
    @EmbeddedId
    private DeckCardId id; // Composite primary key for the DeckCard entity, consisting of the deckId and cardId. This is defined in a separate class, `DeckCardId`.
    
    // Many-to-One relationship to the Deck entity. This field maps to the "deck_id" column and links a card to a specific deck. @MapsId ties this field to the deckId in the composite key. @JsonIgnore prevents it from being serialized in JSON responses.
    @MapsId("deckId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "deck_id", nullable = false)
    @JsonIgnore
    private Deck deck;
    
    // Many-to-One relationship to the Card entity. This field maps to the "card_id" column and links a deck to a specific card. @MapsId ties this field to the cardId in the composite key. @JsonIgnore prevents it from being serialized in JSON responses.
    @MapsId("cardId")
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    @JsonManagedReference
    private Card card;
    
    // This field stores the number of times a specific card appears in a deck.
    @Column(name = "quantity")
    private Integer quantity;

}