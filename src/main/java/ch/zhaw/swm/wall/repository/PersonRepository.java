package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.person.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

public interface PersonRepository extends MongoRepository<Person, BigInteger> {
    List<Person> findByIdIn(List<BigInteger> ids);
}
