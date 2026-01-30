package ma.fsr.ms.patientservice.service;

import lombok.AllArgsConstructor;
import ma.fsr.ms.patientservice.dto.PatientUpdateDto;
import ma.fsr.ms.patientservice.model.Patient;
import ma.fsr.ms.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepository repo;

    public Patient createPatient(Patient patient) throws Exception{
        // le nom est obligatoire
        if(patient.getNom()==null || patient.getNom().isEmpty()) {
            throw new Exception("Le nom du patient est obligatoire");
        }

        // le telephone est obligatoire
        if(patient.getTelephone()==null || patient.getTelephone().isEmpty()) {
            throw new Exception("Le telephone du patient est obligatoire");
        }

        // la date de naissance ne peut pas etre dans le futur
        if(patient.getDateNaissance().isAfter(LocalDate.now())) {
            throw new Exception("La date de naissance ne peut pas etre future");
        }

        return repo.save(patient);
    }

    public List<Patient> getAllPatients() {
        return repo.findAll();
    }

    public Patient getPatientById(Long id) throws Exception {
        // patient introuvable
        Optional<Patient> patient = repo.findById(id);
        if (patient.isEmpty()) {
            throw new Exception("Patient introuvable : id ="+id);
        }

        return patient.get();
    }

    public Patient updatePatient(Long id, PatientUpdateDto updatedPatientDto) throws Exception {
        Patient existingPatient = repo.findById(id).orElse(null);
        if (existingPatient == null) {
            throw new Exception("Patient introuvable : id ="+id);
        }

        // mettre a jour les champs

        if (updatedPatientDto.getNom() != null && !updatedPatientDto.getNom().equals(existingPatient.getNom())) {
            existingPatient.setNom(updatedPatientDto.getNom());
        }

        if (updatedPatientDto.getDateNaissance() != null && !updatedPatientDto.getDateNaissance().equals(existingPatient.getDateNaissance())) {
            if (updatedPatientDto.getDateNaissance().isAfter(LocalDate.now())) {
                throw new Exception("La date de naissance ne peut pas etre future");
            }
            existingPatient.setDateNaissance(updatedPatientDto.getDateNaissance());
        }

        if (updatedPatientDto.getTelephone() != null && !updatedPatientDto.getTelephone().equals(existingPatient.getTelephone())) {
            existingPatient.setTelephone(updatedPatientDto.getTelephone());
        }

        if (updatedPatientDto.getGenre() != null && !updatedPatientDto.getGenre().equals(existingPatient.getGenre())) {
            existingPatient.setGenre(updatedPatientDto.getGenre());
        }

        return repo.save(existingPatient);
    }

    public void deletePatient(Long id) {
        Patient patient = repo.findById(id).orElse(null);
        if (patient != null) {
            repo.delete(patient);
        }
    }
}
