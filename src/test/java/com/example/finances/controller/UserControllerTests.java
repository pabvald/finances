package com.example.finances.controller;


// Base Dependencies
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

// Spring Dependencies
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// Local Dependencies
import com.example.finances.model.User;
import com.example.finances.service.UserService;

// 3rd-Party Dependencies
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;



@WebMvcTest(UserController.class)
public class UserControllerTests {
    private String prefix = "/api";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    

    /**
     * A helper method to convert a Java object into a JSON string representation.
     * @param obj The Java object to convert.
     * @return A JSON string representation of the Java object.
     */
    public static String asJsonString(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetAllUsers() throws Exception {

        // placeholder users
        List<User> users = new ArrayList<User>();
        users.add(new User(1L, "john22",  "John",  "Doe",  "jod@gmail.com"));
        users.add(new User(2L, "jane33",  "Jane",  "Doe",  "jad@gmail.com"));
        users.add(new User(3L, "jim44",  "Jim",  "Doe",  "jim@gmail.com"));
        users.add(new User(4L, "jill55",  "Jill",  "Doe",  "jilldoe@hotmail.com"));

        // mock UserService.getAllUsers() method
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        
        // test GET request to /api/users
        mockMvc.perform(get(prefix + "/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(4)));
    
    }


    @Test
    public void testGetUserById() throws Exception {
        // placeholder user
        User user = new User(1L, "john22",  "John",  "Doe",  "jjf@gmail.com");
        
        // mock UserService.getUserById() method
        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        
        // test GET request to /api/users/1
        mockMvc.perform(get(prefix + "/users/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.firstName").value("John"))
            .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testCreateUser() throws Exception {
        // placeholder user
        Long id = 1L;
        User userWoId = new User("john22",  "John",  "Doe",  "jojo@protonmail.com");
        User user = new User(id, "john22",  "John",  "Doe",  "jojo@protonmail.com");


        // mock UserService.saveUser() method
        Mockito.when(userService.saveUser(Mockito.any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
            User u = invocation.getArgument(0);
            u.setId(id);
            return u;
        });

        // test POST request to /api/users
        mockMvc.perform(post(prefix + "/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(userWoId)))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().json(asJsonString(user)));

    }

    @Test
    public void testDeleteUser() throws Exception {
    
        // mock UserService.deleteUser() method
        Mockito.doNothing().when(userService).deleteUser(Mockito.any(Long.class));

        // test DELETE request to /api/users/1
        mockMvc.perform(delete(prefix + "/users/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    
    }

}