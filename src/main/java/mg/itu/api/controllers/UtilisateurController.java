package mg.itu.api.controllers;

import mg.itu.api.models.Utilisateur;
import mg.itu.api.services.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email,
            @RequestParam String motDePasse,
            HttpSession session) {
        Utilisateur user = utilisateurService.verifierEmailMdp(email, motDePasse);
        if (user != null) {
            session.setAttribute("codeValidation", user.getCode_validation());
            return ResponseEntity.ok("Code de validation généré et enregistré dans la session.");
        } else {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect.");
        }
    }

    @Operation(summary = "Create a new utilisateur", description = "Registers a new utilisateur in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid utilisateur data")
    })
    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        Utilisateur savedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);
        return new ResponseEntity<>(savedUtilisateur, HttpStatus.CREATED);
    }

    // Get utilisateur by ID
    @Operation(summary = "Get utilisateur by ID", description = "Fetches an utilisateur by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur found"),
            @ApiResponse(responseCode = "404", description = "Utilisateur not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(
            @Parameter(description = "ID of the utilisateur to be fetched") @PathVariable Long id) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(id);
        return utilisateur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get utilisateur by email
    @Operation(summary = "Get utilisateur by email", description = "Fetches an utilisateur by their email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur found"),
            @ApiResponse(responseCode = "404", description = "Utilisateur not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<Utilisateur> getUtilisateurByEmail(
            @Parameter(description = "Email of the utilisateur to be fetched") @PathVariable String email) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurByEmail(email);
        return utilisateur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Update an utilisateur
    @Operation(summary = "Update an existing utilisateur", description = "Updates the details of an existing utilisateur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur updated successfully"),
            @ApiResponse(responseCode = "404", description = "Utilisateur not found"),
            @ApiResponse(responseCode = "400", description = "Invalid utilisateur data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(
            @PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        Optional<Utilisateur> existingUtilisateur = utilisateurService.getUtilisateurById(id);
        if (existingUtilisateur.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        utilisateur.setId_utilisateur(id); // Ensure we're updating the correct utilisateur
        Utilisateur updatedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);
        return ResponseEntity.ok(updatedUtilisateur);
    }
}
