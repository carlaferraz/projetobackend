package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.RegisterDTO;
import br.pucpr.projetobackend.dto.UserDTO;
import br.pucpr.projetobackend.model.User;
import br.pucpr.projetobackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private User user;
    private RegisterDTO registerDTO;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setNome("Jane Doe");
        user.setEmail("jane@example.com");
        user.setSenha("$2a$10$encodedPassword");

        registerDTO = new RegisterDTO();
        registerDTO.setNome("Jane Doe");
        registerDTO.setEmail("jane@example.com");
        registerDTO.setSenha("senha123");
        registerDTO.setConfirmarSenha("senha123");

        userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setNome("Jane Doe");
        userDTO.setEmail("jane@example.com");
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        when(userService.register(any(RegisterDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Usuário cadastrado com sucesso!"));

        verify(userService, times(1)).register(any(RegisterDTO.class));
    }

    @Test
    void testRegisterUser_ValidationError() throws Exception {
        RegisterDTO invalidRegister = new RegisterDTO();
        invalidRegister.setNome("");
        invalidRegister.setEmail("invalid-email");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRegister)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).register(any(RegisterDTO.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetAllUsers_Success() throws Exception {
        List<User> users = Arrays.asList(user);
        when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Jane Doe"))
                .andExpect(jsonPath("$[0].email").value("jane@example.com"));

        verify(userService, times(1)).getAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetUserById_Success() throws Exception {
        when(userService.getId(1)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Jane Doe"));

        verify(userService, times(1)).getId(1);
    }

    @Test
    @WithMockUser(roles = "USER")
    void testUpdateUser_Success() throws Exception {
        when(userService.update(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).update(any(User.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testDeleteUser_Success() throws Exception {
        doNothing().when(userService).delete(1);

        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).delete(1);
    }
}
