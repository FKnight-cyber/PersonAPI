package com.attornatuschallenge.personapi.services;

import com.attornatuschallenge.personapi.entities.Address;
import com.attornatuschallenge.personapi.entities.Person;
import com.attornatuschallenge.personapi.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    @Autowired
    private AddressService addressService;

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person findById(Long id) {
        Optional<Person> person = repository.findById(id);
        return person.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There's no person registered with this id!"));
    };

    public List<Person> findByName(String name) {
        List<Person> people = repository.findByName(name);
        return people;
    };

    public Person mainAddressInfo(Long id) {
        Person person = findById(id);
        Address address = addressService.findById(person.getMainAddressId());

        List<Address> mainAddress = new ArrayList<>();
        mainAddress.add(address);

        Person result = new Person(person.getId(), person.getName(), person.getBirthDate(), mainAddress);

        return result;
    }

    public Person insert(Person person) {
        return repository.save(person);
    }

    public Person update(Long id, Person person) {
        try {
            Person entity = repository.getReferenceById(id);
            updateData(entity, person);
            return repository.save(entity);
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There's no person registered with this id!");
        }
    }

    private void updateData(Person entity, Person person) {
        entity.setName(person.getName());
    }
}
