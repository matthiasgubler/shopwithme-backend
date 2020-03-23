package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.person.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {
    List<Person> findByIdIn(List<String> ids);
}
