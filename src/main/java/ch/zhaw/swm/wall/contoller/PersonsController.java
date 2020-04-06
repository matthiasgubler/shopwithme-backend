package ch.zhaw.swm.wall.contoller;

import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.person.RelationshipStatus;
import ch.zhaw.swm.wall.services.person.PersonService;
import ch.zhaw.swm.wall.services.person.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonsController extends BasicController {

    private final PersonService personService;
    private final RelationshipService relationshipService;

    private static final String URI_FRAGMENT = "/persons";
    private static final String ID = "/{id}";

    @Autowired
    public PersonsController(PersonService personService, RelationshipService relationshipService) {
        this.personService = personService;
        this.relationshipService = relationshipService;
    }

    @GetMapping(URI_FRAGMENT)
    public List<Person> all() {
        return personService.findAll();
    }

    @PostMapping(URI_FRAGMENT)
    public Person newPerson(@RequestBody Person newPerson) {
        return personService.createPerson(newPerson);
    }

    @PutMapping(URI_FRAGMENT)
    public Person updatePerson(@RequestBody Person existingPerson) {
        return personService.editPerson(existingPerson);
    }

    @DeleteMapping(URI_FRAGMENT + ID)
    public void delete(@PathVariable String id) {
        this.personService.deletePerson(id);
    }

    @GetMapping(URI_FRAGMENT + ID)
    public ResponseEntity<Person> one(@PathVariable String id) {
        return handleSingleResourceResponse(personService.findById(id));
    }

    @GetMapping(URI_FRAGMENT + ID + "/requested")
    public List<Relationship> findByRequestingPersonId(@PathVariable String id, @PathVariable(required = false) RelationshipStatus status) {
        if (status != null) {
            return relationshipService.findByRequestingPersonIdAndStatus(id, status);
        } else {
            return relationshipService.findByRequestingPersonId(id);
        }
    }

    @GetMapping(URI_FRAGMENT + ID + "/requests")
    public List<Relationship> findByRequestedPersonId(@PathVariable String id, @PathVariable(required = false) RelationshipStatus status) {
        if (status != null) {
            return relationshipService.findByRequestedPersonIdAndStatus(id, status);
        } else {
            return relationshipService.findByRequestedPersonId(id);
        }
    }

}
