package cegep.management.system.api.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Person;
import cegep.management.system.api.repo.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonService {

    private final PersonRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final String PHONE_NUMBER_PATTERN = "(\\d{3})(\\d{3})(\\d{4})";

    public PersonService(PersonRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<Person> getUserById(Long id) {
        return this.userRepository.findById(id);
    }

    public Optional<Person> getUserByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Optional<Person> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Person createUser(Person user) {
        String formattedPhoneNumber = formatPhoneNumber(user.getPhone());
        user.setPhone(formattedPhoneNumber);

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with the same email already exists");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    private String formatPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("[^\\d]", "");

        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) {
            return "(" + matcher.group(1) + ")-" + matcher.group(2) + "-" + matcher.group(3);
        } else {
            return phoneNumber;
        }
    }

    @Transactional
    public Person updateUser(Long id, Person userDetails) {
        return this.userRepository.findById(id)
                .map(user -> {
                    String hashedPassword = passwordEncoder.encode(userDetails.getPassword());
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setEmail(userDetails.getEmail());
                    user.setPhone(userDetails.getPhone());
                    user.setPassword(hashedPassword);
                    user.setDateOfBirth(userDetails.getDateOfBirth());
                    return this.userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public void deleteUser(Long id) {
        if (this.userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
    }

    public boolean authenticateUser(String email, String plainTextPassword) {
        Person user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
        return passwordEncoder.matches(plainTextPassword, user.getPassword());
    }
}
