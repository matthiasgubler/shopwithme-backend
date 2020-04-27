package ch.zhaw.swm.wall.context;

public class LoggedInUser {
    private final String sub;
    private final String email;

    public LoggedInUser(String sub, String email) {
        this.sub = sub;
        this.email = email;
    }

    public String getSub() {
        return sub;
    }

    public String getEmail() {
        return email;
    }

    public static LoggedInUser newDefaultUser() {
        return new LoggedInUser("local", "local@local.ch");
    }
}
