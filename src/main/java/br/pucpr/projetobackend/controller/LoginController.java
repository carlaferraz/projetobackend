package br.pucpr.projetobackend.controller;

import br.pucpr.projetobackend.security.AuthRequest;
import br.pucpr.projetobackend.security.AuthResponse;
import br.pucpr.projetobackend.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/login")
@Tag(name = "Authentication", description = "Authentication endpoints")
@AllArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping
    @Operation(summary = "Login user", description = "Authenticate user with email and password")
    public ResponseEntity<AuthResponse> authenticate(
            @Valid @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
