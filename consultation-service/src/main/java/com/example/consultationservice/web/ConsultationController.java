package com.example.consultationservice.web;

import com.example.consultationservice.dto.ConsultationDto;
import com.example.consultationservice.model.Consultation;
import com.example.consultationservice.service.ConsultationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/consultations")
@AllArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping
    public List<Consultation> getAllConsultations() {
        return consultationService.getAllConsultations();
    }

    @GetMapping("/{id}")
    public Consultation getConsultationById(@PathVariable Long id) throws Exception {
        return consultationService.getConsultationById(id);
    }

    @GetMapping("/rendezvous/{id}")
    public Consultation getConsultationByRendezVousId(@PathVariable Long id) throws Exception {
        return consultationService.getConsultationByRendezVousId(id);
    }

    @PostMapping
    public Consultation createConsultation(@RequestBody ConsultationDto consultationDto) throws Exception {
        return consultationService.createConsultation(consultationDto);
    }

    @PutMapping("/{id}")
    public Consultation updateConsultation(@PathVariable Long id, @RequestBody ConsultationDto consultationDto) throws Exception {
        return consultationService.updateConsultation(id, consultationDto);
    }

    @DeleteMapping("/{id}")
    public void deleteConsultation(@PathVariable Long id) {
        consultationService.deleteConsultation(id);
    }
}
