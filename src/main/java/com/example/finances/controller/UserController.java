package com.example.finances.controller;

// Base Dependencies 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


// Spring Dependencies
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Local Dependencies
import com.example.finances.model.User;
import com.example.finances.service.UserService;


/**
 * This class is a controller that handles HTTP requests for the User model.
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


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
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        try {

            List<User> users = new ArrayList<User>();
            userService.getAllUsers().forEach(users::add);

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    
    /**
     * This method retrieves a user from the database by its ID.
     * 
     * @param id The ID of the user to retrieve.
     * @return A ResponseEntity containing the retrieved user object and a HTTP
     *         status code of 200 (OK) if the user was successfully retrieved, or a
     *         ResponseEntity containing null and a HTTP status code of 404
     *         (NOT_FOUND) if the user was not found.
     */
    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getuserById(@PathVariable("id") long id) {
        Optional<User> userData = userService.getUserById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method creates a new user in the database.
     * 
     * @param user The user object to be created.
     * @return A ResponseEntity containing the created user object and a HTTP status
     *         code of 201 (CREATED) if the user was successfully created, or a
     *         ResponseEntity containing null and a HTTP status code of 500
     *         (INTERNAL_SERVER_ERROR) if an error occurred while creating the user.
     */
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = userService
                    .saveUser(new User(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method deletes a user from the database by its ID.
     * 
     * @param id The ID of the user to delete.
     * @return A ResponseEntity containing a HTTP status code of 204 (NO_CONTENT) if
     *         the user was successfully deleted, or a ResponseEntity containing a
     *         HTTP status code of 500 (INTERNAL_SERVER_ERROR) if an error occurred
     *         while deleting the user.
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/users/id")
    public ResponseEntity<User> modifyUser(@PathVariable Long id, @RequestBody User user) {
        try {
            Optional<User> op_user = userService.getUserById(id);

            if (op_user.isPresent()) {
                User _user = op_user.get();
                _user.setUsername(user.getUsername());
                _user.setFirstName(user.getFirstName());
                _user.setLastName(user.getLastName());
                _user.setEmail(user.getEmail());
                return new ResponseEntity<>(userService.saveUser(_user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
