package com.astrategy.pokemine.entities;




import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card {
	
	@Id
	private String id;

	@Column(name="set_name")
	private String setName;
	
	private String series;
	
	private String publisher;
	
	private String generation;
	
	@Column(name="release_date")
	private String releasedate;
	
	private String artist ;
	
	private String name;

	/*set_num is set as a string because it sometimes contains chars*/
	@Column(name="set_num")
	private String setNum;
	
	private String supertype;

    /*level is set as a String because few cards have level 'X'*/
	@Column(name="card_level")
	private String cardLevel;

    /*used Integer instead of int so it accepts NULL values*/
	private Integer hp;
	
	private String evolvesFrom;
	
	private String evolvesTo;
	
	private String retreatCost;
	
	private String convertedRetreatCost;
	
	private String rarity;
	
	private String flavorText;
	
	private String nationalPokedexNumbers;
	
	private String legalities;
	
	private String rules;
	
	private String regulationMark;
	
	private String ancientTrait;
	
	private String img;

	@OneToMany(mappedBy = "card")
	@JsonIgnore
	private Set<UserCollection> userCollection ;

	/*relationship between a card and its abilities by relational table*/
	@ManyToMany
	@JoinTable(
			name = "card_abilities",
			joinColumns = @JoinColumn(name = "card_id"),
			inverseJoinColumns = @JoinColumn(name = "ability_id")
	)
	@JsonManagedReference
	private Set<Abilities> abilities;

	/*relationship between a card and its attacks by relational table*/
	@ManyToMany
	@JoinTable(
			name = "card_attacks",
			joinColumns = @JoinColumn(name = "card_id"),
			inverseJoinColumns = @JoinColumn(name = "attack_id")
	)
	@JsonManagedReference
	private Set<Attacks> attacks;

	/*relationship between a card and its weaknesses by relational table*/
	@ManyToMany
	@JoinTable(
			name = "card_weaknesses",
			joinColumns = @JoinColumn(name = "card_id"),
			inverseJoinColumns = @JoinColumn(name = "weakness_id")
	)
	@JsonManagedReference
	private Set<Weaknesses> weaknesses;

	/*relationship between a card and its abilities by relational table*/
	@ManyToMany
	@JoinTable(
			name = "card_resistances",
			joinColumns = @JoinColumn(name = "card_id"),
			inverseJoinColumns = @JoinColumn(name = "resistance_id")
	)
	@JsonManagedReference
	private Set<Resistances> resistances;

	/*relationship between a card and its types by relational table*/
	@ManyToMany
	@JoinTable(
			name = "card_types",
			joinColumns = @JoinColumn(name = "card_id"),
			inverseJoinColumns = @JoinColumn(name = "type_id")
	)
	@JsonManagedReference
	private Set<Types> types;

	/*relationship between a card and its subtypes by relational table*/
	@ManyToMany
	@JoinTable(
			name = "card_subtypes",
			joinColumns = @JoinColumn(name = "card_id"),
			inverseJoinColumns = @JoinColumn(name = "subtype_id")
	)
	@JsonManagedReference
	private Set<Subtypes> subtypes;

}
