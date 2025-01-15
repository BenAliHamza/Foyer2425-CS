
package tn.esprit.spring.Services.Etudiant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.Services.Email.EmailService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EtudiantService implements IEtudiantService {

    private final EtudiantRepository etudiantRepository;

    @Override
    public Etudiant addOrUpdate(Etudiant etudiant) {
        // Validation des entrées
        if (etudiant.getNomEt() == null || etudiant.getPrenomEt() == null) {
            throw new IllegalArgumentException("Le nom et le prénom de l'étudiant sont obligatoires.");
        }

        // Sauvegarde de l'étudiant
        Etudiant savedEtudiant = etudiantRepository.save(etudiant);
        log.info("Étudiant sauvegardé : {}", savedEtudiant);

        // Envoi d'une notification par e-mail
        try {
            String subject = "Nouvel étudiant ajouté";
            String text = "L'étudiant " + savedEtudiant.getNomEt() + " " + savedEtudiant.getPrenomEt() + " a été ajouté avec succès.";
            log.info("E-mail envoyé avec succès.");
        } catch (Exception e) {
            log.error("Échec de l'envoi de l'e-mail : {}", e.getMessage());
        }

        return savedEtudiant;
    }

    @Override
    public List<Etudiant> findAll() {
        return etudiantRepository.findAll();
    }

    @Override
    public Etudiant findById(long id) {
        return etudiantRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(long id) {
        etudiantRepository.deleteById(id);
    }

    @Override
    public void delete(Etudiant etudiant) {
        etudiantRepository.delete(etudiant);
    }

    @Override
    public List<Etudiant> selectJPQL(String nom) {
        return etudiantRepository.selectJPQL(nom);
    }
}