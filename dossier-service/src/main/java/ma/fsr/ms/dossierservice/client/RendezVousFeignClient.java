package ma.fsr.ms.dossierservice.client;

import ma.fsr.ms.dossierservice.dto.RendezVousDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "rendezvous-service", url = "${rendezvous.service.url}")
public interface RendezVousFeignClient {
    @GetMapping("/internal/api/v1/rendezvous/patient/{id}")
    List<RendezVousDto> getRendezVousByPatientId(@PathVariable Long id);
}

