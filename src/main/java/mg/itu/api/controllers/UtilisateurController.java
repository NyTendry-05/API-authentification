package mg.itu.api.controllers;

import mg.itu.api.models.Utilisateur;
import mg.itu.api.services.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/verifierCodeValidation")
    public ResponseEntity<String> verifierCodeValidation(@RequestParam String codeValidation, HttpSession session) {
        String codeSession = (String) session.getAttribute("codeValidation");
        if (codeSession != null && codeSession.equals(codeValidation)) {
            return ResponseEntity.ok("Code de validation correct.");
        } else {
            return ResponseEntity.status(400).body("Code de validation incorrect.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email,
            @RequestParam String motDePasse,
            HttpSession session) {
        Utilisateur user = utilisateurService.verifierEmailMdp(email, motDePasse);
        if (user != null) {
            session.setAttribute("codeValidation", user.getCode_validation());
            session.setAttribute("valideLogin",false);
            session.setAttribute("nbTentatives",0);

            /*Ajouter envoi du code par mail */

            return ResponseEntity.ok("Code de validation généré et enregistré dans la session.");
        } else {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect.");
        }
    }

    @GetMapping("/sendLoginCode")
    public ResponseEntity<String> verifCode(@RequestParam String code,
            HttpSession session) {
        // Récupérer le code stocké dans la session
        String codeValidation = (String) session.getAttribute("codeValidation");

        // Vérifier si le codeValidation est présent dans la session
        if (codeValidation == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aucun code de validation trouvé dans la session.");
        }

        // Comparer le code reçu avec le code stocké
        if (code.equals(codeValidation)) {
            // Supprimer le code de la session après vérification réussie
            session.removeAttribute("codeValidation");
            session.setAttribute("valideLogin", codeValidation);
            return ResponseEntity.ok("Code validé avec succès.");
        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Code invalide.");
        }
    }

    @Operation(summary = "Create a new utilisateur", description = "Registers a new utilisateur in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid utilisateur data")
    })
    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        utilisateur.setCode_validation(utilisateurService.genererCodeValidation());
        utilisateur.setIs_valide(false);
        Utilisateur savedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);

        /*Ajouter code envoi de mail*/

        return new ResponseEntity<>(savedUtilisateur, HttpStatus.CREATED);
    }
}
