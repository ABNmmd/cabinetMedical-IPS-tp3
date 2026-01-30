package ma.fsr.ms.dossierservice.service;

import feign.FeignException;
import lombok.AllArgsConstructor;
import ma.fsr.ms.dossierservice.client.ConsultationFeignClient;
import ma.fsr.ms.dossierservice.client.PatientFeignClient;
import ma.fsr.ms.dossierservice.client.RendezVousFeignClient;
import ma.fsr.ms.dossierservice.dto.ConsultationDto;
import ma.fsr.ms.dossierservice.dto.DossierPatientDto;
import ma.fsr.ms.dossierservice.dto.PatientDto;
import ma.fsr.ms.dossierservice.dto.RendezVousDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DossierService {

    private final PatientFeignClient patientFeignClient;
    private final RendezVousFeignClient rendezVousFeignClient;
    private final ConsultationFeignClient consultationFeignClient;

    public DossierPatientDto getDossierByPatientId(Long patientId) throws Exception {
        // Récupérer les informations du patient
        PatientDto patient;
        try {
            patient = patientFeignClient.getPatientById(patientId);
            if (patient == null) {
                throw new Exception("Patient introuvable.");
            }
        } catch (FeignException.NotFound e) {
            throw new Exception("Patient introuvable.");
        } catch (FeignException e) {
            throw new Exception("Erreur lors de la récupération des données du patient.");
        }

        // Récupérer les rendez-vous du patient
        List<RendezVousDto> rendezVous = new ArrayList<>();
        try {
            rendezVous = rendezVousFeignClient.getRendezVousByPatientId(patientId);
            if (rendezVous == null) {
                rendezVous = new ArrayList<>();
            }
        } catch (FeignException.NotFound e) {
            // Pas de rendez-vous, on continue avec une liste vide
            rendezVous = new ArrayList<>();
        } catch (FeignException e) {
            // Erreur de communication, on continue avec une liste vide
            rendezVous = new ArrayList<>();
        }

        // Récupérer les consultations pour chaque rendez-vous
        List<ConsultationDto> consultations = new ArrayList<>();
        for (RendezVousDto rdv : rendezVous) {
            try {
                ConsultationDto consultation = consultationFeignClient.getConsultationByRendezVousId(rdv.getId());
                if (consultation != null) {
                    consultations.add(consultation);
                }
            } catch (FeignException.NotFound e) {
                // Pas de consultation pour ce rendez-vous, on continue
            } catch (FeignException e) {
                // Erreur de communication, on continue
            }
        }

        // Construire et retourner le dossier
        DossierPatientDto dossier = new DossierPatientDto();
        dossier.setPatient(patient);
        dossier.setRendezVous(rendezVous);
        dossier.setConsultations(consultations);

        return dossier;
    }
}

