package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.person.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {

}
