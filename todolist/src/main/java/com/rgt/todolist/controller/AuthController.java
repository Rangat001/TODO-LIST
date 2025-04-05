package com.rgt.todolist.controller;

import com.rgt.todolist.dto.AuthRequest;
import com.rgt.todolist.dto.AuthResponse;
import com.rgt.todolist.model.User;
import com.rgt.todolist.service.UserDetailsServiceImpl;
import com.rgt.todolist.service.UserService;
import com.rgt.todolist.utils.JWTutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/public")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTutil jwTutil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @GetMapping("/login")
    public String login() {
        return "login";  // This will look for login.html in src/main/resources/templates/
    }

//    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> checkAuth(@RequestBody AuthRequest authRequest){
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
//
//            User user = userService.findByEmail(authRequest.getEmail());
//
//            // Generate JWT token
//            String token = jwTutil.generateToken(authRequest.getEmail());
//
//            // Create response
//            AuthResponse response = new AuthResponse(
//                    token,
//                    user.getEmail(),
//                    user.getFullName(),
//                    "Login successful"
//            );
//
//            return ResponseEntity.ok(response);
//
//
//        }catch (AuthenticationException e){
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body(new AuthResponse(
//                            null,
//                            null,
//                            null,
//                            "Invalid email or password"
//                    ));
//        }
//    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkAuth(@RequestBody AuthRequest authRequest) {
        try {
            // Create authentication token
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );

            // Get user details after successful authentication
            User user = userService.findByEmail(authRequest.getEmail());

            // Generate JWT token
            String token = jwTutil.generateToken(authRequest.getEmail());

            // Create success response
//            AuthResponse response = new AuthResponse(
//                    token
//            );

            return new ResponseEntity<>(token,HttpStatus.OK);

        }
          catch (Exception e) {
            // General error response
            return  new ResponseEntity<>("Incorrect Credential",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";  // This will look for signup.html in src/main/resources/templates/
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user){
        try {
            userService.createUser(user);
            return "redirect:/login?signup=success";
        } catch (Exception e) {
            return "redirect:/signup?error";
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // Remove "Bearer " prefix if present
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            String username = jwTutil.extractUserEmail(token);
            User user = userService.findByEmail(username);

            return ResponseEntity.ok(new AuthResponse(
                    token,
                    user.getEmail(),
                    user.getFullName(),
                    "Token is valid"
            ));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(
                            null,
                            null,
                            null,
                            "Invalid token"
                    ));
        }
    }
    @GetMapping("/index")
    public String index() {
        return "index";  // This will look for index.html in templates folder
    }

}
