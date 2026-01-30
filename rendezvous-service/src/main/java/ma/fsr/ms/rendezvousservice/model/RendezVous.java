package ma.fsr.ms.rendezvousservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateRdv;

    @Enumerated(EnumType.STRING)
    private StatutRdv statut;

    private Long patientId;
    private Long medecinId;
}
