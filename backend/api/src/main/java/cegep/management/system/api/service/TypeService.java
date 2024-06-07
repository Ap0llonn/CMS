package cegep.management.system.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Type;
import cegep.management.system.api.repo.TypeRepository;
import jakarta.transaction.Transactional;

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> getAllTypes() {
        return this.typeRepository.findAll();
    }

    public Optional<Type> getTypeById(Long id) {
        return this.typeRepository.findById(id);
    }

    public Optional<Type> getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    public Type createType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    public Type updateType(Long id, Type typeDetails) {
        return typeRepository.findById(id)
                .map(type -> {
                    type.setName(typeDetails.getName());
                    return typeRepository.save(type);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Type not found with id " + id));
    }

    public void deleteType(Long id) {
        if (typeRepository.existsById(id)) {
            typeRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Type not found with id " + id);
        }
    }
}
