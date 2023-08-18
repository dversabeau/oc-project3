package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.ERole;
import com.openclassrooms.chatop.model.ResponseUser;
import com.openclassrooms.chatop.model.Role;
import com.openclassrooms.chatop.model.UserInfo;
import com.openclassrooms.chatop.payload.request.LoginRequest;
import com.openclassrooms.chatop.payload.request.RegisterRequest;
import com.openclassrooms.chatop.payload.response.MessageResponse;
import com.openclassrooms.chatop.repository.RoleRepository;
import com.openclassrooms.chatop.repository.UserInfoRepository;
import com.openclassrooms.chatop.service.JwtUserDetailsService;
import com.openclassrooms.chatop.service.UserDetailsImpl;
import com.openclassrooms.chatop.service.UserService;
import com.openclassrooms.chatop.utils.JWTUtils;
import com.openclassrooms.chatop.payload.response.JwtResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthenticationController {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JWTUtils jwtUtils;
    private final RoleRepository roleRepository;
    private final UserInfoRepository userInfoRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final UserService userService;


    public JwtAuthenticationController(JwtUserDetailsService jwtUserDetailsService, JWTUtils jwtUtils, RoleRepository roleRepository, UserInfoRepository userInfoRepository, AuthenticationManager authenticationManager, PasswordEncoder encoder, UserService userService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
        this.userInfoRepository = userInfoRepository;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (userInfoRepository.existsByName(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userInfoRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserInfo user = new UserInfo(registerRequest.getUsername(),
                registerRequest.getEmail(),
                encoder.encode(registerRequest.getPassword()));

        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userInfoRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseUser> getMe(Authentication authentication) {
        ResponseUser responseUser = userService.getUser(authentication.getName());
        return ResponseEntity.ok(responseUser);
    }
}
