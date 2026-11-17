package tn.esprit.autoloc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agences")
public class AgenceController {

    private final IAgenceService agenceService;

    @PostMapping
    public Agence create(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    @GetMapping("/{id}")
    public Agence findById(@PathVariable Long id) {
        return agenceService.findById(id);
    }

    @GetMapping
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    @PutMapping
    public Agence update(@RequestBody Agence agence) {
        return agenceService.update(agence);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        agenceService.delete(id);
    }

    // Scenario 2 : POST /agences/ajouter-avec-vehicules (agence + liste de vehicules dans le body)
    @PostMapping("/ajouter-avec-vehicules")
    public Agence ajouterAgenceEtAffecterVehicules(@RequestBody Agence agence) {
        return agenceService.ajouterAgenceEtAffecterVehicules(agence);
    }
}
