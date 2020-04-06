package ch.zhaw.swm.wall.model.person;

public final class PersonBuilder {
    private String id;
    private String username;
    private String email;

    private PersonBuilder() {
    }

    public static PersonBuilder aPerson() {
        return new PersonBuilder();
    }

    public PersonBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public PersonBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public PersonBuilder withEmail(String email) {
        this.email = email;
        return this;
    }


    public Person build() {
        Person person = new Person(username, email);
        person.setId(id);
        return person;
    }

}
