package com.example.consultationservice.repository;

import com.example.consultationservice.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    Optional<Consultation> findByRendezVousId(Long rendezVousId);
}
