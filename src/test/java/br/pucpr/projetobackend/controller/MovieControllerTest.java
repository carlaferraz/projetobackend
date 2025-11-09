package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.MovieDTO;
import br.pucpr.projetobackend.model.Movie;
import br.pucpr.projetobackend.service.AuthorService;
import br.pucpr.projetobackend.service.MovieService;
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
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MovieService movieService;

    @MockitoBean
    private AuthorService authorService;

    private Movie movie;
    private MovieDTO movieDTO;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1);
        movie.setTitle("The Matrix");

        movieDTO = new MovieDTO();
        movieDTO.setId(1);
        movieDTO.setTitle("The Matrix");
    }

    @Test
    void testCreateMovie_Success() throws Exception {
        doNothing().when(movieService).save(any(Movie.class));

        mockMvc.perform(post("/api/v1/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("The Matrix"));

        verify(movieService, times(1)).save(any(Movie.class));
    }

    @Test
    void testGetAllMovies_Success() throws Exception {
        List<Movie> movies = Arrays.asList(movie);
        when(movieService.getAll()).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("The Matrix"));

        verify(movieService, times(1)).getAll();
    }

    @Test
    void testGetMovieById_Success() throws Exception {
        when(movieService.getId(1)).thenReturn(movie);

        mockMvc.perform(get("/api/v1/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("The Matrix"));

        verify(movieService, times(1)).getId(1);
    }

    @Test
    @WithMockUser(roles = "USER")
    void testUpdateMovie_Success() throws Exception {
        when(movieService.update(any(Movie.class))).thenReturn(movie);

        mockMvc.perform(put("/api/v1/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDTO)))
                .andExpect(status().isOk());

        verify(movieService, times(1)).update(any(Movie.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testDeleteMovie_Success() throws Exception {
        doNothing().when(movieService).delete(1);

        mockMvc.perform(delete("/api/v1/movies/1"))
                .andExpect(status().isOk());

        verify(movieService, times(1)).delete(1);
    }
}

