package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.model.Movie;
import br.pucpr.projetobackend.model.MovieReview;
import br.pucpr.projetobackend.repository.MovieRepository;
import br.pucpr.projetobackend.repository.MovieReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repositoryMovie;

    public void save(Movie movie){
        repositoryMovie.save(movie);
    }

    public void delete(Integer id){
        repositoryMovie.deleteById(id);
    }

    public Movie getId(Integer id){
        Optional<Movie> movie = repositoryMovie.findById(id);
        return movie.get();
    }

    public List<Movie> getAll(){
        return repositoryMovie.findAll();
    }

    public Movie update(Movie movie) {
        Movie entity = getId(movie.getId());

        entity.setTitle(movie.getTitle());
        //adicionar entitity do genre aqui

        return repositoryMovie.save(entity);
    }
}
