package ch.zhaw.swm.wall.services.person.impl;

import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.repository.PersonRepository;
import ch.zhaw.swm.wall.services.person.PersonService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Person> findById(BigInteger id) {
        return personRepository.findById(id);
    }

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person editPerson(Person person) {
        return findById(person.getId()).
            map(storedPerson -> editPerson(person, storedPerson)).orElseThrow(() ->
            new IllegalArgumentException("person with id: " + person.getId() + " not found"));
    }

    @Override
    public List<Person> getFriends(BigInteger personId) {
        return findById(personId).
            map(storedPerson -> getFriends(storedPerson)).orElseThrow(() ->
            new IllegalArgumentException("person with id: " + personId + " not found"));
    }

    @Override
    public List<Person> getFriends(Person person) {
        return personRepository.findByIdIn(person.getFriends());
    }

    public Person editPerson(Person editedPerson, Person storedPerson) {
        //hier könnte man z.B Felder welche wir nur intern führen usw restored werden aus storedPerson..
        return personRepository.save(editedPerson);
    }
}
