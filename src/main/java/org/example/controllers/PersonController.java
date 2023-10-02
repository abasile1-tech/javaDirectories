package org.example.controllers;

import org.example.models.Person;
import org.example.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @GetMapping(value = "/persons")
    public ResponseEntity<List<Person>> getAllPersons() {
        return new ResponseEntity<List<Person>>(personRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/persons/{id}")
    public ResponseEntity<Optional<Person>> getPerson(@PathVariable Long id) {
        return new ResponseEntity<Optional<Person>>(personRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/persons")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return new ResponseEntity<>(personRepository.save(person), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/persons/{id}")
    public void deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
    }

    @PutMapping(value = "/persons/{id}")
    public Person replacePerson(@PathVariable Long id, @RequestBody Person newPerson) {
        return personRepository.findById(id).map((person) -> {
            person.setName(newPerson.getName());
            person.setFolders(newPerson.getFolders());
            return personRepository.save(person);
        }).orElseGet(() -> {
            newPerson.setId(id);
            return personRepository.save(newPerson);
        });
    }
}