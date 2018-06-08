package ch.scs.random;

import java.io.File;
import java.io.IOException;

import ch.scs.random.dom.EditorState;
import ch.scs.random.dom.EmptyEditorState;
import ch.scs.random.dom.Schaltplan;
import ch.scs.random.dom.User;
import ch.scs.random.utils.FileChooserWrapper;
import ch.scs.random.view.MainView;
import ch.scs.random.view.RolleDialog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ImageEditor extends Application {

    private Stage primaryStage;

    private BorderPane rootLayout;

    private static User user = null;

    private static Schaltplan schaltplan = null;

    private MainView mainViewController;

    private static FileChooserWrapper fileChooser = new FileChooserWrapper();

    public static void setFileChooser(FileChooserWrapper fileChooser) {
        ImageEditor.fileChooser = fileChooser;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Editor");
        if (user == null || schaltplan == null) {
            setState(new EmptyEditorState());
        }
        initRootLayout();
    }

    private void initRootLayout() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ImageEditor.class.getResource("view/MainView.fxml"));
        try {
            rootLayout = (BorderPane) loader.load();
            mainViewController = loader.getController();
            mainViewController.setUser(user);
            mainViewController.setSchaltplan(schaltplan);
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

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private void openFile(File file) {
        System.out.println(file.getAbsolutePath());
        if (file.exists() && file.isFile()) {
            String path = file.getParent();
            String name = file.getAbsolutePath()
                    .substring(path.length() + 1);
            schaltplan.setFilePath(path);
            schaltplan.setFileName(name);
        }
    }

    public User getUser() {
        return user;
    }

    public static void setState(EditorState state) {
        ImageEditor.user = state.getUser();
        ImageEditor.schaltplan = state.getSchaltplan();
    }
}
