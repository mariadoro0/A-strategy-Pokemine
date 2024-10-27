package com.astrategy.pokemine.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
@Getter
@Setter
@Entity
@Table(name ="user_collection")
public class UserCollection {

	@EmbeddedId
	private UserCollectionId id;
	
	 // Many-to-One relationship to the 'Card' entity, representing the card in the collection.
	@ManyToOne
	@MapsId("cardId")
	@JoinColumn(name="card_id") // Foreign key reference to the 'Card' entity.
	@JsonManagedReference  // Prevents the card reference from being serialized to JSON.
	private Card card;
	
	// Many-to-One relationship to the 'User' entity, representing the owner of the card collection.
	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name="user_id") // Foreign key reference to the 'User' entity.
	@JsonIgnore  // Prevents the user reference from being serialized to JSON.
	private User user;
	
	// The number of this specific card the user has in their collection.
	@Column(name ="quantity")
	private int quantity ;



}
