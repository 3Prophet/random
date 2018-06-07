package ch.scs.random.view;

import ch.scs.random.ImageEditor;
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
    private ImageView imageView;

    @FXML
    private Button openButton;

    private ContextMenu imageConextMenu;

    private ImageEditor root;

    private User user;

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

    private void initializeContextMenu() {
        imageConextMenu = new ContextMenu();
        MenuItem saveMenuItem = new MenuItem("Save...");
        imageConextMenu.getItems()
                .add(saveMenuItem);
    }

    public void setUser(User user) {
        this.user = user;
        rolleLabel.setText(user.getUserRole());
    }

    public void update() {
        rolleLabel.setText(user.getUserRole());
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }

}