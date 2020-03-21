package ch.zhaw.swm.wall.contoller;

import ch.zhaw.swm.wall.contoller.exception.NotFoundException;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
public class PersonsController {
    private final PersonRepository repository;

    @Autowired
    public PersonsController(PersonRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/persons")
    Person newPerson(@RequestBody Person newPerson) {
        return repository.save(newPerson);
    }

    @GetMapping("/persons")
    List<Person> all() {
        return repository.findAll();
    }

    @GetMapping("/persons/{id}")
    Person one(@PathVariable BigInteger id) {

        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("person", id));
    }

    @DeleteMapping("/persons/{id}")
    void delete(@PathVariable BigInteger id) {
        Person personToDelete = this.one(id);
        this.repository.delete(personToDelete);
    }
}
