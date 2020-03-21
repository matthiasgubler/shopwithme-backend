package ch.zhaw.swm.wall.contoller;

import ch.zhaw.swm.wall.contoller.exception.NotFoundException;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.model.person.PersonBuilder;
import ch.zhaw.swm.wall.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonsControllerTest {

    @Mock
    private PersonRepository personRepositoryMock;

    @InjectMocks
    private PersonsController personsController;

    @Test
    void newPerson() {
        Person person = PersonBuilder.aPerson().build();
        personsController.newPerson(person);
        verify(personRepositoryMock).save(person);
    }

    @Test
    void all_emtpy_result() {
        when(personRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        assertEquals(0, personsController.all().size());
    }

    @Test
    void all() {
        Person personA = PersonBuilder.aPerson().build();
        Person personB = PersonBuilder.aPerson().build();
        Person personC = PersonBuilder.aPerson().build();
        when(personRepositoryMock.findAll()).thenReturn(Arrays.asList(personA, personB, personC));
        assertEquals(3, personsController.all().size());
    }

    @Test
    void one_found() {
        Person personA = PersonBuilder.aPerson().withId(BigInteger.TEN).build();
        when(personRepositoryMock.findById(BigInteger.TEN)).thenReturn(Optional.of(personA));
        assertEquals(personA, personsController.one(BigInteger.TEN));
    }

    @Test
    void one_not_found() {
        when(personRepositoryMock.findById(BigInteger.TEN)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> personsController.one(BigInteger.TEN));
    }

    @Test
    void delete() {
        Person personA = PersonBuilder.aPerson().withId(BigInteger.TEN).build();
        when(personRepositoryMock.findById(BigInteger.TEN)).thenReturn(Optional.of(personA));
        personsController.delete(BigInteger.TEN);
        verify(personRepositoryMock).delete(personA);
    }

    @Test
    void delete_not_found() {
        when(personRepositoryMock.findById(BigInteger.TEN)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> personsController.delete(BigInteger.TEN));
    }
}
