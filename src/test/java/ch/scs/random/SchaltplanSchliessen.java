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

import ch.scs.random.dom.User.Role;
import ch.scs.random.utils.FileChooserWrapper;

public class SchaltplanSchliessen extends JavaFxApplicationTest<ImageEditor> {

    @Mock
    private FileChooserWrapper chooser;

    @Override
    @BeforeEach
    public void setUp() throws TimeoutException, InterruptedException {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        when(chooser.showOpenDialog(any())).thenReturn(TestResources.getTestSchaltplanFile());
        ((ImageEditor) application).setFileChooser(chooser);
    }

    SchaltplanSchliessen() {
        super(ImageEditor.class);
    }

    @ParameterizedTest(name = "[{index}] Closing Schaltplan opened by {1} using action {0}")
    @MethodSource("closeActions")
    public void closePlan(SchaltplanEditorRobot.FileCloseAction action, Role userRole) {
        robot.openUnmodifiedSchaltplanAs(userRole);
        robot.userInitiatesClosingOfExistingSchaltplan(action);
        robot.thereIsNoOpenedSchaltplan();
        robot.thereIsNoFilePathAndNameShown();
        robot.userNameAndUserRoleAreUndefined();
    }

    private static Iterable<Object[]> closeActions() {
        return Arrays.asList(new Object[][] { { SchaltplanEditorRobot.fileCloseViaContextMenuAction, Role.Planer },
                { SchaltplanEditorRobot.fileCloseViaContextMenuAction, Role.Planprüfer },
                { SchaltplanEditorRobot.fileCloseViaContextMenuAction, Role.Betrachter },
                { SchaltplanEditorRobot.fileCloseActionViaToolbar, Role.Planer },
                { SchaltplanEditorRobot.fileCloseActionViaToolbar, Role.Planprüfer },
                { SchaltplanEditorRobot.fileCloseActionViaToolbar, Role.Betrachter } });
    }
}
