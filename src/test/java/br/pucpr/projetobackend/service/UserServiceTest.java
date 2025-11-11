package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.dto.RegisterDTO;
import br.pucpr.projetobackend.exception.DuplicateResourceException;
import br.pucpr.projetobackend.model.User;
import br.pucpr.projetobackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private RegisterDTO registerDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setNome("Juca");
        user.setEmail("juquinha@example.com");
        user.setSenha("$2a$10$encodedPassword");

        registerDTO = new RegisterDTO();
        registerDTO.setNome("Juca");
        registerDTO.setEmail("juquinha@example.com");
        registerDTO.setSenha("senha123");
        registerDTO.setConfirmarSenha("senha123");
    }

    @Test
    void testRegisterUser_Success() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.register(registerDTO);

        assertNotNull(result);
        assertEquals("Juca", result.getNome());
        verify(passwordEncoder, times(1)).encode("senha123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_PasswordMismatch() {
        registerDTO.setConfirmarSenha("senhaDiferente");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.register(registerDTO)
        );

        assertTrue(exception.getMessage().contains("senhas não coincidem"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterUser_DuplicateEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> userService.register(registerDTO)
        );

        assertTrue(exception.getMessage().contains("Email já cadastrado"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testSaveUser_Success() {
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.save(user);

        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAllUsers_Success() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Juca", result.get(0).getNome());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.getId(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Juca", result.getNome());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateUser_Success() {
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setNome("Juca Updated");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.update(updatedUser);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser_Success() {
        doNothing().when(userRepository).deleteById(1);

        userService.delete(1);

        verify(userRepository, times(1)).deleteById(1);
    }
}

