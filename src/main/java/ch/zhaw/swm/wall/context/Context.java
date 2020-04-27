package ch.zhaw.swm.wall.context;

public class Context {
    private static ThreadLocal<Context> threadLocal = new ThreadLocal<>();
    private LoggedInUser loggedInUser;

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public static Context getCurrentContext() {
        Context context = threadLocal.get();
        if (context == null) {
            context = new Context();
            threadLocal.set(context);
        }
        return context;
    }
}
