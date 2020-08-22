package cours.java.rhwedappspring.dao;

import cours.java.rhwedappspring.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    public Utilisateur findByUsername(String username);
}
