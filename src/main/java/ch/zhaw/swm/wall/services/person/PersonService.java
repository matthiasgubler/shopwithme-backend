package ch.zhaw.swm.wall.services.person;

import ch.zhaw.swm.wall.model.person.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Optional<Person> findById(String id);
    Person createPerson(Person person);
    Person editPerson(Person person);
    void deletePerson(String personId);
    List<Person> findAll();
    List<Person> findFriends(String personId);
    List<Person> findFriends(Person person);
}
