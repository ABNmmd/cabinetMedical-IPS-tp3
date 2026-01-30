package ma.fsr.ms.medecinservice.service;

import lombok.AllArgsConstructor;
import ma.fsr.ms.medecinservice.model.Medecin;
import ma.fsr.ms.medecinservice.repository.MedecinRepository;
import ma.fsr.ms.medecinservice.dto.MedecinUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MedecinService {

    private final MedecinRepository repo;

    public Medecin createMedecin(Medecin medecin) throws Exception {
        // Le nom est obligatoire
        if (medecin.getNom() == null || medecin.getNom().isEmpty()) {
            throw new Exception("Le nom du médecin est obligatoire");
        }

        // L'email est obligatoire
        if (medecin.getEmail() == null || medecin.getEmail().isEmpty()) {
            throw new Exception("L'email du médecin est obligatoire");
        }

        // L'email doit être valide (contenir @)
        if (!medecin.getEmail().contains("@")) {
            throw new Exception("Email du médecin invalide");
        }

        // La spécialité est obligatoire
        if (medecin.getSpecialite() == null || medecin.getSpecialite().isEmpty()) {
            throw new Exception("La spécialité du médecin est obligatoire");
        }

        return repo.save(medecin);
    }

    public List<Medecin> getAllMedecins() {
        return repo.findAll();
    }

    public Medecin getMedecinById(Long id) throws Exception {
        Optional<Medecin> medecin = repo.findById(id);
        if (medecin.isEmpty()) {
            throw new Exception("Médecin introuvable : id = " + id);
        }
        return medecin.get();
    }

    public Medecin updateMedecin(Long id, MedecinUpdateDto updatedMedecinDto) throws Exception {
        Medecin existingMedecin = repo.findById(id).orElse(null);
        if (existingMedecin == null) {
            throw new Exception("Médecin introuvable : id = " + id);
        }

        // Mettre à jour les champs

        if (updatedMedecinDto.getNom() != null && !updatedMedecinDto.getNom().equals(existingMedecin.getNom())) {
            if (updatedMedecinDto.getNom().isEmpty()) {
                throw new Exception("Le nom du médecin est obligatoire");
            }
            existingMedecin.setNom(updatedMedecinDto.getNom());
        }

        if (updatedMedecinDto.getEmail() != null && !updatedMedecinDto.getEmail().equals(existingMedecin.getEmail())) {
            if (updatedMedecinDto.getEmail().isEmpty()) {
                throw new Exception("L'email du médecin est obligatoire");
            }
            if (!updatedMedecinDto.getEmail().contains("@")) {
                throw new Exception("Email du médecin invalide");
            }
            existingMedecin.setEmail(updatedMedecinDto.getEmail());
        }

        if (updatedMedecinDto.getSpecialite() != null && !updatedMedecinDto.getSpecialite().equals(existingMedecin.getSpecialite())) {
            if (updatedMedecinDto.getSpecialite().isEmpty()) {
                throw new Exception("La spécialité du médecin est obligatoire");
            }
            existingMedecin.setSpecialite(updatedMedecinDto.getSpecialite());
        }

        return repo.save(existingMedecin);
    }

    public void deleteMedecin(Long id) {
        Medecin medecin = repo.findById(id).orElse(null);
        if (medecin != null) {
            repo.delete(medecin);
        }
    }
}
