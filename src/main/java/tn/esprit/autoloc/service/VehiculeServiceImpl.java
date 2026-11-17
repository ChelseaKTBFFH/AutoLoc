package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Service
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IEquipementRepository equipementRepository;

    // Injection par constructeur (recommandee)
    public VehiculeServiceImpl(IVehiculeRepository vehiculeRepository, IEquipementRepository equipementRepository) {
        this.vehiculeRepository = vehiculeRepository;
        this.equipementRepository = equipementRepository;
    }

    @Override
    public Vehicule create(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule introuvable : id = " + id));
    }

    @Override
    public Vehicule update(Vehicule vehicule) {
        if (!vehiculeRepository.existsById(vehicule.getIdVehicule())) {
            throw new EntityNotFoundException("Vehicule introuvable : id = " + vehicule.getIdVehicule());
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public void delete(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new EntityNotFoundException("Vehicule introuvable : id = " + id);
        }
        vehiculeRepository.deleteById(id);
    }

    // Vehicule est le cote proprietaire du @ManyToMany (@JoinTable vehicule_equipement) :
    // modifier vehicule.equipements suffit pour inserer / supprimer la ligne de jointure.
    @Override
    @Transactional
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findById(idVehicule);
        Equipement equipement = findEquipement(idEquipement);
        if (!vehicule.getEquipements().contains(equipement)) {
            vehicule.getEquipements().add(equipement);
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findById(idVehicule);
        Equipement equipement = findEquipement(idEquipement);
        vehicule.getEquipements().remove(equipement);
        return vehiculeRepository.save(vehicule);
    }

    private Equipement findEquipement(Long idEquipement) {
        return equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new EntityNotFoundException("Equipement introuvable : id = " + idEquipement));
    }
}
