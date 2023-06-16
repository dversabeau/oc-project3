package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.DTO.UserDTO;
import com.openclassrooms.chatop.service.JwtUserDetailsService;
import com.openclassrooms.chatop.utils.JWTUtils;
import com.openclassrooms.chatop.utils.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;



    public JwtAuthenticationController(JwtUserDetailsService jwtUserDetailsService, JWTUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/api/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDTO authentication) throws Exception {

        authenticate(authentication.getName(), authentication.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authentication.getName());

        final String token = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/api/auth/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(jwtUserDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
