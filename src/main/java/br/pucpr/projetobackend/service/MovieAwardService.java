package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.dto.MovieAwardDTO;
import br.pucpr.projetobackend.model.Movie;
import br.pucpr.projetobackend.model.MovieAward;
import br.pucpr.projetobackend.repository.MovieAwardRepository;
import br.pucpr.projetobackend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieAwardService {

    private final MovieAwardRepository awardRepository;
    private final MovieRepository movieRepository;

    public List<MovieAwardDTO> findAll() {
        return awardRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MovieAwardDTO findById(Integer id) {
        return awardRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Award not found"));
    }

    public List<MovieAwardDTO> findByMovieId(Integer movieId) {
        return awardRepository.findByMovieId(movieId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MovieAwardDTO save(MovieAwardDTO dto) {
        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        MovieAward award = new MovieAward();
        award.setName(dto.getName());
        award.setCategory(dto.getCategory());
        award.setAwardDate(dto.getAwardDate());
        award.setMovie(movie);

        return toDTO(awardRepository.save(award));
    }

    public MovieAwardDTO update(Integer id, MovieAwardDTO dto) {
        MovieAward award = awardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Award not found"));

        award.setName(dto.getName());
        award.setCategory(dto.getCategory());
        award.setAwardDate(dto.getAwardDate());

        return toDTO(awardRepository.save(award));
    }

    public void delete(Integer id) {
        awardRepository.deleteById(id);
    }

    private MovieAwardDTO toDTO(MovieAward award) {
        return new MovieAwardDTO(
                award.getId(),
                award.getName(),
                award.getCategory(),
                award.getAwardDate(),
                award.getMovie().getId()
        );
    }
}
