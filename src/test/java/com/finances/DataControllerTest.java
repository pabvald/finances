package com.finances;


/* Base Dependencies */
import java.util.Locale;

/* Spring Dependencies */
import org.springframework.boot.test.context.SpringBootTest;

/* 3rd-Party Dependencies */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class DataControllerTest {

    private DataController dataController;

    @BeforeEach
    public void setUp() {
        dataController = new DataController();
    }

    @Test
    public void testHealthCheck() {
        String result = dataController.healthCheck();
        assertEquals("HEALTH CHECK OK!", result);
    }

    @Test
    public void testVersion() {
        String result = dataController.version();
        assertEquals("The actual version is 1.0.0", result);
    }

    @Test
    public void testGetRandomNations() throws Exception {
        JsonNode result = dataController.getRandomNations();
        assertNotNull(result);
        assertEquals(10, result.size());
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < result.size(); i++) {
            JsonNode nation = result.get(i);
            assertNotNull(nation.get("nationality"));
            assertNotNull(nation.get("capitalCity"));
            assertNotNull(nation.get("flag"));
            assertNotNull(nation.get("language"));
        }
    }

    @Test
    public void testGetRandomCurrencies() throws Exception {
        JsonNode result = dataController.getRandomCurrencies();
        assertNotNull(result);
        assertEquals(20, result.size());
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < result.size(); i++) {
            JsonNode currency = result.get(i);
            assertNotNull(currency.get("name"));
            assertNotNull(currency.get("code"));
        }
    }

}