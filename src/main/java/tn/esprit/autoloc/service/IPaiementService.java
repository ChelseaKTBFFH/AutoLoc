package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Paiement;

import java.util.List;

public interface IPaiementService {

    Paiement create(Paiement paiement);

    List<Paiement> findAll();

    Paiement findById(Long id);

    Paiement update(Paiement paiement);

    void delete(Long id);
}
