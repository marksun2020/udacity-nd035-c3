package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class PetService {
    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public Pet save(Pet pet) {
        return this.repository.save(pet);
    }

    public Set<Pet> getPetsByOwner(long ownerId) {
        return this.repository.getPetsByOwnerId(ownerId);
    }
    public List<Pet> getAll() {
        return this.repository.findAll();
    }
    public Optional<Pet> find(long petId) {
        return this.repository.findById(petId);
    }
}
