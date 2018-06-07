package ch.scs.random;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.application.Application;
import javafx.application.Platform;

/**
 * Class responsible for starting and shutting down the application to be tested.
 * 
 * @param <T>
 *            Application main class.
 */
public class JavaFxApplicationTest<T extends Application> {

    private Class<T> applicationClass;

    protected Application application;

    protected final FxRobot fxRobot = new FxRobot();

    protected final SchaltplanEditorRobot robot;

    public interface Precondition {
        public void establishPrecondition(SchaltplanEditorRobot robot, String rolle);
    }

    JavaFxApplicationTest(Class<T> applicationClass) {
        this.applicationClass = applicationClass;
        this.robot = new SchaltplanEditorRobot(fxRobot);
    }

    @BeforeAll
    public static void setUpBeforeAll() throws TimeoutException {
        setupHeadlessEnvironment();
        FxToolkit.registerPrimaryStage();
    }

    /**
     * Sets environment variables required for headless testing.
     */
    public static void setupHeadlessEnvironment() {
        if (Boolean.getBoolean("headless")) {
            // TODO replace sysout with logger
            System.out.println("Setting up environment for headless testing...");

            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("headless.geometry", "1920x1200-32");
            System.setProperty("java.awt.headless", "true");
        }
    }

    @BeforeEach
    public void setUp() throws TimeoutException, InterruptedException {
        FxToolkit.showStage();
        application = FxToolkit.setupApplication(applicationClass);
        robot.setApplication(application);
    }

    @AfterEach
    public void cleanAfterTest() throws TimeoutException {
        FxToolkit.cleanupApplication(application);
        FxToolkit.hideStage();
    }

    @AfterAll
    public static void cleanAfterAllTests() {
        Platform.exit();
    }

    public SchaltplanEditorRobot getRobot() {
        return robot;
    }
}
