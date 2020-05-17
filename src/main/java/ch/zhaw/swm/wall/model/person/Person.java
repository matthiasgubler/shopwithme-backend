package ch.zhaw.swm.wall.model.person;


import ch.zhaw.swm.wall.model.AbstractDocument;
import ch.zhaw.swm.wall.model.Status;

public class Person extends AbstractDocument {

    public static final String ENTITY_NAME = "person";

    private String username;
    private String email;
    private String picture = "";
    private Status status = Status.ACTIVE;

    public Person() {
    }

    public Person(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Person(String username, String email, String picture) {
        this.username = username;
        this.email = email;
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
