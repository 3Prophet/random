package ch.scs.random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.testfx.api.FxRobot;

import ch.scs.random.dom.EditorState;
import ch.scs.random.dom.User;
import ch.scs.random.dom.User.Role;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;

public class SchaltplanEditorRobot {

    public interface UserAction {
        public void exec(FxRobot fxRobot);
    }

    public interface FileOpenAction extends UserAction {
    }

    public static FileOpenAction fileOpenViaMenuAction = new FileOpenAction() {
        @Override
        public void exec(FxRobot fxRobot) {
            fxRobot.clickOn("#fileMenu")
                    .clickOn("#openMenuItem");
        }

        @Override
        public String toString() {
            return "Opening Schaltplan via Menu";
        }
    };

    public static FileOpenAction fileOpenViaToolbarAction = new FileOpenAction() {
        @Override
        public void exec(FxRobot fxRobot) {
            fxRobot.clickOn("#openButton");
        }

        @Override
        public String toString() {
            return "Opening Schaltplan via Button";
        }
    };

    public interface FileCloseAction extends UserAction {
    }

    public static FileCloseAction fileCloseViaContextMenuAction = new FileCloseAction() {
        @Override
        public void exec(FxRobot fxRobot) {
            fxRobot.clickOn("#imageView", MouseButton.SECONDARY);
            fxRobot.clickOn("#closeContext");

            /*
             * fxRobot.sleep(1000); fxRobot.type(KeyCode.DOWN); fxRobot.sleep(1000); fxRobot.type(KeyCode.ENTER); fxRobot.sleep(1000);
             */
        }

        @Override
        public String toString() {
            return "Closing Schaltplan via Context Menu";
        }
    };

    public static FileCloseAction fileCloseActionViaToolbar = fxRobot -> fxRobot.clickOn("#closeButton");

    public interface PreconditionWithRole {
        public void establish(SchaltplanEditorRobot robot, String userRole, FileOpenAction action);
    }

    public interface OpenSchaltplanWithRolePrecondition extends PreconditionWithRole {
    };

    public static OpenSchaltplanWithRolePrecondition openSchaltplanWithRole = new OpenSchaltplanWithRolePrecondition() {

        @Override
        public void establish(SchaltplanEditorRobot robot, String userRole, FileOpenAction action) {
            action.exec(robot.fxRobot);
            robot.userProvidesHisNameAndRoleToSchaltplanEditor(userRole);
        }
    };

    private FxRobot fxRobot = null;
    private Application application;

    public SchaltplanEditorRobot(FxRobot fxRobot) {
        this.fxRobot = fxRobot;
    }

    FxRobot getFxRobot() {
        return fxRobot;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void userInitiatesOpeningOfExistingSchaltplan(FileOpenAction action) {
        fxRobot.sleep(1000);
        action.exec(fxRobot);

    }

    public void schaltplanEditorRequestsPathAndFileNameOfExistingFile() {
        // TODO Auto-generated method stub

    }

    public void userProvidesPathAndFileName() {
        // TODO Auto-generated method stub

    }

    public void schaltplanEditorRequestsUserNameAndRole() {
        BorderPane userRoleDialog = (BorderPane) find("#userRoleDialogue");
        assertThat(userRoleDialog.isVisible(), is(true));

    }

    private boolean selectInChoiceBox(String choiceBoxFxId, String entryToSelect) {

        int valueCount = ((ChoiceBox<String>) find(choiceBoxFxId)).getItems()
                .size();

        String currentValue = "";
        for (int i = 0; i < valueCount; i++) {
            currentValue = ((ChoiceBox<String>) find(choiceBoxFxId)).getValue();
            if (currentValue.equals(entryToSelect)) {
                break;
            }
            fxRobot.clickOn(choiceBoxFxId);
            fxRobot.sleep(1000);
            fxRobot.type(KeyCode.DOWN);
            fxRobot.sleep(1000);
            fxRobot.type(KeyCode.ENTER);
            fxRobot.sleep(1000);

        }

        if (currentValue.equals(entryToSelect)) {
            return true;
        }
        return false;
    }

    // TODO: Better selection for Choice box
    public void userProvidesHisNameAndRoleToSchaltplanEditor(String userRole) {
        selectInChoiceBox("#roleChoiceBox", userRole);
        fxRobot.sleep(2000);
        fxRobot.clickOn("#roleOkButton");
    }

    public void schaltplanEditorOpensRequestedSchaltplan() {
        ImageView imageView = (ImageView) find("#imageView");
        Image guiImage = imageView.getImage();

        Image refImage = new Image(TestResources.getTestSchaltplanInputStream());
        assertThat(UtilsTestFx.computeDifference(guiImage, refImage), is(0L));
    }

    public void schaltplanEditorDisplaysNameAndPathOfTheRequestedSchaltplan() {
    }

    public void schaltplanEditorKeepsRoleAndNameEnteredByUser(String userRole) {
        User user = ((ImageEditor) application).getUser();
        assertThat(user.getUserName(), equalTo("dlogvinovich"));
        assertThat(user.getUserRole(), equalTo(userRole));

    }

    /**
     * Returns widget(node) identified with its fx:id.
     * 
     * @param fxId
     *            Id of the searched parameter.
     * @return Found node.
     */
    protected <T extends Node> T find(final String fxId) {
        return fxRobot.lookup(fxId)
                .query();
    }

    public void userInitiatesClosingOfExistingSchaltplan(FileCloseAction action) {
        action.exec(fxRobot);
    }

    public void openUnmodifiedSchaltplanAs(Role userRole) {
        userInitiatesOpeningOfExistingSchaltplan(fileOpenViaToolbarAction);
        userProvidesHisNameAndRoleToSchaltplanEditor(userRole.toString());
    }

    public void establishSchaltplanEditorState(EditorState editorState) {
        ImageEditor.setState(editorState);

    }

    public void thereIsNoOpenedSchaltplan() {
        ImageView imageView = (ImageView) find("#imageView");
        assertThat(null, is(imageView.getImage()));
    }

    public void thereIsNoFilePathAndNameShown() {
        Label filePathLabel = (Label) find("#filePath");
        Label fileNameLabel = (Label) find("#fileName");
        assertThat(filePathLabel.getText(), is(""));
        assertThat(fileNameLabel.getText(), is(""));
    }

    public void userNameAndUserRoleAreUndefined() {
        Label nameLabel = (Label) find("#nameLabel");
        Label rolleLabel = (Label) find("#rolleLabel");
        assertThat(nameLabel.getText(), is(""));
        assertThat(rolleLabel.getText(), is(""));

    }

}
