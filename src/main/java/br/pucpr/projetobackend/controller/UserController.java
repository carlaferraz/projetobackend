package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Users API")
public class UserController {

    private List<UserDTO> users = new ArrayList<>();

    @PostMapping
    @Operation(summary = "Save a user", description = "Save a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Saving - POST!"),
            @ApiResponse(responseCode = "400", description = "The user data are wrong."),
    })
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO userDTO) {
        users.add(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @GetMapping
    @Operation(summary = "Get ALL users", description = "Get ALL users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET all users."),
    })
    public List<UserDTO> findAll() {
        return users;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET user."),
            @ApiResponse(responseCode = "404", description = "User not found."),
    })
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Integer id) {
        for (UserDTO tempUserDTO : users) {
            if (tempUserDTO.getId().equals(id)) {
                return ResponseEntity.ok(tempUserDTO);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user by ID", description = "Update a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Updating - PUT!"),
    })
    public ResponseEntity<UserDTO> update(@PathVariable("id") Integer id, @RequestBody UserDTO userDTO) {
        for (UserDTO tempUserDTO : users) {
            if (tempUserDTO.getId().equals(id)) {
                tempUserDTO.setId(userDTO.getId());
                tempUserDTO.setName(userDTO.getName());
                tempUserDTO.setEmail(userDTO.getEmail());
                return ResponseEntity.ok(tempUserDTO);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by ID", description = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Deleting - DELETE!"),
    })
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boolean removed = users.removeIf(userDTO -> id.equals(userDTO.getId()));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
