package com.astrategy.pokemine.controllers;


import com.astrategy.pokemine.entities.User;
import com.astrategy.pokemine.services.JwtUtil;
import com.astrategy.pokemine.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AuthController {


    @Autowired
    private UsersServiceImpl userimp;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
        //	return new ResponseEntity<>(user.getPassword(),HttpStatus.OK);
        try {
            //throw new Exception(user.getPassword());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userimp.loadUserByUsername(user.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        System.out.println("ID passed to the query: " + user.getId());
        System.out.println("ID passed to the query: " + user.getEmail());
        System.out.println("ID passed to the query: " + user.getPassword());

        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
}
