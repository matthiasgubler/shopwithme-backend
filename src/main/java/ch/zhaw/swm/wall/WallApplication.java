package ch.zhaw.swm.wall;

import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.math.BigInteger;

@SpringBootApplication
@ComponentScan({"ch.zhaw.swm.wall.ioc", "ch.zhaw.swm.wall.contoller", "ch.zhaw.swm.wall.repository"})
public class WallApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(WallApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(WallApplication.class, args);
    }

    @Autowired
    private PersonRepository personRepository;

    @PostConstruct
    public void rofl() {
        Person person = new Person();
        person.getFriends().add(BigInteger.ONE);
        Person person1 = personRepository.save(person);
        Person person2 = personRepository.findById(person1.getId()).get();
        int a = 0;
    }

}
