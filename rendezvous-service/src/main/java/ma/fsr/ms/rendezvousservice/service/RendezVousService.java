package ma.fsr.ms.rendezvousservice.service;

import feign.FeignException;
import lombok.AllArgsConstructor;
import ma.fsr.ms.rendezvousservice.client.MedecinFeignClient;
import ma.fsr.ms.rendezvousservice.client.PatientFeignClient;
import ma.fsr.ms.rendezvousservice.dto.MedecinDto;
import ma.fsr.ms.rendezvousservice.dto.PatientDto;
import ma.fsr.ms.rendezvousservice.dto.RendezVousDto;
import ma.fsr.ms.rendezvousservice.model.RendezVous;
import ma.fsr.ms.rendezvousservice.model.StatutRdv;
import ma.fsr.ms.rendezvousservice.repository.RendezVousRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RendezVousService {

    private final RendezVousRepository repo;
    private final PatientFeignClient patientFeignClient;
    private final MedecinFeignClient medecinFeignClient;

    public RendezVous createRendezVous(RendezVousDto rendezVousDto) throws Exception {
        // La date du rendez-vous doit être postérieure à la date actuelle
        if (rendezVousDto.getDateRdv() == null || !rendezVousDto.getDateRdv().isAfter(LocalDate.now())) {
            throw new Exception("La date du rendez-vous doit être future.");
        }

        // Le patient doit exister
        if (rendezVousDto.getPatientId() == null) {
            throw new Exception("Patient introuvable.");
        }

        try {
            PatientDto patient = patientFeignClient.getPatientById(rendezVousDto.getPatientId());
            if (patient == null) {
                throw new Exception("Patient introuvable.");
            }
        } catch (FeignException.NotFound e) {
            throw new Exception("Patient introuvable.");
        } catch (FeignException e) {
            throw new Exception("Erreur lors de la vérification du patient.");
        }

        // Le médecin doit exister
        if (rendezVousDto.getMedecinId() == null) {
            throw new Exception("Médecin introuvable.");
        }

        try {
            MedecinDto medecin = medecinFeignClient.getMedecinById(rendezVousDto.getMedecinId());
            if (medecin == null) {
                throw new Exception("Médecin introuvable.");
            }
        } catch (FeignException.NotFound e) {
            throw new Exception("Médecin introuvable.");
        } catch (FeignException e) {
            throw new Exception("Erreur lors de la vérification du médecin.");
        }

        // Statut initial par défaut : PLANIFIE
        StatutRdv statut = rendezVousDto.getStatut();
        if (statut == null) {
            statut = StatutRdv.PLANIFIE;
        } else {
            // Valider le statut
            validateStatut(statut);
        }

        RendezVous rendezVous = new RendezVous();
        rendezVous.setDateRdv(rendezVousDto.getDateRdv());
        rendezVous.setStatut(statut);
        rendezVous.setPatientId(rendezVousDto.getPatientId());
        rendezVous.setMedecinId(rendezVousDto.getMedecinId());

        return repo.save(rendezVous);
    }

    public List<RendezVous> getAllRendezVous() {
        return repo.findAll();
    }

    public RendezVous getRendezVousById(Long id) throws Exception {
        Optional<RendezVous> rendezVous = repo.findById(id);
        if (rendezVous.isEmpty()) {
            throw new Exception("Rendez-vous introuvable : id = " + id);
        }
        return rendezVous.get();
    }

    public RendezVous updateRendezVous(Long id, RendezVousDto updatedRendezVousDto) throws Exception {
        RendezVous existingRendezVous = repo.findById(id).orElse(null);
        if (existingRendezVous == null) {
            throw new Exception("Rendez-vous introuvable : id = " + id);
        }

        // Mettre à jour les champs
        if (updatedRendezVousDto.getDateRdv() != null && !updatedRendezVousDto.getDateRdv().equals(existingRendezVous.getDateRdv())) {
            if (!updatedRendezVousDto.getDateRdv().isAfter(LocalDate.now())) {
                throw new Exception("La date du rendez-vous doit être future.");
            }
            existingRendezVous.setDateRdv(updatedRendezVousDto.getDateRdv());
        }

        if (updatedRendezVousDto.getStatut() != null && !updatedRendezVousDto.getStatut().equals(existingRendezVous.getStatut())) {
            validateStatut(updatedRendezVousDto.getStatut());
            existingRendezVous.setStatut(updatedRendezVousDto.getStatut());
        }

        if (updatedRendezVousDto.getPatientId() != null && !updatedRendezVousDto.getPatientId().equals(existingRendezVous.getPatientId())) {
            try {
                PatientDto patient = patientFeignClient.getPatientById(updatedRendezVousDto.getPatientId());
                if (patient == null) {
                    throw new Exception("Patient introuvable.");
                }
            } catch (FeignException.NotFound e) {
                throw new Exception("Patient introuvable.");
            } catch (FeignException e) {
                throw new Exception("Erreur lors de la vérification du patient.");
            }
            existingRendezVous.setPatientId(updatedRendezVousDto.getPatientId());
        }

        if (updatedRendezVousDto.getMedecinId() != null && !updatedRendezVousDto.getMedecinId().equals(existingRendezVous.getMedecinId())) {
            try {
                MedecinDto medecin = medecinFeignClient.getMedecinById(updatedRendezVousDto.getMedecinId());
                if (medecin == null) {
                    throw new Exception("Médecin introuvable.");
                }
            } catch (FeignException.NotFound e) {
                throw new Exception("Médecin introuvable.");
            } catch (FeignException e) {
                throw new Exception("Erreur lors de la vérification du médecin.");
            }
            existingRendezVous.setMedecinId(updatedRendezVousDto.getMedecinId());
        }

        return repo.save(existingRendezVous);
    }

    public void deleteRendezVous(Long id) {
        RendezVous rendezVous = repo.findById(id).orElse(null);
        if (rendezVous != null) {
            repo.delete(rendezVous);
        }
    }

    public List<RendezVous> getRendezVousByPatientId(Long patientId) throws Exception {
        // Vérifier que le patient existe
        try {
            PatientDto patient = patientFeignClient.getPatientById(patientId);
            if (patient == null) {
                throw new Exception("Patient introuvable : id = " + patientId);
            }
        } catch (FeignException.NotFound e) {
            throw new Exception("Patient introuvable : id = " + patientId);
        } catch (FeignException e) {
            throw new Exception("Erreur lors de la vérification du patient.");
        }

        return repo.findByPatientId(patientId);
    }

    public List<RendezVous> getRendezVousByMedecinId(Long medecinId) throws Exception {
        // Vérifier que le médecin existe
        try {
            MedecinDto medecin = medecinFeignClient.getMedecinById(medecinId);
            if (medecin == null) {
                throw new Exception("Médecin introuvable : id = " + medecinId);
            }
        } catch (FeignException.NotFound e) {
            throw new Exception("Médecin introuvable : id = " + medecinId);
        } catch (FeignException e) {
            throw new Exception("Erreur lors de la vérification du médecin.");
        }

        return repo.findByMedecinId(medecinId);
    }

    public RendezVous updateRendezVousStatus(Long id, StatutRdv statut) throws Exception {
        RendezVous existingRendezVous = repo.findById(id).orElse(null);
        if (existingRendezVous == null) {
            throw new Exception("Rendez-vous introuvable : id = " + id);
        }

        // Valider le statut
        validateStatut(statut);

        existingRendezVous.setStatut(statut);
        return repo.save(existingRendezVous);
    }

    private void validateStatut(StatutRdv statut) throws Exception {
        if (statut == null) {
            throw new Exception("Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE.");
        }
        // L'enum garantit déjà que seules les valeurs valides sont utilisées
        // Mais on peut ajouter une vérification explicite si nécessaire
        try {
            StatutRdv.valueOf(statut.name());
        } catch (IllegalArgumentException e) {
            throw new Exception("Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE.");
        }
    }
}
