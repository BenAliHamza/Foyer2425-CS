package tn.esprit.spring.Services.Etudiant;

import tn.esprit.spring.DAO.Entities.Etudiant;
import java.util.List;

/**
 * Service pour gérer les opérations liées aux étudiants.
 */
public interface IEtudiantService {
    /**
     * Ajoute ou met à jour un étudiant.
     *
     * @param etudiant L'étudiant à ajouter ou mettre à jour.
     * @return L'étudiant sauvegardé.
     */
    Etudiant addOrUpdate(Etudiant etudiant);

    /**
     * Récupère tous les étudiants.
     *
     * @return Une liste de tous les étudiants.
     */
    List<Etudiant> findAll();

    /**
     * Récupère un étudiant par son ID.
     *
     * @param id L'ID de l'étudiant.
     * @return L'étudiant trouvé, ou null si non trouvé.
     */
    Etudiant findById(long id);

    /**
     * Supprime un étudiant par son ID.
     *
     * @param id L'ID de l'étudiant à supprimer.
     */
    void deleteById(long id);

    /**
     * Supprime un étudiant.
     *
     * @param etudiant L'étudiant à supprimer.
     */
    void delete(Etudiant etudiant);

    /**
     * Récupère les étudiants par nom en utilisant une requête JPQL.
     *
     * @param nom Le nom de l'étudiant.
     * @return Une liste d'étudiants correspondant au nom.
     */
    List<Etudiant> selectJPQL(String nom);
}