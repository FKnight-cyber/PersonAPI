package com.attornatuschallenge.personapi.controllers;

import com.attornatuschallenge.personapi.entities.Person;
import com.attornatuschallenge.personapi.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/people")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping
    public ResponseEntity<List<Person>> getRegisteredPeople() {

        List<Person> list = service.findAll();

        return ResponseEntity.ok().body(list);
    };

    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        Person person = service.findById(id);
        return ResponseEntity.ok().body(person);
    }

    @GetMapping(value = "/name")
    public ResponseEntity<List<Person>> findByName(@RequestParam String name) {
       return new ResponseEntity<List<Person>>(service.findByName(name), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Person> insert(@RequestBody Person person) {
        service.insert(person);
        return new ResponseEntity("Person successfully registered!", HttpStatus.CREATED);
    }
}
