package ch.scs.random.dom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class Schaltplan {

    private StringProperty filePath;

    private StringProperty fileName;

    private File schaltplanFile;

    private ObjectProperty<Image> image;

    private StringExpression pathBinding;

    public Schaltplan() {
        this("", "");
    }

    public Schaltplan(String filePath, String fileName) {
        this.filePath = new SimpleStringProperty(filePath);
        this.fileName = new SimpleStringProperty(fileName);
        try {
            this.image = new SimpleObjectProperty<>(new Image(new FileInputStream(new File(filePath, fileName))));
        } catch (FileNotFoundException e2) {
            this.image = new SimpleObjectProperty();
        }
        pathBinding = Bindings.concat(this.filePath, System.getProperty("file.separator"), this.fileName);
        pathBinding.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    setImage(new Image(new FileInputStream(new File(getFilePath(), getFileName()))));
                } catch (FileNotFoundException e) {
                    setImage(null);
                }
            }

        });
    }

    public void setFilePath(String filePath) {
        this.filePath.set(filePath);
    }

    public String getFilePath() {
        return this.filePath.get();
    }

    public StringProperty filePathProperty() {
        return filePath;
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public String getFileName() {
        return fileName.get();
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public File getSchaltplanFile() {
        return new File(filePath.get(), fileName.get());
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    public Image getImage() {
        return image.get();
    }

}
