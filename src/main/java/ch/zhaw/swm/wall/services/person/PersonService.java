package ch.zhaw.swm.wall.services.person;

import ch.zhaw.swm.wall.model.person.Person;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface PersonService {
    Optional<Person> findById(BigInteger id);
    Person createPerson(Person person);
    Person editPerson(Person person);
    List<Person> getFriends(BigInteger personId);
    List<Person> getFriends(Person person);
}
