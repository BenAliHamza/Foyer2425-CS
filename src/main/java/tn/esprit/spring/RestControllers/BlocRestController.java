package tn.esprit.spring.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.Services.Bloc.IBlocService;

import java.util.List;

@RestController
@RequestMapping("bloc")
@CrossOrigin(origins ="*")
@AllArgsConstructor
public class BlocRestController {
    IBlocService service;

    @PostMapping("addOrUpdate")
    Bloc addOrUpdate(@RequestBody Bloc b) {
        return service.addOrUpdate(b);
    }

    @GetMapping("findAll")
    List<Bloc> findAll() {
        return service.findAll();
    }

    @GetMapping("findById")
    Bloc findById(@RequestParam long id) {
        return service.findById(id);
    }

    @DeleteMapping("delete")
    void delete(@RequestBody Bloc b) {
        service.delete(b);
    }

    @DeleteMapping("deleteById")
    void deleteById(@RequestParam long id) {
        service.deleteById(id);
    }

    @PutMapping("affecterChambresABloc")
    Bloc affecterChambresABloc(@RequestBody List<Long> numChambre, @RequestParam String nomBloc) {
        return service.affecterChambresABloc(numChambre, nomBloc);
    }
    // ...............?nomFoyer=....&nomBloc=....
    @PutMapping("affecterBlocAFoyer")
    Bloc affecterBlocAFoyer(@RequestParam String nomBloc, @RequestParam String nomFoyer) {
        return service.affecterBlocAFoyer(nomBloc, nomFoyer);
    }

    // .............../Foyer des jasmins/Bloc G
    @PutMapping("affecterBlocAFoyer2/{nomFoyer}/{nomBloc}")
    Bloc affecterBlocAFoyer2(@PathVariable String nomBloc, @PathVariable String nomFoyer) {
        return service.affecterBlocAFoyer(nomBloc, nomFoyer);
    }

    @PostMapping("ajouterBlocEtSesChambres")
    Bloc ajouterBlocEtSesChambres(@RequestBody Bloc b) {
        return service.ajouterBlocEtSesChambres(b);
    }

    @PostMapping("ajouterBlocEtAffecterAFoyer/{nomF}")
    Bloc ajouterBlocEtAffecterAFoyer(@RequestBody Bloc b,@PathVariable String nomF) {
        return service.ajouterBlocEtAffecterAFoyer(b,nomF);
    }
}
