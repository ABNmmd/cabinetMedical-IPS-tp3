package ma.fsr.ms.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientUpdateDto {
    private String nom;
    private LocalDate dateNaissance;
    private String genre;
    private String telephone;
}
