package ch.zhaw.swm.wall.context;

public class LoggedInUser {
    private final String sub;
    private final String email;
    private final String name;
    private final String imageURL;

    public LoggedInUser(String sub, String email, String name, String imageURL) {
        this.sub = sub;
        this.email = email;
        this.name = name;
        this.imageURL = imageURL;
    }

    private LoggedInUser(String sub, String email, String name) {
        this.sub = sub;
        this.email = email;
        this.name = name;
        imageURL = null;
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

    public String getImageURL() {
        return imageURL;
    }

    public static LoggedInUser newDefaultUser() {
        return new LoggedInUser("local", "local@local.ch", "localName");
    }
}
