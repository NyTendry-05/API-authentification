package mg.itu.api.services;

import mg.itu.api.models.Utilisateur;
import mg.itu.api.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Utilisateur verifierEmailMdp(String email, String motDePasse) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);
        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            if (passwordEncoder.matches(motDePasse, utilisateur.getMot_de_passe())) {
                String codeValidation = genererCodeValidation();
                utilisateur.setCode_validation(codeValidation);
                utilisateurRepository.save(utilisateur);
                return utilisateur; 
            }
        }
        return null;
    }

    /**
     * Generates a random validation code.
     */
    private String genererCodeValidation() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);  // 6-digit code
        return String.valueOf(code);
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Optional<Utilisateur> getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }
}

