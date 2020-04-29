package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.person.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {

    Optional<Person> findByEmail(String email);

}
