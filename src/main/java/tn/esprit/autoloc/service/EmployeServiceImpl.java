package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEmployeRepository;

import java.util.List;

@Service
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;

    // Injection par constructeur (recommandee)
    public EmployeServiceImpl(IEmployeRepository employeRepository, IAgenceRepository agenceRepository) {
        this.employeRepository = employeRepository;
        this.agenceRepository = agenceRepository;
    }

    @Override
    public Employe create(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employe introuvable : id = " + id));
    }

    @Override
    public Employe update(Employe employe) {
        if (!employeRepository.existsById(employe.getIdEmploye())) {
            throw new EntityNotFoundException("Employe introuvable : id = " + employe.getIdEmploye());
        }
        return employeRepository.save(employe);
    }

    @Override
    public void delete(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employe introuvable : id = " + id);
        }
        employeRepository.deleteById(id);
    }

    // Employe est le cote proprietaire (@ManyToOne + @JoinColumn id_agence) :
    // il suffit de modifier employe.agence pour mettre a jour la cle etrangere.
    @Override
    @Transactional
    public Employe desaffecterEmploye(Long idEmploye) {
        Employe employe = findById(idEmploye);
        employe.setAgence(null);
        return employeRepository.save(employe);
    }

    @Override
    @Transactional
    public Employe affecterEmploye(Long idEmploye, Long idAgence) {
        Employe employe = findById(idEmploye);
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable : id = " + idAgence));
        employe.setAgence(agence);
        return employeRepository.save(employe);
    }
}
