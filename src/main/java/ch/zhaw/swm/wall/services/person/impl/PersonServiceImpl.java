package ch.zhaw.swm.wall.services.person.impl;

import ch.zhaw.swm.wall.contoller.exception.NotFoundException;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.repository.PersonRepository;
import ch.zhaw.swm.wall.services.person.PersonService;

import java.util.List;
import java.util.Optional;

public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Person> findById(String id) {
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
            new NotFoundException("person", person.getId()));
    }

    @Override
    public void deletePerson(String personId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isPresent()) {
            personRepository.delete(personOptional.get());
        } else {
            throw new NotFoundException("person", personId);
        }
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> findFriends(String personId) {
        return findById(personId).
            map(this::findFriends).orElseThrow(() ->
            new NotFoundException("person", personId));
    }

    @Override
    public List<Person> findFriends(Person person) {
        return personRepository.findByIdIn(person.getFriends());
    }

    public Person editPerson(Person editedPerson, Person storedPerson) {
        //hier könnte man z.B Felder welche wir nur intern führen usw restored werden aus storedPerson..
        return personRepository.save(editedPerson);
    }
}
