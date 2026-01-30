package ma.fsr.ms.medecinservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedecinUpdateDto {
    private String nom;
    private String specialite;
    private String email;
}
