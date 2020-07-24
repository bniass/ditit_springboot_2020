package cours.java.rhwedappspring.dao;

import cours.java.rhwedappspring.model.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {
    public List<Specialite> findByService_Id(long idService);
}
