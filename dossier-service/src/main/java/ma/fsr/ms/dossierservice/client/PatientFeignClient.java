package ma.fsr.ms.dossierservice.client;

import ma.fsr.ms.dossierservice.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service", url = "${patient.service.url}")
public interface PatientFeignClient {
    @GetMapping("/internal/api/v1/patients/{id}")
    PatientDto getPatientById(@PathVariable Long id);
}

