package com.udacity.jdnd.course3.critter.pet;

import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService service;

    public PetController(PetService service) {
        this.service = service;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return PetDTO.fromEntity(this.service.save(petDTO.toEntity()));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) throws EntityNotFoundException {
        Pet pet = this.service.find(petId).
                orElseThrow(() -> new EntityNotFoundException(String.format("A pet with id %d not found", petId)));
        return PetDTO.fromEntity(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return this.service.getAll().stream().map(PetDTO::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return this.service.getPetsByOwner(ownerId).stream().map(PetDTO::fromEntity).collect(Collectors.toList());
    }

}
