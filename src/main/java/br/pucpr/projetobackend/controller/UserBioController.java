package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.MovieDTO;
import br.pucpr.projetobackend.dto.RatingDTO;
import br.pucpr.projetobackend.dto.UserBioDTO;
import br.pucpr.projetobackend.dto.UserDTO;
import br.pucpr.projetobackend.exception.BusinessException;
import br.pucpr.projetobackend.model.User;
import br.pucpr.projetobackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController //responde requisicoes p json
@RequestMapping("/api/v1/biographies") //cria endereco das rotas
@Tag(name = "Biograhpies", description = "Biography for User")
@AllArgsConstructor
public class UserBioController {

    private List<UserBioDTO> biographies = new ArrayList<>();

    // MARK: CREATE
    @PostMapping("/{userID}")
    @Operation(summary = "Save a bio for existent user", description = "Save a bio for existent user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Saving - POST!"),
            @ApiResponse(responseCode = "400", description = "The data are wrong."),
    })
    public ResponseEntity<UserBioDTO> save(@PathVariable Integer userID, @Valid @RequestBody UserBioDTO bioDTO) {
        bioDTO.setId(userID);
        biographies.add(bioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bioDTO);

        //essa paradinha é a adição no banco via service
//        User user = new ModelMapper().map(bioDTO, User.class);
//        userService.save(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(bioDTO);
    }


    // MARK: READ
    @GetMapping
    @Operation(summary = "Get ALL bios", description = "Get ALL bios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET all bios."),
    })
    public List<UserBioDTO> findAll() {
        return biographies;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bio by ID", description = "Get bio by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET Bio."),
            @ApiResponse(responseCode = "404", description = "Bio not found."),
    })
    public UserBioDTO findById(@PathVariable("id") Integer id) {
        for (UserBioDTO tempBioDTO : biographies) {
            if (tempBioDTO.getId().equals(id)) {
                return tempBioDTO;
            }
        }
        return null;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all bios by user ID.", description = "Get all bios by movie ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET."),
            @ApiResponse(responseCode = "400", description = "The data are wrong."),
    })
    public List<UserBioDTO> findByUserId(@PathVariable("userId") Integer userId) {
        List<UserBioDTO> result = new ArrayList<>();

        for (UserBioDTO tempBioDTO : biographies) {
            if (tempBioDTO.getUserId().equals(userId)) {
                result.add(tempBioDTO);
            }
        }
        return result;
    }

    //MARK: UPDATE
    @PutMapping("/{id}")
    @Operation(summary = "Update a BIO by ID", description = "Update a BIO by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Updating - PUT!"),
    })
    public UserBioDTO update(@PathVariable("id") Integer id, @RequestBody UserBioDTO bioDTO) {
        for (UserBioDTO tempBioDTO : biographies) {
            if (tempBioDTO.getId().equals(id)) {
                tempBioDTO.setId(bioDTO.getId());
                tempBioDTO.setUserId(bioDTO.getUserId());
                tempBioDTO.setBiography(bioDTO.getBiography());
                return tempBioDTO;
            }
        }
        return null;
    }

    //MARK: DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie by ID", description = "Delete a movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Deleting - DELETE!"),
    })
    public void delete(@PathVariable("id") Integer id) {
        biographies.removeIf(bioDTO -> id.equals(bioDTO.getId()));
    }
}

