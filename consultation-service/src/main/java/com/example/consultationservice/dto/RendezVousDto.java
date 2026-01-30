package com.example.consultationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RendezVousDto {
    private Long id;
    private LocalDate dateRdv;
    private String statut;
    private Long patientId;
    private Long medecinId;
}

