package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;

import java.util.List;

public interface IAgenceService {

    Agence create(Agence agence);

    List<Agence> findAll();

    Agence findById(Long id);

    Agence update(Agence agence);

    void delete(Long id);
}
