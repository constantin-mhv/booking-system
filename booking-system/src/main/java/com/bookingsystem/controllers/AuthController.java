package com.bookingsystem.controllers;

import com.bookingsystem.models.user.ERole;
import com.bookingsystem.models.user.Role;
import com.bookingsystem.models.user.User;
import com.bookingsystem.security.request.LoginRequest;
import com.bookingsystem.security.request.SignupRequest;
import com.bookingsystem.security.response.JwtResponse;
import com.bookingsystem.security.response.MessageResponse;
import com.bookingsystem.repository.RoleRepository;
import com.bookingsystem.repository.UserRepository;
import com.bookingsystem.security.jwt.JwtUtils;
import com.bookingsystem.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

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
                userDetails.getEmail(),
                roles,
                userDetails.getDisplayName()
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getEmail(),
//							 signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getDisplayName());

        Set<Role> roles = new HashSet<>();
        Role role;
        switch (signUpRequest.getUserRole()) {
            case "ROLE_CLIENT":
                role = roleRepository.findByName(ERole.ROLE_CLIENT)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(role);
                break;
            case "ROLE_OWNER":
                role = roleRepository.findByName(ERole.ROLE_OWNER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(role);
                break;
            case "ROLE_ADMIN":
                role = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(role);
                break;

        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
