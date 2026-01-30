package ma.fsr.ms.rendezvousservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedecinDto {
    private  Long id;
    private String nom;
    private String email;
    private String specialite;
}
