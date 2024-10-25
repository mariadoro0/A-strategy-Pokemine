package com.astrategy.pokemine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "decks")
public class Deck {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer deck_id;
	
	// Many-to-One relationship with the User entity, linking a deck to its owner. Marked with @JsonIgnore to prevent serialization in the JSON response.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	@JsonIgnore
	private User user;
	
	private String deckName;
	
	private String deckDescription;
	
	// One-to-Many relationship with DeckCard entities, representing the cards contained in the deck. @JsonIgnore prevents infinite recursion in JSON responses.
	@OneToMany(mappedBy = "deck", orphanRemoval = true)
	@JsonIgnore
	private Set<DeckCard> deckCards;

	public Deck(User userId, String deckName, String deckDescription) {
		this.user = userId;
		this.deckName = deckName;
		this.deckDescription = deckDescription;
		this.deckCards = new LinkedHashSet<>();
	}


	public Deck() {

	}
}
