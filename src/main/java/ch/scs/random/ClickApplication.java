package ch.scs.random;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ch.scs.random.utils.FileChoice;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    public void setFileChooser(FileChoice chooser) {
        ((ClickPane) sceneRoot).setFileChooser(chooser);
    }

    // scene object for unit tests
    public static class ClickPane extends BorderPane {
        private FileChoice fileChooser = new FileChoice();

        private ImageView imageView;

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

            setBottom(button);

            imageView = new ImageView();
            imageView.setId("imageView");
            Image image = new Image(getClass().getClassLoader()
                    .getResourceAsStream("Aufloesevorbereiter_abgefallen_Kontakt_leitend_links.bmp"), 0, 0, true, false);

            imageView.setImage(image);
            setCenter(imageView);
        }

        public void setFileChooser(FileChoice chooser) {
            fileChooser = chooser;
        }

        public void setUpListeners() {

        }

        private void openFile(File file) throws FileNotFoundException {
            System.out.println(file.getAbsolutePath());

            Image image = new Image(new FileInputStream(file), 0, 0, true, false);
            imageView.setImage(image);
        }
    }
}
