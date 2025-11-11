package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.security.AuthRequest;
import br.pucpr.projetobackend.security.AuthResponse;
import br.pucpr.projetobackend.security.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    private AuthRequest authRequest;
    private AuthResponse authResponse;

    @BeforeEach
    void setUp() {
        authRequest = new AuthRequest();
        authRequest.setEmail("user@example.com");
        authRequest.setSenha("senha123");

        authResponse = new AuthResponse();
        authResponse.setToken("fake.jwt.token");
        authResponse.setEmail("user@example.com");
        authResponse.setExpires(new Date());
    }

    @Test
    void testLogin_Success() throws Exception {
        when(authService.authenticate(any(AuthRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake.jwt.token"))
                .andExpect(jsonPath("$.email").value("user@example.com"));

        verify(authService, times(1)).authenticate(any(AuthRequest.class));
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        when(authService.authenticate(any(AuthRequest.class)))
                .thenThrow(new RuntimeException("Email ou senha inválidos"));

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isInternalServerError());

        verify(authService, times(1)).authenticate(any(AuthRequest.class));
    }

    @Test
    void testLogin_ValidationError() throws Exception {
        AuthRequest invalidRequest = new AuthRequest();
        invalidRequest.setEmail("invalid-email");
        invalidRequest.setSenha("");

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).authenticate(any(AuthRequest.class));
    }
}

