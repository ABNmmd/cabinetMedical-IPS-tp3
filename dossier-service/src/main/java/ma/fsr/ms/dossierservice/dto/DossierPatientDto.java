package ma.fsr.ms.dossierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DossierPatientDto {
    private PatientDto patient;
    private List<RendezVousDto> rendezVous;
    private List<ConsultationDto> consultations;
}

