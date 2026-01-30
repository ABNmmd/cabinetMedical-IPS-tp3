package com.example.consultationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDto {
    private LocalDate dateConsultation;
    private String rapport;
    private Long rendezVousId;
}
