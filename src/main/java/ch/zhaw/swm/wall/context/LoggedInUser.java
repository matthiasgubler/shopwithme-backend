package ch.zhaw.swm.wall.context;

public class LoggedInUser {
    private final String sub;
    private final String email;
    private final String name;
    private final String picture;

    public LoggedInUser(String sub, String email, String name, String picture) {
        this.sub = sub;
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

    public String getSub() {
        return sub;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public static LoggedInUser newDefaultUser() {
        return new LoggedInUser("local", "local@local.ch", "localName", "https://lh3.googleusercontent.com/a-/AOh14GjymxkVspHEnoPtc8bk6UKHTe_62kllAKxex9Gj=s96-c");
    }
}
