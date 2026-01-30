package ma.fsr.ms.dossierservice.web;

import lombok.AllArgsConstructor;
import ma.fsr.ms.dossierservice.dto.DossierPatientDto;
import ma.fsr.ms.dossierservice.service.DossierService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api/v1/dossiers")
@AllArgsConstructor
public class DossierController {

    private final DossierService dossierService;

    @GetMapping("/patient/{patientId}")
    public DossierPatientDto getDossierByPatientId(@PathVariable Long patientId) throws Exception {
        return dossierService.getDossierByPatientId(patientId);
    }
}

