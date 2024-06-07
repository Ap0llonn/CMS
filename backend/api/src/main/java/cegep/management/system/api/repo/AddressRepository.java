package cegep.management.system.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cegep.management.system.api.model.Address;
import cegep.management.system.api.model.Person;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(Person user);
}
