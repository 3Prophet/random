package ch.scs.random;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

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

}
