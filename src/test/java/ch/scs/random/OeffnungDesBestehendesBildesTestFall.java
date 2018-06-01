package ch.scs.random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;

import ch.scs.random.utils.FileChooserWrapper;

public class OeffnungDesBestehendesBildesTestFall extends JavaFxApplicationTest<ImageEditor> {

    @Mock
    private FileChooserWrapper chooser;

    @Override
    public void setUp() throws TimeoutException, InterruptedException {

        super.setUp();
        MockitoAnnotations.initMocks(this);

        when(chooser.showOpenDialog(any()))
                .thenReturn(new File("D:\\stw\\workspaces\\new\\random\\src\\main\\resources\\Ausgabeschalter_angezogen_rechts.bmp"));
        ((ClickApplication) application).setFileChooser(chooser);
    }

    Robot robot = null;

    OeffnungDesBestehendesBildesTestFall() {
        super(ImageEditor.class);
        robot = new Robot(super.fxRobot);
    }

    private class Robot {

        private FxRobot fxRobot = null;

        public Robot(FxRobot fxRobot) {
            this.fxRobot = fxRobot;
        }

        public void userInitiatesOpeningOfExistingSchaltplan() throws InterruptedException {
            fxRobot.clickOn("#fileMenu")
                    .clickOn("#openMenuItem");
            Thread.sleep(2000);

        }

        public void schaltplanEditorRequestsPathAndFileNameOfExistingFile() {
            // TODO Auto-generated method stub

        }

        public void userProvidesPathAndFileName() {
            // TODO Auto-generated method stub

        }

        public void schaltplanEditorRequestsUserNameAndRole() {
            // TODO Auto-generated method stub

        }

        public void userProvidesHisNameAndRoleToSchaltplanEditor() {
            // TODO Auto-generated method stub

        }

        public void schaltplanEditorOpensRequestedSchaltplan() {
            // TODO Auto-generated method stub

        }

        public void schaltplanEditorDisplaysNameAndPathOfTheRequestedSchaltplan() {
            // TODO Auto-generated method stub

        }

        public void schaltplanEditorKeepsRoleAndNameEnteredByUser() {
            // TODO Auto-generated method stub

        }
    }

    public static Iterable<Object[]> ausloeserRolleKombinationen() {
        return Arrays.asList(new Object[][] { { "Ausgabeschalter_angezogen_rechts.bmp", -1L },
                { "Aufloesevorbereiter_abgefallen_Kontakt_leitend_links.bmp", 0L } });
    }

    @Test
    public void benutzerKannBestehendesBildOeffnen() throws InterruptedException {
        robot.userInitiatesOpeningOfExistingSchaltplan();
        robot.schaltplanEditorRequestsPathAndFileNameOfExistingFile();
        robot.userProvidesPathAndFileName();
        robot.schaltplanEditorRequestsUserNameAndRole();
        robot.userProvidesHisNameAndRoleToSchaltplanEditor();
        robot.schaltplanEditorOpensRequestedSchaltplan();
        robot.schaltplanEditorDisplaysNameAndPathOfTheRequestedSchaltplan();
        robot.schaltplanEditorKeepsRoleAndNameEnteredByUser();
    }

}
