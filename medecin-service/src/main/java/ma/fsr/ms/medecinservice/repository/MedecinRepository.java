package ma.fsr.ms.medecinservice.repository;

import ma.fsr.ms.medecinservice.model.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin,Long> {
}
