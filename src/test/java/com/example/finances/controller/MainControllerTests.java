package com.example.finances.controller;



// Spring Dependencies
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// 3rd-Party Dependencies
import org.junit.jupiter.api.Test;


@WebMvcTest(MainController.class)
public class MainControllerTests {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testCheck() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/check"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}