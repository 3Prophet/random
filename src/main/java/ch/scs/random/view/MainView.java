package ch.scs.random.view;

import ch.scs.random.ImageEditor;
import ch.scs.random.dom.Schaltplan;
import ch.scs.random.dom.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class MainView {

    @FXML
    private BorderPane rootView;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private Label rolleLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Button openButton;

    @FXML
    private Button closeButton;

    private ContextMenu imageConextMenu;

    @FXML
    private Label filePath;

    @FXML
    private Label fileName;

    private ImageEditor root;

    private User user;

    private Schaltplan schaltplan;

    public void setRoot(ImageEditor root) {
        this.root = root;
    }

    @FXML
    public void initialize() {
        initializeContextMenu();
    }

    @FXML
    private void handleImageViewRightClick(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            imageConextMenu.show(imageView, e.getScreenX(), e.getScreenY());
        }
    }

    @FXML
    private void handleOpenMenuItemSelection(ActionEvent e) {
        root.showRoleDialog();
    }

    @FXML
    private void handleSchaltplanClose(ActionEvent e) {
        schaltplan.setFileName("");
        schaltplan.setFilePath("");
        user.setUserName("");
        user.setUserRole("");
    }

    private void initializeContextMenu() {
        imageConextMenu = new ContextMenu();
        MenuItem closeMenuItem = new MenuItem("Close...");
        closeMenuItem.setId("closeContext");
        closeMenuItem.setOnAction(e -> this.handleSchaltplanClose(e));

        imageConextMenu.getItems()
                .add(closeMenuItem);
    }

    public void setUser(User user) {
        this.user = user;
        rolleLabel.textProperty()
                .bind(user.userRoleProperty());
        nameLabel.textProperty()
                .bind(user.userNameProperty());
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    public void setSchaltplan(Schaltplan schaltplan) {
        this.schaltplan = schaltplan;
        filePath.textProperty()
                .bind(schaltplan.filePathProperty());
        fileName.textProperty()
                .bind(schaltplan.fileNameProperty());
        imageView.imageProperty()
                .bind(schaltplan.imageProperty());
    }

}
