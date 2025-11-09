package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.model.Movie;
import br.pucpr.projetobackend.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1);
        movie.setTitle("Inception");
    }

    @Test
    void testSaveMovie_Success() {
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        movieService.save(movie);

        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void testGetAllMovies_Success() {
        List<Movie> movies = Arrays.asList(movie);
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void testGetMovieById_Success() {
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));

        Movie result = movieService.getId(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Inception", result.getTitle());
        verify(movieRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateMovie_Success() {
        Movie updatedMovie = new Movie();
        updatedMovie.setId(1);
        updatedMovie.setTitle("Inception Updated");

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie result = movieService.update(updatedMovie);

        assertNotNull(result);
        verify(movieRepository, times(1)).findById(1);
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testDeleteMovie_Success() {
        doNothing().when(movieRepository).deleteById(1);

        movieService.delete(1);

        verify(movieRepository, times(1)).deleteById(1);
    }
}

