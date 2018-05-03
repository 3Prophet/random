package ch.scs.random;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClickApplication extends Application {
	// application for acceptance tests.
	@Override
	public void start(Stage stage) {
		Parent sceneRoot = new ClickPane();
		Scene scene = new Scene(sceneRoot, 100, 100);
		stage.setScene(scene);
		stage.show();
	}

	// scene object for unit tests
	public static class ClickPane extends BorderPane {
		public ClickPane() {
			super();
			Button button = new Button("click me!");
			button.setId("button");
			button.setOnAction(actionEvent -> button.setText("clicked!"));

			setBottom(button);

			ImageView imageView = new ImageView();
			imageView.setId("imageView");
			Image image = new Image(
					getClass().getClassLoader().getResourceAsStream("Ausgabeschalter_angezogen_rechts.bmp"));
			imageView.setImage(image);
			setCenter(imageView);
		}
	}
}
