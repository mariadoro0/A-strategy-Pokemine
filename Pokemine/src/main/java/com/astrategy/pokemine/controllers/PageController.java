package com.astrategy.pokemine.controllers;

import ch.qos.logback.core.model.Model;
import com.astrategy.pokemine.services.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/test")
public class PageController {

    @Autowired
    private JwtUtil util;
    // Rotta per la Home Page
    @GetMapping("home")
    public String home() {
        return "home"; // Nome della vista per la home page
    }

    // Rotta per la pagina di contatto
    @GetMapping("login")
    public String contact() {
        return "login"; // Nome della vista per la pagina di contatto
    }
    @GetMapping("collection")
    public String collection() {
        return "collection"; // Nome della vista per la pagina di contatto
    }

    // Rotta per la pagina di informazioni
    @GetMapping("decks")
    public String about() {
        return "deckscreen"; // Nome della vista per la pagina di informazioni
    }
}