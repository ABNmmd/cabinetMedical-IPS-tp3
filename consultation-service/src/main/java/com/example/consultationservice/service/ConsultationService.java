package com.example.consultationservice.service;

import com.example.consultationservice.client.RendezVousFeignClient;
import com.example.consultationservice.dto.ConsultationDto;
import com.example.consultationservice.dto.RendezVousDto;
import com.example.consultationservice.model.Consultation;
import com.example.consultationservice.repository.ConsultationRepository;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultationService {

    private final ConsultationRepository repo;
    private final RendezVousFeignClient rendezVousFeignClient;

    public Consultation createConsultation(ConsultationDto consultationDto) throws Exception {
        // Le rendez-vous doit exister
        if (consultationDto.getRendezVousId() == null) {
            throw new Exception("Rendez-vous introuvable.");
        }

        RendezVousDto rendezVous;
        try {
            rendezVous = rendezVousFeignClient.getRendezVousById(consultationDto.getRendezVousId());
            if (rendezVous == null) {
                throw new Exception("Rendez-vous introuvable.");
            }
        } catch (FeignException.NotFound e) {
            throw new Exception("Rendez-vous introuvable.");
        } catch (FeignException e) {
            throw new Exception("Erreur lors de la vérification du rendez-vous.");
        }

        // La date de consultation est obligatoire
        if (consultationDto.getDateConsultation() == null) {
            throw new Exception("La date de consultation est obligatoire.");
        }

        // La date de consultation doit être ≥ à la date du rendez-vous
        if (consultationDto.getDateConsultation().isBefore(rendezVous.getDateRdv())) {
            throw new Exception("Date de consultation invalide.");
        }

        // Le rapport est obligatoire (au moins 10 caractères)
        if (consultationDto.getRapport() == null || consultationDto.getRapport().trim().length() < 10) {
            throw new Exception("Rapport de consultation insuffisant.");
        }

        Consultation consultation = new Consultation();
        consultation.setDateConsultation(consultationDto.getDateConsultation());
        consultation.setRapport(consultationDto.getRapport());
        consultation.setRendezVousId(consultationDto.getRendezVousId());

        return repo.save(consultation);
    }

    public List<Consultation> getAllConsultations() {
        return repo.findAll();
    }

    public Consultation getConsultationById(Long id) throws Exception {
        Optional<Consultation> consultation = repo.findById(id);
        if (consultation.isEmpty()) {
            throw new Exception("Consultation introuvable : id = " + id);
        }
        return consultation.get();
    }

    public Consultation getConsultationByRendezVousId(Long rendezVousId) throws Exception {
        // Vérifier que le rendez-vous existe
        try {
            RendezVousDto rendezVous = rendezVousFeignClient.getRendezVousById(rendezVousId);
            if (rendezVous == null) {
                throw new Exception("Rendez-vous introuvable.");
            }
        } catch (FeignException.NotFound e) {
            throw new Exception("Rendez-vous introuvable.");
        } catch (FeignException e) {
            throw new Exception("Erreur lors de la vérification du rendez-vous.");
        }

        Optional<Consultation> consultation = repo.findByRendezVousId(rendezVousId);
        if (consultation.isEmpty()) {
            throw new Exception("Aucune consultation trouvée pour ce rendez-vous : id = " + rendezVousId);
        }
        return consultation.get();
    }

    public Consultation updateConsultation(Long id, ConsultationDto updatedConsultationDto) throws Exception {
        Consultation existingConsultation = repo.findById(id).orElse(null);
        if (existingConsultation == null) {
            throw new Exception("Consultation introuvable : id = " + id);
        }

        // Récupérer le rendez-vous pour validation
        RendezVousDto rendezVous;
        try {
            rendezVous = rendezVousFeignClient.getRendezVousById(existingConsultation.getRendezVousId());
            if (rendezVous == null) {
                throw new Exception("Rendez-vous introuvable.");
            }
        } catch (FeignException.NotFound e) {
            throw new Exception("Rendez-vous introuvable.");
        } catch (FeignException e) {
            throw new Exception("Erreur lors de la vérification du rendez-vous.");
        }

        // Mettre à jour les champs
        if (updatedConsultationDto.getDateConsultation() != null
                && !updatedConsultationDto.getDateConsultation().equals(existingConsultation.getDateConsultation())) {
            // La date de consultation doit être ≥ à la date du rendez-vous
            if (updatedConsultationDto.getDateConsultation().isBefore(rendezVous.getDateRdv())) {
                throw new Exception("Date de consultation invalide.");
            }
            existingConsultation.setDateConsultation(updatedConsultationDto.getDateConsultation());
        }

        if (updatedConsultationDto.getRapport() != null
                && !updatedConsultationDto.getRapport().equals(existingConsultation.getRapport())) {
            if (updatedConsultationDto.getRapport().trim().length() < 10) {
                throw new Exception("Rapport de consultation insuffisant.");
            }
            existingConsultation.setRapport(updatedConsultationDto.getRapport());
        }

        if (updatedConsultationDto.getRendezVousId() != null
                && !updatedConsultationDto.getRendezVousId().equals(existingConsultation.getRendezVousId())) {
            // Vérifier que le nouveau rendez-vous existe
            RendezVousDto newRendezVous;
            try {
                newRendezVous = rendezVousFeignClient.getRendezVousById(updatedConsultationDto.getRendezVousId());
                if (newRendezVous == null) {
                    throw new Exception("Rendez-vous introuvable.");
                }
            } catch (FeignException.NotFound e) {
                throw new Exception("Rendez-vous introuvable.");
            } catch (FeignException e) {
                throw new Exception("Erreur lors de la vérification du rendez-vous.");
            }

            // Valider la date de consultation avec le nouveau rendez-vous
            if (existingConsultation.getDateConsultation().isBefore(newRendezVous.getDateRdv())) {
                throw new Exception("Date de consultation invalide.");
            }
            existingConsultation.setRendezVousId(updatedConsultationDto.getRendezVousId());
        }

        return repo.save(existingConsultation);
    }

    public void deleteConsultation(Long id) {
        Consultation consultation = repo.findById(id).orElse(null);
        if (consultation != null) {
            repo.delete(consultation);
        }
    }
}

