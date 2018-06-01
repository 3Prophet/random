package ch.scs.random;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ch.scs.random.utils.FileChooserWrapper;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

public class ClickApplication extends Application {

    private Parent sceneRoot;

    // application for acceptance tests.
    @Override
    public void start(Stage stage) {
        sceneRoot = new ClickPane(stage);

        Scene scene = new Scene(sceneRoot, 100, 100);

        stage.setScene(scene);
        stage.show();
    }

    public void setFileChooser(FileChooserWrapper chooser) {
        ((ClickPane) sceneRoot).setFileChooser(chooser);
    }

    // scene object for unit tests
    public static class ClickPane extends BorderPane {
        private FileChooserWrapper fileChooser = new FileChooserWrapper();

        private ImageView imageView;

        final private ContextMenu contextMenu = new ContextMenu();

        private Label label = new Label();

        public ClickPane(Stage stage) {
            super();
            MenuBar menu = new MenuBar();

            Menu fileMenu = new Menu("File");

            fileMenu.setId("fileMenu");

            MenuItem openEntry = new MenuItem("Open...");

            openEntry.setId("openFile");

            fileMenu.getItems()
                    .addAll(openEntry);
            menu.getMenus()
                    .addAll(fileMenu);

            openEntry.setOnAction(e -> {
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    try {
                        openFile(file);
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

            setTop(menu);

            Button button = new Button("click me!");
            button.setId("button");
            button.setOnAction(actionEvent -> button.setText("clicked!"));

            label.setId("label");

            setBottom(button);
            setLeft(label);

            imageView = new ImageView();
            imageView.setId("imageView");
            Image image = new Image(getClass().getClassLoader()
                    .getResourceAsStream("Aufloesevorbereiter_abgefallen_Kontakt_leitend_links.bmp"), 0, 0, true, false);

            imageView.setImage(image);
            setCenter(imageView);
            setupContextMenu();
        }

        public void setFileChooser(FileChooserWrapper chooser) {
            fileChooser = chooser;
        }

        public void setupContextMenu() {
            contextMenu.setId("contextMenu");
            MenuItem cmItem1 = new MenuItem("Copy Image");
            cmItem1.setId("cmItem1");
            cmItem1.setOnAction(actionEvent -> label.setText("Image is copied"));
            contextMenu.getItems()
                    .add(cmItem1);
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(imageView, e.getScreenX(), e.getScreenY());
                }
            });
        }

        private void openFile(File file) throws FileNotFoundException {
            System.out.println(file.getAbsolutePath());

            Image image = new Image(new FileInputStream(file), 0, 0, true, false);
            imageView.setImage(image);
        }
    }
}
