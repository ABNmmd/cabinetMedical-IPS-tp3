package ma.fsr.ms.rendezvousservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.fsr.ms.rendezvousservice.model.StatutRdv;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RendezVousDto {
    private LocalDate dateRdv;
    private StatutRdv statut;
    private Long patientId;
    private Long medecinId;
}
