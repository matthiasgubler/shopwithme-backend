package ch.zhaw.swm.wall.controller;

import ch.zhaw.swm.wall.context.Context;
import ch.zhaw.swm.wall.context.LoggedInUser;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.person.RelationshipStatus;
import ch.zhaw.swm.wall.model.topic.Topic;
import ch.zhaw.swm.wall.services.person.PersonService;
import ch.zhaw.swm.wall.services.person.RelationshipService;
import ch.zhaw.swm.wall.services.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonsController extends BasicController {

    private final PersonService personService;
    private final RelationshipService relationshipService;
    private final TopicService topicService;

    private static final String URI_FRAGMENT = "/persons";
    private static final String ID = "/{id}";
    private static final String SELF = "/self";

    @Autowired
    public PersonsController(PersonService personService, RelationshipService relationshipService, TopicService topicService) {
        this.personService = personService;
        this.relationshipService = relationshipService;
        this.topicService = topicService;
    }

    @GetMapping(URI_FRAGMENT)
    public ResponseEntity<List<Person>> all(@RequestParam(required = false) String email) {
        if (StringUtils.isEmpty(email)) {
            return new ResponseEntity<>(personService.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(personService.findByEmail(email).map(Collections::singletonList).orElseGet(Collections::emptyList), HttpStatus.OK);
        }
    }

    @GetMapping(URI_FRAGMENT + SELF)
    public ResponseEntity<Person> whoami() {
        LoggedInUser loggedInUser = Context.getCurrentContext().getLoggedInUser();
        String email = loggedInUser.getEmail();
        if (email.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Person> optionalPerson = personService.findByEmail(email);
        if (optionalPerson.isPresent()) {
            return new ResponseEntity<>(optionalPerson.get(), HttpStatus.OK);
        }

        Person newPerson = new Person(loggedInUser.getName(), email, loggedInUser.getPicture());
        return new ResponseEntity<>(personService.createPerson(newPerson), HttpStatus.OK);
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

    @GetMapping(URI_FRAGMENT + ID + "/topics")
    public List<Topic> findTopicsByPersonId(@PathVariable String id) {
        return topicService.findByPersonId(id);
    }
}
