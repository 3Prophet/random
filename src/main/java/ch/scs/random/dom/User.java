package ch.scs.random.dom;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private StringProperty userName;
    private StringProperty userRole;

    public enum Role {
        Planer, Planprüfer, Betrachter;
    }

    public User() {
        this("", "");
    }

    public User(String userName, String userRole) {
        this.userName = new SimpleStringProperty(userName);
        this.userRole = new SimpleStringProperty(userRole);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getUserRole() {
        return userRole.get();
    }

    public void setUserRole(String userRole) {
        this.userRole.set(userRole);
    }

    public StringProperty userRoleProperty() {
        return userRole;
    }
}
