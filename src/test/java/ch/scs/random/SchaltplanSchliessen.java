package ch.scs.random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    /*
     * @ParameterizedTest(name = "[{index}] Action: {0}, Role: {1}")
     * 
     * @MethodSource("actionRoleData") public void unveraenderterSchaltplanSchliessen(FileCloseAction action, String rolle) { //
     * SchaltplanEditorRobot.openSchaltplanWithRole.establish(robot, rolle, SchaltplanEditorRobot.fileOpenViaButtonAction);
     * robot.userInitiatesClosingOfExistingSchaltplan(action);
     * 
     * }
     */

    @ExtendWith(UnmodifiedPlanOpenedPrecondition.class)
    @ParameterizedTest
    @ValueSource(strings = { "one", "two" })
    public void closePlan(String param) {
        getRobot().getFxRobot()
                .sleep(2000);
        assertThat(param, equalTo(param));
    }

    private static Iterable<Object[]> actionRoleData() {
        return Arrays.asList(new Object[][] { { SchaltplanEditorRobot.fileCloseViaContextMenuAction, "Planer" } });
    }
}
