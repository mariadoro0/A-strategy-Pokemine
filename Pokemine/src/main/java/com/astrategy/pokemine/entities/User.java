package com.astrategy.pokemine.entities;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users")
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id ;	
	private String username;
	private String email;
	private String password;
	@Column(columnDefinition = "BIT(1)")
	private boolean isActive = true;
	
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.decks = new LinkedHashSet<>();
		this.userCollection = new LinkedHashSet<>();
	}

	// One-to-Many relationship with the Deck entity.
    // The decks field represents the user's decks, and the relationship is managed from the Deck entity (using mappedBy = "user").
    // @JsonIgnore: Prevents this field from being included in JSON serialization, avoiding recursive references during serialization.
	@OneToMany(mappedBy = "user",cascade=CascadeType.ALL,orphanRemoval = true)
    private Set<UserCollection> userCollection;

	
	
	// One-to-Many relationship with the Deck entity.
    // The decks field represents the user's decks, and the relationship is managed from the Deck entity (using mappedBy = "user").
    // @JsonIgnore: Prevents this field from being included in JSON serialization, avoiding recursive references during serialization.
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Deck> decks = new LinkedHashSet<>();// A collection of Deck objects representing the user's decks.
}
