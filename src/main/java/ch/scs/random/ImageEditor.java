package ch.scs.random;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import ch.scs.random.dom.User;
import ch.scs.random.utils.FileChooserWrapper;
import ch.scs.random.view.MainView;
import ch.scs.random.view.RolleDialog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ImageEditor extends Application {

    private Stage primaryStage;

    private BorderPane rootLayout;

    private User user = new User();

    private MainView mainViewController;

    private static FileChooserWrapper fileChooser = new FileChooserWrapper();

    public static void setFileChooser(FileChooserWrapper fileChooser) {
        ImageEditor.setFileChooser(fileChooser);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Editor");
        initRootLayout();
    }

    private void initRootLayout() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ImageEditor.class.getResource("view/MainView.fxml"));
        try {
            rootLayout = (BorderPane) loader.load();
            mainViewController = loader.getController();
            mainViewController.setUser(user);
            mainViewController.setRoot(this);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showRoleDialog() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass()
                .getResource("view/RolleDialog.fxml"));
        try {

            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                openFile(file);
            }

            BorderPane roleDialog = (BorderPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(roleDialog);
            dialogStage.setScene(scene);

            RolleDialog controller = loader.getController();
            controller.setUser(user);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            mainViewController.update();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private void openFile(File file) throws FileNotFoundException {
        System.out.println(file.getAbsolutePath());

        Image image = new Image(new FileInputStream(file), 0, 0, true, false);
        mainViewController.setImage(image);
    }

}
