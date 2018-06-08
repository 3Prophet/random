package ch.scs.random;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import ch.scs.random.dom.EditorState;
import ch.scs.random.dom.EmptyEditorState;
import ch.scs.random.dom.Schaltplan;
import ch.scs.random.dom.User;
import ch.scs.random.dom.User.Role;

public class TestResources {

    private static final String resourcesDirectoryPath = "D:\\stw\\workspaces\\new\\random\\src\\main\\resources";

    private static final String testSchaltplanFileName = "Ausgabeschalter_angezogen_rechts.bmp";

    private static final String userName = "dlogvinovich";

    private static final List<String> userRoles = Arrays.asList("Planer", "Planprüfer", "Betrachter");

    public static InputStream getTestSchaltplanInputStream() {
        return TestResources.class.getClassLoader()
                .getResourceAsStream(testSchaltplanFileName);
    }

    public static File getTestSchaltplanFile() {
        File file = new File(resourcesDirectoryPath, testSchaltplanFileName);
        return file;
    }

    /**
     * @param userRole
     * @return Editor state with predefined user name and variable user role and unmodified reference Schaltplan.
     */
    public static EditorState getUnmodifiedSchaltplanState(Role userRole) {
        return new EditorState(new User(userName, userRole.toString()), new Schaltplan(resourcesDirectoryPath, testSchaltplanFileName));
    }

    /**
     * @return Editor state with undefined user and empty Schaltplan.
     */
    public static EditorState getEmptyEditorState() {
        return new EmptyEditorState();
    }

}
