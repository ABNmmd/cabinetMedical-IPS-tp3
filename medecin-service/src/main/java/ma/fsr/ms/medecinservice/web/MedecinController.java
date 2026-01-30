package ma.fsr.ms.medecinservice.web;

import lombok.AllArgsConstructor;
import ma.fsr.ms.medecinservice.model.Medecin;
import ma.fsr.ms.medecinservice.dto.MedecinUpdateDto;
import ma.fsr.ms.medecinservice.service.MedecinService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/medecins")
@AllArgsConstructor
public class MedecinController {

    private final MedecinService medecinService;

    @GetMapping
    public List<Medecin> getAllMedecins() {
        return medecinService.getAllMedecins();
    }

    @GetMapping("/{id}")
    public Medecin getMedecinById(@PathVariable Long id) throws Exception {
        return medecinService.getMedecinById(id);
    }

    @PostMapping
    public Medecin createMedecin(@RequestBody Medecin medecin) throws Exception {
        return medecinService.createMedecin(medecin);
    }

    @PutMapping("/{id}")
    public Medecin updateMedecin(@PathVariable Long id, @RequestBody MedecinUpdateDto medecinDto) throws Exception {
        return medecinService.updateMedecin(id, medecinDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMedecin(@PathVariable Long id) {
        medecinService.deleteMedecin(id);
    }
}
