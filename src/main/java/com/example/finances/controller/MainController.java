package com.example.finances.controller;

// Spring Dependencies
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MainController {

    /**
     * This method retrieves all users from the database.
     * 
     * @return A ResponseEntity containing a list of all user objects and a HTTP
     *         status
     *         code of 200 (OK) if the users were successfully retrieved, or a
     *         ResponseEntity containing null and a HTTP status code of 500
     *         (INTERNAL_SERVER_ERROR) if an error occurred while retrieving the
     *         users.
     */
    @GetMapping("/check")
    public ResponseEntity<String> check() {
        return new ResponseEntity<>("Health check successful!", HttpStatus.OK);
    }
}