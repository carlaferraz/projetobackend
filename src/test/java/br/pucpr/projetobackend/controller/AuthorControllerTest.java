package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.AuthorDTO;
import br.pucpr.projetobackend.model.Author;
import br.pucpr.projetobackend.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthorService authorService;

    private Author author;
    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1);
        author.setName("Steven Spielberg");
        author.setBio("Legendary filmmaker");

        authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setName("Steven Spielberg");
        authorDTO.setBio("Legendary filmmaker");
    }

    @Test
    void testCreateAuthor_Success() throws Exception {
        when(authorService.save(any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Steven Spielberg"))
                .andExpect(jsonPath("$.bio").value("Legendary filmmaker"));

        verify(authorService, times(1)).save(any(Author.class));
    }

    @Test
    void testGetAllAuthors_Success() throws Exception {
        List<Author> authors = Arrays.asList(author);
        when(authorService.getAll()).thenReturn(authors);

        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Steven Spielberg"))
                .andExpect(jsonPath("$[0].bio").value("Legendary filmmaker"));

        verify(authorService, times(1)).getAll();
    }

    @Test
    void testGetAuthorById_Success() throws Exception {
        when(authorService.getId(1)).thenReturn(author);

        mockMvc.perform(get("/api/v1/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Steven Spielberg"));

        verify(authorService, times(1)).getId(1);
    }

    @Test
    void testUpdateAuthor_Success() throws Exception {
        when(authorService.update(any(Author.class))).thenReturn(author);

        mockMvc.perform(put("/api/v1/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isOk());

        verify(authorService, times(1)).update(any(Author.class));
    }

    @Test
    void testDeleteAuthor_Success() throws Exception {
        doNothing().when(authorService).delete(1);

        mockMvc.perform(delete("/api/v1/authors/1"))
                .andExpect(status().isNoContent());

        verify(authorService, times(1)).delete(1);
    }
}

