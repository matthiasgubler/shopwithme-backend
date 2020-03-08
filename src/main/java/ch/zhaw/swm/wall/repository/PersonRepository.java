package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface PersonRepository extends MongoRepository<Person, BigInteger> {

}
