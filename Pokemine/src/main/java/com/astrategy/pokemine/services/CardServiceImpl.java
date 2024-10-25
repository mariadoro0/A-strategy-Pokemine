package com.astrategy.pokemine.services;

import com.astrategy.pokemine.dto.CardDTO;
import com.astrategy.pokemine.entities.Card;
import com.astrategy.pokemine.repos.CardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {
	
	// Constant for pagination size.
    private static final int page_size = 100;
    
    // Automatically injects the CardDAO dependency.
    @Autowired
    private CardDAO dao;
    
    // Method to retrieve a list of cards based on various filters.
    public CardDTO getByFilters(String Id, String name, String series,String artist, String type, String setName, String generation, String rarity, String supertype, int page) {
        Specification<Card> spec = (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
            
         // Adding predicates based on non-null filter values.
            if (Id != null) {
                predicates.add(cb.equal(root.get("id"), Id));
            }

            if (name != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (series != null) {
                predicates.add(cb.like(cb.lower(root.get("series")),"%" + series.toLowerCase() + "%" ));
            }
            if (supertype != null) {
                predicates.add(cb.like(cb.lower(root.get("supertype")),"%" + supertype.toLowerCase() + "%" ));
            }

            if (type != null) {
                predicates.add(cb.equal(cb.lower(root.join("types").get("name")), type.toLowerCase()));
            }

            if (artist != null) {
                predicates.add(cb.like(cb.lower(root.get("artist")), "%" + artist.toLowerCase() + "%"));
            }

            if (setName != null) {
                predicates.add(cb.equal(cb.lower(root.get("setName")), setName.toLowerCase()));
            }

            if (generation != null) {
                predicates.add(cb.equal(cb.lower(root.get("generation")), generation.toLowerCase()));
            }

            if (rarity != null) {
                predicates.add(cb.equal(cb.lower(root.get("rarity")), rarity.toLowerCase()));
            }
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };

     // Creating a pageable object for pagination.
        Pageable pageable = PageRequest.of(page - 1, page_size);
        Page<Card> cardPage = dao.findAll(spec, pageable);
        if(cardPage.isEmpty()){
            throw new IllegalArgumentException("Invalid filter parameter: ");
        }else{
            // returns a DTO in order to store the page information.
            return new CardDTO(
                    cardPage.getContent(),
                    page,
                    cardPage.getTotalPages(),
                    (int) cardPage.getTotalElements()
            );
        }

    }

}
