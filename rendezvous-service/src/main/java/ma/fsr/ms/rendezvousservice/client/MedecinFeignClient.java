package ma.fsr.ms.rendezvousservice.client;

import ma.fsr.ms.rendezvousservice.dto.MedecinDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "medecin-service", url = "${medecin.service.url}")
public interface MedecinFeignClient {
    @GetMapping("/internal/api/v1/medecins/{id}")
    MedecinDto getMedecinById(@PathVariable Long id);
}
