package br.pucpr.projetobackend.service;

import br.pucpr.projetobackend.dto.ActorDTO;
import br.pucpr.projetobackend.exception.ResourceNotFoundException;
import br.pucpr.projetobackend.model.Actor;
import br.pucpr.projetobackend.repository.ActorRepository;
import br.pucpr.projetobackend.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ActorService actorService;

    private Actor actor;
    private ActorDTO actorDTO;

    @BeforeEach
    void setUp() {
        actor = new Actor();
        actor.setId(1);
        actor.setName("Leonardo DiCaprio");
        actor.setBirthDate(LocalDate.of(1974, 11, 11));
        actor.setNationality("American");
        actor.setBiography("Academy Award winner");
        actor.setPhotoUrl("http://example.com/leo.jpg");

        actorDTO = new ActorDTO();
        actorDTO.setId(1);
        actorDTO.setName("Leonardo DiCaprio");
        actorDTO.setBirthDate(LocalDate.of(1974, 11, 11));
        actorDTO.setNationality("American");
        actorDTO.setBiography("Academy Award winner");
        actorDTO.setPhotoUrl("http://example.com/leo.jpg");
    }

    @Test
    void testCreateActor_Success() {
        when(actorRepository.save(any(Actor.class))).thenReturn(actor);

        ActorDTO result = actorService.create(actorDTO);

        assertNotNull(result);
        assertEquals("Leonardo DiCaprio", result.getName());
        assertEquals("American", result.getNationality());
        verify(actorRepository, times(1)).save(any(Actor.class));
    }

    @Test
    void testFindAllActors_Success() {
        List<Actor> actors = Arrays.asList(actor);
        when(actorRepository.findAll()).thenReturn(actors);

        List<ActorDTO> result = actorService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Leonardo DiCaprio", result.get(0).getName());
        verify(actorRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        when(actorRepository.findById(1)).thenReturn(Optional.of(actor));

        ActorDTO result = actorService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Leonardo DiCaprio", result.getName());
        verify(actorRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_NotFound() {
        when(actorRepository.findById(999)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> actorService.findById(999)
        );

        assertTrue(exception.getMessage().contains("Actor not found"));
        verify(actorRepository, times(1)).findById(999);
    }

    @Test
    void testSearchByName_Success() {
        List<Actor> actors = Arrays.asList(actor);
        when(actorRepository.findByNameContainingIgnoreCase("leo")).thenReturn(actors);

        List<ActorDTO> result = actorService.searchByName("leo");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Leonardo DiCaprio", result.get(0).getName());
        verify(actorRepository, times(1)).findByNameContainingIgnoreCase("leo");
    }

    @Test
    void testUpdateActor_Success() {
        when(actorRepository.findById(1)).thenReturn(Optional.of(actor));
        when(actorRepository.save(any(Actor.class))).thenReturn(actor);

        ActorDTO updatedDTO = new ActorDTO();
        updatedDTO.setName("Leo DiCaprio");
        updatedDTO.setBirthDate(LocalDate.of(1974, 11, 11));
        updatedDTO.setNationality("American");
        updatedDTO.setBiography("Updated bio");
        updatedDTO.setPhotoUrl("http://example.com/new.jpg");

        ActorDTO result = actorService.update(1, updatedDTO);

        assertNotNull(result);
        verify(actorRepository, times(1)).findById(1);
        verify(actorRepository, times(1)).save(any(Actor.class));
    }

    @Test
    void testUpdateActor_NotFound() {
        when(actorRepository.findById(999)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> actorService.update(999, actorDTO)
        );

        assertTrue(exception.getMessage().contains("Actor not found"));
        verify(actorRepository, times(1)).findById(999);
        verify(actorRepository, never()).save(any(Actor.class));
    }

    @Test
    void testDeleteActor_Success() {
        when(actorRepository.existsById(1)).thenReturn(true);
        doNothing().when(actorRepository).deleteById(1);

        actorService.delete(1);

        verify(actorRepository, times(1)).existsById(1);
        verify(actorRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteActor_NotFound() {
        when(actorRepository.existsById(999)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> actorService.delete(999)
        );

        assertTrue(exception.getMessage().contains("Actor not found"));
        verify(actorRepository, times(1)).existsById(999);
        verify(actorRepository, never()).deleteById(anyInt());
    }
}

