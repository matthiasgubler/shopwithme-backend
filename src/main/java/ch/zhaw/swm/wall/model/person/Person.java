package ch.zhaw.swm.wall.model.person;


import ch.zhaw.swm.wall.model.AbstractDocument;
import ch.zhaw.swm.wall.model.Status;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.List;

public class Person extends AbstractDocument {
    public static final String ENTITY_NAME = "person";

    private String name;
    private String firstName;
    private Address address;
    private final List<String> friends;
    private Status status = Status.ACTIVE;

    public Person() {
        friends = new ArrayList<>();
    }

    @PersistenceConstructor
    public Person(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getFriends() {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
