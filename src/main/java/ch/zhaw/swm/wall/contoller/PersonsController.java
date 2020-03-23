package ch.zhaw.swm.wall.contoller;

import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.services.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonsController extends BasicController {
    private final PersonService personService;

    @Autowired
    public PersonsController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/persons")
    public Person newPerson(@RequestBody Person newPerson) {
        return personService.createPerson(newPerson);
    }

    @GetMapping("/persons")
    public List<Person> all() {
        return personService.findAll();
    }

    @PutMapping("/persons")
    public Person updatePerson(@RequestBody Person existingPerson) {
        return personService.editPerson(existingPerson);
    }

    @DeleteMapping("/persons/{id}")
    void delete(@PathVariable String id) {
        this.personService.deletePerson(id);
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> one(@PathVariable String id) {
        return handleSingleResourceResponse(personService.findById(id));
    }

    @GetMapping("persons/{id}/friends")
    public List<Person> getFriends(@PathVariable String id) {
        return personService.findFriends(id);
    }


}
