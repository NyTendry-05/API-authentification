package mg.itu.api.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Utilisateur {
    @Id
    private Long id_utilisateur;
    private String email;
    private String identifiant;
    private String mot_de_passe;
    private Boolean is_valide; 
    private String code_validation;
}

