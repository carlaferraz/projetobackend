package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.dto.ActorDTO;
import br.pucpr.projetobackend.exception.ResourceNotFoundException;
import br.pucpr.projetobackend.model.Actor;
import br.pucpr.projetobackend.model.Movie;
import br.pucpr.projetobackend.repository.ActorRepository;
import br.pucpr.projetobackend.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    public ActorDTO create(ActorDTO actorDTO) {
        Actor actor = new Actor();
        actor.setName(actorDTO.getName());
        actor.setBirthDate(actorDTO.getBirthDate());
        actor.setNationality(actorDTO.getNationality());
        actor.setBiography(actorDTO.getBiography());
        actor.setPhotoUrl(actorDTO.getPhotoUrl());

        if (actorDTO.getMovieIds() != null && !actorDTO.getMovieIds().isEmpty()) {
            List<Movie> movies = movieRepository.findAllById(actorDTO.getMovieIds());
            actor.setMovies(movies);
        }

        Actor savedActor = actorRepository.save(actor);
        return convertToDTO(savedActor);
    }

    public List<ActorDTO> findAll() {
        return actorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ActorDTO findById(Integer id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor not found", "id: " + id));
        return convertToDTO(actor);
    }

    public List<ActorDTO> searchByName(String name) {
        return actorRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ActorDTO update(Integer id, ActorDTO actorDTO) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor not found", "id: " + id));

        actor.setName(actorDTO.getName());
        actor.setBirthDate(actorDTO.getBirthDate());
        actor.setNationality(actorDTO.getNationality());
        actor.setBiography(actorDTO.getBiography());
        actor.setPhotoUrl(actorDTO.getPhotoUrl());

        Actor updatedActor = actorRepository.save(actor);
        return convertToDTO(updatedActor);
    }

    public void delete(Integer id) {
        if (!actorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Actor not found", "id: " + id);
        }
        actorRepository.deleteById(id);
    }

    private ActorDTO convertToDTO(Actor actor) {
        ActorDTO dto = new ActorDTO();
        dto.setId(actor.getId());
        dto.setName(actor.getName());
        dto.setBirthDate(actor.getBirthDate());
        dto.setNationality(actor.getNationality());
        dto.setBiography(actor.getBiography());
        dto.setPhotoUrl(actor.getPhotoUrl());

        if (actor.getMovies() != null) {
            dto.setMovieIds(actor.getMovies().stream()
                    .map(Movie::getId)
                    .collect(Collectors.toList()));
        } else {
            dto.setMovieIds(new ArrayList<>());
        }

        return dto;
    }
}

