package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Employe;

import java.util.List;

public interface IEmployeService {

    Employe create(Employe employe);

    List<Employe> findAll();

    Employe findById(Long id);

    Employe update(Employe employe);

    void delete(Long id);

    // Mutation : retirer l'employe de son agence actuelle
    Employe desaffecterEmploye(Long idEmploye);

    // Mutation : affecter l'employe a une nouvelle agence
    Employe affecterEmploye(Long idEmploye, Long idAgence);
}
