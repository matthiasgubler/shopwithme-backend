package ch.zhaw.swm.wall.context;

public class LoggedInUser {
    private final String sub;
    private final String email;
    private final String name;

    public LoggedInUser(String sub, String email, String name) {
        this.sub = sub;
        this.email = email;
        this.name = name;
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

    public static LoggedInUser newDefaultUser() {
        return new LoggedInUser("local", "local@local.ch", "local");
    }
}
