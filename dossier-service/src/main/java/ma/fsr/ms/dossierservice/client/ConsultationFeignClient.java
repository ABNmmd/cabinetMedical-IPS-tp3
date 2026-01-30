package ma.fsr.ms.dossierservice.client;

import ma.fsr.ms.dossierservice.dto.ConsultationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "consultation-service", url = "${consultation.service.url}")
public interface ConsultationFeignClient {
    @GetMapping("/internal/api/v1/consultations/rendezvous/{id}")
    ConsultationDto getConsultationByRendezVousId(@PathVariable Long id);
}

