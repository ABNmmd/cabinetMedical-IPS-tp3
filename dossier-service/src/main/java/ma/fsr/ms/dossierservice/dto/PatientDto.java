package ma.fsr.ms.dossierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private Long id;
    private String nom;
    private LocalDate dateNaissance;
    private String genre;
    private String telephone;
}

