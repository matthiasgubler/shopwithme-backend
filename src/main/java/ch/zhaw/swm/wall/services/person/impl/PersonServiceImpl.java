package ch.zhaw.swm.wall.services.person.impl;

import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.repository.PersonRepository;
import ch.zhaw.swm.wall.services.EntityIdHandler;
import ch.zhaw.swm.wall.services.person.PersonService;

import java.util.List;
import java.util.Optional;

public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final EntityIdHandler entityIdHandler = new EntityIdHandler();

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
        return entityIdHandler.handle(Person.ENTITY_NAME, person.getId(), personRepository::findById, storedPerson -> personRepository.save(person));
    }

    @Override
    public void deletePerson(String personId) {
        entityIdHandler.consume(Person.ENTITY_NAME, personId, personRepository::findById, personRepository::delete);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

}
