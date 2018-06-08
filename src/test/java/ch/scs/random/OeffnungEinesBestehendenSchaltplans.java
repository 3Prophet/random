package ch.scs.random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ch.scs.random.SchaltplanEditorRobot.FileOpenAction;
import ch.scs.random.utils.FileChooserWrapper;

public class OeffnungEinesBestehendenSchaltplans extends JavaFxApplicationTest<ImageEditor> {

    @Mock
    private FileChooserWrapper chooser;

    @Override
    @BeforeEach
    public void setUp() throws TimeoutException, InterruptedException {
        // Setting the state to start with
        ImageEditor.setState(TestResources.getEmptyEditorState());
        super.setUp();
        MockitoAnnotations.initMocks(this);

        when(chooser.showOpenDialog(any())).thenReturn(TestResources.getTestSchaltplanFile());
        ((ImageEditor) application).setFileChooser(chooser);
    }

    OeffnungEinesBestehendenSchaltplans() {
        super(ImageEditor.class);
    }

    private static Iterable<Object[]> actionRoleData() {
        return Arrays.asList(new Object[][] { { SchaltplanEditorRobot.fileOpenViaMenuAction, "Planer" },
                { SchaltplanEditorRobot.fileOpenViaMenuAction, "Planprüfer" }, { SchaltplanEditorRobot.fileOpenViaMenuAction, "Betrachter" },
                { SchaltplanEditorRobot.fileOpenViaButtonAction, "Planer" }, { SchaltplanEditorRobot.fileOpenViaButtonAction, "Planprüfer" },
                { SchaltplanEditorRobot.fileOpenViaButtonAction, "Betrachter" } });
    }

    /**
     * Testfall: Oeffnen enies bestehenden Schaltplans.
     * 
     * @param action
     *            Aktion, damit dieser Tesfall getriggert wird.
     * @param userRole
     *            Benutzer rolle.
     * @throws InterruptedException
     */
    @ParameterizedTest(name = "[{index}] Action: {0}, Role: {1}")
    @MethodSource("actionRoleData")
    public void benutzerKannBestehendesBildOeffnen(FileOpenAction action, String userRole) {
        robot.userInitiatesOpeningOfExistingSchaltplan(action);
        robot.schaltplanEditorRequestsPathAndFileNameOfExistingFile();
        robot.userProvidesPathAndFileName();
        robot.schaltplanEditorRequestsUserNameAndRole();
        robot.userProvidesHisNameAndRoleToSchaltplanEditor(userRole);
        robot.schaltplanEditorOpensRequestedSchaltplan();
        robot.schaltplanEditorDisplaysNameAndPathOfTheRequestedSchaltplan();
        robot.schaltplanEditorKeepsRoleAndNameEnteredByUser(userRole);
    }

}
