package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.model.MovieReview;
import br.pucpr.projetobackend.model.User;
import br.pucpr.projetobackend.repository.MovieReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieReviewService {

    @Autowired
    private MovieReviewRepository repositoryMovieReview;

    public void save(MovieReview movieReview){
        repositoryMovieReview.save(movieReview);
    }

    public void delete(Integer id){
        repositoryMovieReview.deleteById(id);
    }

    public MovieReview getId(Integer id){
        return repositoryMovieReview.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public List<MovieReview> getAll(){
        return repositoryMovieReview.findAll();
    }

    public MovieReview update(MovieReview movieReview) {
        MovieReview entity = getId(movieReview.getId());

        entity.setComment(movieReview.getComment());
        entity.setUser(movieReview.getUser());
        entity.setMovie(movieReview.getMovie());

        return repositoryMovieReview.save(entity);
    }
}
