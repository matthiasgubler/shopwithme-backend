package ch.zhaw.swm.wall.model.person;


import ch.zhaw.swm.wall.model.AbstractDocument;
import org.springframework.data.annotation.PersistenceConstructor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Person extends AbstractDocument {
    private String name;
    private String firstName;
    private Address address;
    private final List<BigInteger> friends;

    public Person() {
        friends = new ArrayList<>();
    }

    @PersistenceConstructor
    public Person(List<BigInteger> friends) {
        this.friends = friends;
    }

    public List<BigInteger> getFriends() {
        return friends;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
