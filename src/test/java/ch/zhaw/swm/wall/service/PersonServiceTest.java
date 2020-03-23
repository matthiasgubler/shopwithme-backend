package ch.zhaw.swm.wall.service;

import ch.zhaw.swm.wall.contoller.exception.NotFoundException;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.model.person.PersonBuilder;
import ch.zhaw.swm.wall.repository.PersonRepository;
import ch.zhaw.swm.wall.services.person.impl.PersonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private static final String DEFAULT_ID = "10";

    @Test
    void friends_not_found() {
        Person person = PersonBuilder.aPerson().withId(DEFAULT_ID).build();

        Person personA = PersonBuilder.aPerson().build();
        Person personB = PersonBuilder.aPerson().build();
        Person personC = PersonBuilder.aPerson().build();

        when(personRepository.findById(DEFAULT_ID)).thenReturn(Optional.empty());
        when(personRepository.findByIdIn(Mockito.any())).thenReturn(Arrays.asList(personA, personB, personC));

        Assertions.assertThrows(NotFoundException.class, () -> personService.findFriends(person.getId()));
    }

    @Test
    void friends() {
        Person person = PersonBuilder.aPerson().withId(DEFAULT_ID).build();

        Person personA = PersonBuilder.aPerson().build();
        Person personB = PersonBuilder.aPerson().build();
        Person personC = PersonBuilder.aPerson().build();

        when(personRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(person));
        when(personRepository.findByIdIn(Mockito.any())).thenReturn(Arrays.asList(personA, personB, personC));

        List<Person> friends = personService.findFriends(DEFAULT_ID);
        assertEquals(3, friends.size());
    }

    @Test
    void create() {
        Person personA = PersonBuilder.aPerson().withId(null).build();
        Person personB = PersonBuilder.aPerson().withId(DEFAULT_ID).build();
        when(personRepository.save(personA)).thenReturn(personB);
        Person result = personService.createPerson(personA);
        Assertions.assertEquals(DEFAULT_ID, result.getId());
    }

    @Test
    void edit_not_found() {
        Person personA = PersonBuilder.aPerson().withId(DEFAULT_ID).build();
        when(personRepository.findById(DEFAULT_ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> personService.editPerson(personA));
    }

    @Test
    void edit() {
        Person personA = PersonBuilder.aPerson().withId(DEFAULT_ID).build();
        when(personRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(personA));
        when(personRepository.save(personA)).thenReturn(personA);
        Person person = personService.editPerson(personA);
        Assertions.assertEquals(DEFAULT_ID, person.getId());
        verify(personRepository).save(personA);
    }

    @Test
    void delete() {
        Person personA = PersonBuilder.aPerson().withId(DEFAULT_ID).build();
        when(personRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(personA));
        personService.deletePerson(DEFAULT_ID);
        verify(personRepository).delete(personA);
    }

    @Test
    void delete_not_found() {
        when(personRepository.findById(DEFAULT_ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> personService.deletePerson(DEFAULT_ID));
    }
}
