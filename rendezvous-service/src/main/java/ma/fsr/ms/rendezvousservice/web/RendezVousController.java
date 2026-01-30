package ma.fsr.ms.rendezvousservice.web;

import lombok.AllArgsConstructor;
import ma.fsr.ms.rendezvousservice.dto.RendezVousDto;
import ma.fsr.ms.rendezvousservice.model.RendezVous;
import ma.fsr.ms.rendezvousservice.model.StatutRdv;
import ma.fsr.ms.rendezvousservice.service.RendezVousService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/rendezvous")
@AllArgsConstructor
public class RendezVousController {

    private final RendezVousService rendezVousService;

    @GetMapping
    public List<RendezVous> getAllRendezVous() {
        return rendezVousService.getAllRendezVous();
    }

    @GetMapping("/{id}")
    public RendezVous getRendezVousById(@PathVariable Long id) throws Exception {
        return rendezVousService.getRendezVousById(id);
    }

    @GetMapping("/patient/{id}")
    public List<RendezVous> getRendezVousByPatientId(@PathVariable Long id) throws Exception {
        return rendezVousService.getRendezVousByPatientId(id);
    }

    @GetMapping("/medecin/{id}")
    public List<RendezVous> getRendezVousByMedecinId(@PathVariable Long id) throws Exception {
        return rendezVousService.getRendezVousByMedecinId(id);
    }

    @PostMapping
    public RendezVous createRendezVous(@RequestBody RendezVousDto rendezVousDto) throws Exception {
        return rendezVousService.createRendezVous(rendezVousDto);
    }

    @PutMapping("/{id}")
    public RendezVous updateRendezVous(@PathVariable Long id, @RequestBody RendezVousDto rendezVousDto) throws Exception {
        return rendezVousService.updateRendezVous(id, rendezVousDto);
    }

    @PatchMapping("/{id}/statut")
    public RendezVous updateRendezVousStatus(@PathVariable Long id, @RequestBody StatutRdv statut) throws Exception {
        return rendezVousService.updateRendezVousStatus(id, statut);
    }

    @DeleteMapping("/{id}")
    public void deleteRendezVous(@PathVariable Long id) {
        rendezVousService.deleteRendezVous(id);
    }
}
