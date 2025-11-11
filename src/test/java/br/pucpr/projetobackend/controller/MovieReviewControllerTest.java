package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.MovieReviewDTO;
import br.pucpr.projetobackend.model.MovieReview;
import br.pucpr.projetobackend.service.AuthorService;
import br.pucpr.projetobackend.service.MovieReviewService;
import br.pucpr.projetobackend.service.MovieService;
import br.pucpr.projetobackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
public class MovieReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MovieReviewService movieReviewService;

    @MockitoBean
    private AuthorService authorService;

    @MockitoBean
    private UserService userService;

    private MovieReview movieReview;
    private MovieReviewDTO movieReviewDTO;

    @BeforeEach
    void setUp(){
        movieReview = new MovieReview();
        movieReview.setId(1);
        movieReview.setComment("Top demais");

        movieReviewDTO = new MovieReviewDTO();
        movieReviewDTO.setId(1);
        movieReviewDTO.setComment("Top demais");
    }

    @Test
    void testCreateMovieReview_Success() throws Exception{
        doNothing().when(movieReviewService).save(any(MovieReview.class));

        mockMvc.perform(post("/api/v1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieReviewDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comment").value("Top demais"));
                verify(movieReviewService, times(1)).getAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void testDeleteMovieReview_Success() throws Exception {
        doNothing().when(movieReviewService).delete(1);

        mockMvc.perform(delete("/api/v1/reviews"))
                .andExpect(status().isOk());
        verify(movieReviewService, times(1)).delete(1);
    }
}
