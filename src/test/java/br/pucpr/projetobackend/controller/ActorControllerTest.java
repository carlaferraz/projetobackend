package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.ActorDTO;
import br.pucpr.projetobackend.exception.ResourceNotFoundException;
import br.pucpr.projetobackend.service.ActorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ActorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ActorService actorService;

    private ActorDTO actorDTO;

    @BeforeEach
    void setUp() {
        actorDTO = new ActorDTO();
        actorDTO.setId(1);
        actorDTO.setName("Tom Hanks");
        actorDTO.setBirthDate(LocalDate.of(1956, 7, 9));
        actorDTO.setNationality("American");
        actorDTO.setBiography("Two-time Academy Award winner");
        actorDTO.setPhotoUrl("http://example.com/hanks.jpg");
    }

    @Test
    void testCreateActor_Success() throws Exception {
        when(actorService.create(any(ActorDTO.class))).thenReturn(actorDTO);

        mockMvc.perform(post("/api/v1/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tom Hanks"))
                .andExpect(jsonPath("$.nationality").value("American"));

        verify(actorService, times(1)).create(any(ActorDTO.class));
    }

    @Test
    void testCreateActor_ValidationError() throws Exception {
        ActorDTO invalidActor = new ActorDTO();
        invalidActor.setName("");

        mockMvc.perform(post("/api/v1/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidActor)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation error"));

        verify(actorService, never()).create(any(ActorDTO.class));
    }

    @Test
    void testFindAllActors_Success() throws Exception {
        List<ActorDTO> actors = Arrays.asList(actorDTO);
        when(actorService.findAll()).thenReturn(actors);

        mockMvc.perform(get("/api/v1/actors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Tom Hanks"))
                .andExpect(jsonPath("$.length()").value(1));

        verify(actorService, times(1)).findAll();
    }

    @Test
    void testFindById_Success() throws Exception {
        when(actorService.findById(1)).thenReturn(actorDTO);

        mockMvc.perform(get("/api/v1/actors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tom Hanks"))
                .andExpect(jsonPath("$.biography").value("Two-time Academy Award winner"));

        verify(actorService, times(1)).findById(1);
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(actorService.findById(999)).thenThrow(new ResourceNotFoundException("Actor not found", "id: 999"));

        mockMvc.perform(get("/api/v1/actors/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists());

        verify(actorService, times(1)).findById(999);
    }

    @Test
    void testSearchByName_Success() throws Exception {
        List<ActorDTO> actors = Arrays.asList(actorDTO);
        when(actorService.searchByName("tom")).thenReturn(actors);

        mockMvc.perform(get("/api/v1/actors/search")
                .param("name", "tom"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tom Hanks"))
                .andExpect(jsonPath("$.length()").value(1));

        verify(actorService, times(1)).searchByName("tom");
    }

    @Test
    void testUpdateActor_Success() throws Exception {
        ActorDTO updatedDTO = new ActorDTO();
        updatedDTO.setId(1);
        updatedDTO.setName("Thomas Hanks");
        updatedDTO.setBirthDate(LocalDate.of(1956, 7, 9));
        updatedDTO.setNationality("American");
        updatedDTO.setBiography("Updated biography");

        when(actorService.update(eq(1), any(ActorDTO.class))).thenReturn(updatedDTO);

        mockMvc.perform(put("/api/v1/actors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Thomas Hanks"))
                .andExpect(jsonPath("$.biography").value("Updated biography"));

        verify(actorService, times(1)).update(eq(1), any(ActorDTO.class));
    }

    @Test
    void testUpdateActor_NotFound() throws Exception {
        when(actorService.update(eq(999), any(ActorDTO.class)))
                .thenThrow(new ResourceNotFoundException("Actor not found", "id: 999"));

        mockMvc.perform(put("/api/v1/actors/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actorDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));

        verify(actorService, times(1)).update(eq(999), any(ActorDTO.class));
    }

    @Test
    void testDeleteActor_Success() throws Exception {
        doNothing().when(actorService).delete(1);

        mockMvc.perform(delete("/api/v1/actors/1"))
                .andExpect(status().isNoContent());

        verify(actorService, times(1)).delete(1);
    }

    @Test
    void testDeleteActor_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Actor not found", "id: 999"))
                .when(actorService).delete(999);

        mockMvc.perform(delete("/api/v1/actors/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));

        verify(actorService, times(1)).delete(999);
    }
}

