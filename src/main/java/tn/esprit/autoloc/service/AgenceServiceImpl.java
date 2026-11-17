package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final IVehiculeRepository vehiculeRepository;

    // Injection par constructeur (recommandee)
    public AgenceServiceImpl(IAgenceRepository agenceRepository, IVehiculeRepository vehiculeRepository) {
        this.agenceRepository = agenceRepository;
        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    public Agence create(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence findById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable : id = " + id));
    }

    @Override
    public Agence update(Agence agence) {
        if (!agenceRepository.existsById(agence.getIdAgence())) {
            throw new EntityNotFoundException("Agence introuvable : id = " + agence.getIdAgence());
        }
        return agenceRepository.save(agence);
    }

    @Override
    public void delete(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new EntityNotFoundException("Agence introuvable : id = " + id);
        }
        agenceRepository.deleteById(id);
    }

    // Vehicule est le cote proprietaire (@ManyToOne + id_agence) et il n'y a pas de cascade :
    // on enregistre d'abord l'agence, puis on affecte l'agence a chaque vehicule avant de les enregistrer.
    @Override
    @Transactional
    public Agence ajouterAgenceEtAffecterVehicules(Agence agence) {
        List<Vehicule> vehicules = agence.getVehicules() != null ? agence.getVehicules() : new ArrayList<>();
        agence.setVehicules(new ArrayList<>());
        Agence agenceEnregistree = agenceRepository.save(agence);

        for (Vehicule vehicule : vehicules) {
            vehicule.setAgence(agenceEnregistree);
        }
        agenceEnregistree.setVehicules(vehiculeRepository.saveAll(vehicules));
        return agenceEnregistree;
    }
}
