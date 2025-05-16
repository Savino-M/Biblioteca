package it.savinomarzano.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    private void setup() {

        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

    }

    @Test
    public void securityTest() throws Exception {

        String username = "";
        String password = "";

        mvc.perform(get("/user/login").param("email", username).param("password",
                password)).andExpect(status().isInternalServerError());

        username = "saviomarzano@gmail.com";
        password = "1234";

        mvc.perform(get("/user/login").param("email", username).param("password",
                password)).andExpect(status().isOk());

    }

}