package cegep.management.system.api.service;

import org.springframework.stereotype.Service;

import cegep.management.system.api.dto.AddressDTO;
import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Address;
import cegep.management.system.api.model.Person;
import cegep.management.system.api.repo.AddressRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final PersonService personService;

    public AddressService(AddressRepository addressRepository, PersonService personService) {
        this.addressRepository = addressRepository;
        this.personService = personService;
    }

    public List<Address> getAllAddresses() {
        return this.addressRepository.findAll();
    }

    public Optional<Address> getAddressById(Long id) {
        return this.addressRepository.findById(id);
    }

    public List<Address> getAddressesByUser(Person user) {
        return this.addressRepository.findByUser(user);
    }

    public Address createAddress(AddressDTO addressDTO) {
        Person person = personService.getUserById(addressDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Can't find a Person with the id: " + addressDTO.getUserId()));
        Address address = new Address(person, addressDTO.getAddress(), addressDTO.getCity(), addressDTO.getProvince(),
                addressDTO.getPostalCode());
        return this.addressRepository.save(address);
    }

    @Transactional
    public Address updateAddress(Long id, Address addressDetails) {
        return this.addressRepository.findById(id)
                .map(address -> {
                    address.setAddress(addressDetails.getAddress());
                    address.setCity(addressDetails.getCity());
                    address.setProvince(addressDetails.getProvince());
                    address.setPostalCode(addressDetails.getPostalCode());
                    return this.addressRepository.save(address);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + id));
    }

    public void deleteAddress(Long id) {
        if (this.addressRepository.existsById(id)) {
            this.addressRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Address not found with id " + id);
        }
    }
}
