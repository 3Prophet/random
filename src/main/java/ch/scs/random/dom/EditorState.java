package ch.scs.random.dom;

public class EditorState {

    protected User user = null;

    protected Schaltplan schaltplan = null;

    public EditorState(User user, Schaltplan schaltplan) {
        this.user = user;
        this.schaltplan = schaltplan;
    }

    public User getUser() {
        return user;
    }

    public Schaltplan getSchaltplan() {
        return schaltplan;
    }

}
