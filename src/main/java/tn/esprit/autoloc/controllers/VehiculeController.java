package tn.esprit.autoloc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @PostMapping
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return vehiculeService.create(vehicule);
    }

    @GetMapping("/{id}")
    public Vehicule findById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @GetMapping
    public List<Vehicule> findAll() {
        return vehiculeService.findAll();
    }

    @PutMapping
    public Vehicule update(@RequestBody Vehicule vehicule) {
        return vehiculeService.update(vehicule);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vehiculeService.delete(id);
    }

    // Scenario 3 : PUT /vehicules/{idVehicule}/affecter-equipement/{idEquipement}
    @PutMapping("/{idVehicule}/affecter-equipement/{idEquipement}")
    public Vehicule affecterEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        return vehiculeService.affecterEquipement(idVehicule, idEquipement);
    }

    // Scenario 3 : PUT /vehicules/{idVehicule}/desaffecter-equipement/{idEquipement}
    @PutMapping("/{idVehicule}/desaffecter-equipement/{idEquipement}")
    public Vehicule desaffecterEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        return vehiculeService.desaffecterEquipement(idVehicule, idEquipement);
    }
}
