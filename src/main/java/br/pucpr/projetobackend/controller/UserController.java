package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.dto.UserDTO;
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

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Users API")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

//    private List<UserDTO> users = new ArrayList<>();


// MARK: CREATE
    @PostMapping
    @Operation(summary = "Save a user", description = "Save a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Saving - POST!"),
            @ApiResponse(responseCode = "400", description = "The user data are wrong."),
    })
    //requestbody: pega o json e transforma em obj userdto
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO usuarioDTO) {
        User user = new ModelMapper().map(usuarioDTO, User.class);
        User savedUser = userService.save(user);
        UserDTO responseDTO = new ModelMapper().map(savedUser, UserDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


// MARK: READ
    @GetMapping
    @Operation(summary = "Get ALL users", description = "Get ALL users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET all users."),
    })
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userService.getAll();
        List<UserDTO> userVOs = users.stream().map(user -> new ModelMapper().map(user, UserDTO.class)).
                toList();
        return new ResponseEntity<>(userVOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET user."),
            @ApiResponse(responseCode = "404", description = "User not found."),
    })
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Integer id) {
        User user = userService.getId(id);

        UserDTO dto = new ModelMapper().map(user, UserDTO.class);

        return ResponseEntity.ok(dto);
//        return ResponseEntity.ok().body(userService.getId(id));
    }


    // MARK: UPDATE
    @PutMapping("/{id}")
    @Operation(summary = "Update a user by ID", description = "Update a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Updating - PUT!"),
    })
    public ResponseEntity<UserDTO> update(@PathVariable("id") Integer id, @RequestBody UserDTO usuarioDTO) {
        if (id == null || usuarioDTO.getId() == null) {
            throw new IllegalArgumentException("The ID is required");
        }

        User user = new ModelMapper().map(usuarioDTO, User.class);
        userService.update(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }
//    public ResponseEntity<UserDTO> update(@PathVariable("id") Integer id, @RequestBody UserDTO userDTO) {
//        for (UserDTO tempUserDTO : users) {
//            if (tempUserDTO.getId().equals(id)) {
//                tempUserDTO.setId(userDTO.getId());
//                tempUserDTO.setName(userDTO.getName());
//                tempUserDTO.setEmail(userDTO.getEmail());
//                return ResponseEntity.ok(tempUserDTO);
//            }
//        }
//        return ResponseEntity.notFound().build();
//    }


// MARK: DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by ID", description = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Deleting - DELETE!"),
    })
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
//        boolean removed = users.removeIf(userDTO -> id.equals(userDTO.getId()));
        userService.delete(id);
        return ResponseEntity.noContent().build();

//        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
