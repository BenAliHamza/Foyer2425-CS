package tn.esprit.spring.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.TypeChambre;

import java.util.List;

public interface BlocRepository extends JpaRepository<Bloc, Long> {

    @Query("select b from Bloc b where b.nomBloc=?1")
    Bloc selectByNomBJPQL1(String nom);

    @Query("select b from Bloc b where b.nomBloc=:nom")
    Bloc selectByNomBJPQL2(@Param("nom") String nom);

    @Query(value = "SELECT * FROM t_bloc WHERE nom_bloc=?1  ", nativeQuery = true)
    Bloc selectByNomBSQL1(String nom);

    @Query(value = "SELECT * FROM t_bloc WHERE nom_bloc=:nom  ", nativeQuery = true)
    Bloc selectByNomBSQL2(@Param("nom") String nom);

    @Modifying
    @Query("update Bloc b set b.nomBloc=?1 where b.capaciteBloc<10")
    void updateBlocJPQL(String nom);

    @Modifying
    @Query(value = "update t_bloc set nom_bloc=?1 where capacite_bloc<10", nativeQuery = true)
    void updateBlocSQL(String nom);

    // Récupérer les blocs qui ont des chambres avec un typeChambre donné
    // Bloc (Child) 1--* Chambre (Parent-FK)
    @Query("select b from Bloc b join Chambre c on c.bloc.idBloc=b.idBloc  where c.typeC=?1")
    List<Bloc> selectBlocsByTypeChambreJPQL(TypeChambre typeChambre);


    // 1- Recherche par nomBloc
    List<Bloc> getByNomBloc(String nom);

    Bloc findByNomBloc(String nom);

    // 2- Recherche par capaciteBloc
    List<Bloc> getByCapaciteBloc(long cap);

    Bloc findByCapaciteBloc(long cap);

    // 3- Recherche par nomBloc et capaciteBloc
    List<Bloc> getByNomBlocAndCapaciteBloc(String nomB, long cap);

    // 4- Recherche par nomBloc en ignorant la casse
    List<Bloc> findByNomBlocIgnoreCase(String nomB);

    // 5- Recherche par capaciteBloc supérieure à une valeur donnée
    List<Bloc> findByCapaciteBlocGreaterThan(long cap);

    // 6- Recherche par nomBloc contenant une sous-chaîne
    List<Bloc> findByNomBlocLike(String nom);

    // 7- Tri par nomBloc par ordre alphabétique


    // 8- Recherche par nomBloc ou capaciteBloc
    List<Bloc> getByNomBlocOrCapaciteBloc(String nomB, long cap);

    // 9- Recherche des blocs d'un foyer spécifique
    List<Bloc> findByFoyerIdFoyer(long idFoyer);

    @Query("select b from Bloc b where b.foyer.idFoyer= ?1")
    List<Bloc> findByFoyerIdFoyerJPQL(long idFoyer);

    // 10- Recherche des blocs pour un foyer d'une université donnée
    List<Bloc> findByFoyerUniversiteIdUniversite(long idUniversite);

    @Query("select b from Bloc b where b.foyer.universite.idUniversite=?1")
    List<Bloc> findByFoyerUniversiteIdUniversiteJPQL(long idUniversite);






    //9- Recherche des blocs d'un foyer spécifique
    @Query("select b from Bloc b where b.foyer.idFoyer=?1 ")
    List<Bloc> req9JPQL(long idF);
    @Query(value = "SELECT b.* FROM t_bloc b JOIN t_foyer " +
            "f ON f.id_foyer= b.foyer_id_foyer  WHERE f.id_foyer=?1", nativeQuery = true)
    List<Bloc> req9SQL(long idF);

    // 10- Recherche des blocs pour un foyer d'une université donnée
    @Query("select b from Bloc b where b.foyer.universite.idUniversite=?1 ")
    List<Bloc> req10JPQL(long idU);
    @Query(value = "SELECT * FROM t_bloc b JOIN t_foyer f " +
            "ON f.id_foyer= b.foyer_id_foyer JOIN t_universite u " +
            "ON u.foyer_id_foyer=f.id_foyer WHERE u.id_universite=?1;"
            , nativeQuery = true)
    List<Bloc> req10SQL(long idU);


















}
