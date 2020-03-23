package ch.zhaw.swm.wall.model.person;

import java.util.List;

public final class PersonBuilder {
    private String id;
    private String name;
    private String firstName;
    private Address address;
    private List<String> friends;

    private PersonBuilder() {
    }

    public static PersonBuilder aPerson() {
        return new PersonBuilder();
    }

    public PersonBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public PersonBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PersonBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public PersonBuilder withFriends(List<String> friends) {
        this.friends = friends;
        return this;
    }

    public Person build() {
        Person person = new Person(friends);
        person.setId(id);
        person.setName(name);
        person.setFirstName(firstName);
        person.setAddress(address);
        return person;
    }
}
