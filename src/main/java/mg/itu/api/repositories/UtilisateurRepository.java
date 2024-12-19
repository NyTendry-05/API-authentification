package mg.itu.api.repositories;

import mg.itu.api.models.Utilisateur;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    Optional<Utilisateur> findByIdentifiant(String identifiant);

    @Query("SELECT * FROM utilisateur WHERE is_valide = :isValide")
    Iterable<Utilisateur> findAllByIsValide(Boolean isValide);
}
