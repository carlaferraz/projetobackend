package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.dto.RatingDTO;
import br.pucpr.projetobackend.model.MovieRating;
import br.pucpr.projetobackend.repository.MovieRatingRepository;
import br.pucpr.projetobackend.repository.MovieRepository;
import br.pucpr.projetobackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieRatingService {
    private final MovieRatingRepository repository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public List<MovieRating> findAll() {
        return repository.findAll();
    }

    public MovieRating findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
    }

    public List<MovieRating> findByMovieId(Integer movieId) {
        return repository.findByMovieId(movieId);
    }

    public List<MovieRating> findByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }

    public MovieRating save(RatingDTO dto) {
        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        var entity = dto.toEntity(user, movie);
        return repository.save(entity);
    }

    public MovieRating update(Integer id, RatingDTO dto) {
        var rating = findById(id);
        rating.setRating(dto.getRating());
        return repository.save(rating);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
