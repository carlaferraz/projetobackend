package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.model.MovieReview;
import br.pucpr.projetobackend.repository.MovieReviewRepository;
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
public class MovieReviewServiceTest {

    @Mock
    private MovieReviewRepository movieReviewRepository;

    @InjectMocks
    private MovieReviewService movieReviewService;

    private MovieReview movieReview;

    @BeforeEach
    void setUp() {
        movieReview = new MovieReview();
        movieReview.setId(1);
        movieReview.setComment("Top demais");
    }

    @Test
    void testSaveMovieReview_Success() {
        when(movieReviewRepository.save(any(MovieReview.class))).thenReturn(movieReview);

        movieReviewService.save(movieReview);

        verify(movieReviewRepository, times(1)).save(movieReview);
    }

    @Test
    void testGetAllMovieReviews_Success() {
        List<MovieReview> reviews = Arrays.asList(movieReview);
        when(movieReviewRepository.findAll()).thenReturn(reviews);

        List<MovieReview> result = movieReviewService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Top demais", result.get(0).getComment());
        verify(movieReviewRepository, times(1)).findAll();
    }

    @Test
    void testGetMovieReviewById_Success() {
        when(movieReviewRepository.findById(1)).thenReturn(Optional.of(movieReview));

        MovieReview result = movieReviewService.getId(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Top demais", result.getComment());
        verify(movieReviewRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateMovieReview_Success() {
        MovieReview updatedReview = new MovieReview();
        updatedReview.setId(1);
        updatedReview.setComment("Comentário atualizado");

        when(movieReviewRepository.findById(1)).thenReturn(Optional.of(movieReview));
        when(movieReviewRepository.save(any(MovieReview.class))).thenReturn(updatedReview);

        MovieReview result = movieReviewService.update(updatedReview);

        assertNotNull(result);
        assertEquals("Comentário atualizado", result.getComment());
        verify(movieReviewRepository, times(1)).findById(1);
        verify(movieReviewRepository, times(1)).save(any(MovieReview.class));
    }

    @Test
    void testDeleteMovieReview_Success() {
        doNothing().when(movieReviewRepository).deleteById(1);

        movieReviewService.delete(1);

        verify(movieReviewRepository, times(1)).deleteById(1);
    }
}
