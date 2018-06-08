package ch.scs.random.view;

import java.util.Arrays;

import ch.scs.random.dom.User;
import ch.scs.random.dom.User.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RolleDialog {

    @FXML
    private BorderPane userRoleDialogue;

    @FXML
    private Button roleOkButton;

    @FXML
    private Button roleCancelButton;

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    private TextField nameTextField;

    private Stage dialogStage;

    private User user;

    @FXML
    private void initialize() {
        setUpRoles();
    }

    @FXML
    public void handleOkButtonClicked() {
        user.setUserName(nameTextField.getText());
        user.setUserRole(roleChoiceBox.getValue());
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void setUpRoles() {
        Arrays.asList(Role.values())
                .stream()
                .forEach(role -> roleChoiceBox.getItems()
                        .add(role.toString()));
    }

    public void setUser(User user) {
        this.user = user;
        this.nameTextField.textProperty()
                .bind(user.userNameProperty());
        // this.nameTextField.setText(user.getUserName());
        this.roleChoiceBox.valueProperty()
                .bindBidirectional(user.userRoleProperty());
    }

}
