package cegep.management.system.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cegep.management.system.api.dto.AddressDTO;
import cegep.management.system.api.model.Address;
import cegep.management.system.api.service.AddressService;
import cegep.management.system.api.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Address address = addressService.getAddressById(id)
                .orElseThrow(() -> new RuntimeException("Can't find address with the id: " + id));
        return ResponseEntity.ok(address);
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Address>> getAddressByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(
                addressService.getAddressesByUser(personService.getUserById(userId)
                        .orElseThrow(() -> new RuntimeException("Can't find user with id: " + userId))));
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressDTO addressDTO) {
        Address newAddress = addressService.createAddress(addressDTO);
        return ResponseEntity.status(201).body(newAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        Address updatedAddress = addressService.updateAddress(id, address);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}