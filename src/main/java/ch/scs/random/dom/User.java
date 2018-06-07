package ch.scs.random.dom;

public class User {

    private String userName;
    private String userRole;

    public enum Role {
        Planer, Planprüfer, Betrachter;
    }

    public User() {
        this(System.getProperty("user.name"), Role.Planer.toString());
    }

    private User(String userName, String userRole) {
        this.userName = userName;
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
