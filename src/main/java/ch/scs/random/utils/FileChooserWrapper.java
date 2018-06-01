package ch.scs.random.utils;

import java.io.File;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * Decorator over @link{javafx.stage#FileChooser} which can be mocked and delegates all its functionality to the latter class.
 * 
 * @code{FileChooser} itself is a final class, so can't be mocked by default by Mockito.
 */
public class FileChooserWrapper {

    private static final FileChooser chooser = new FileChooser();

    @Override
    public int hashCode() {
        return chooser.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return chooser.equals(obj);
    }

    public final void setTitle(String value) {
        chooser.setTitle(value);
    }

    public final String getTitle() {
        return chooser.getTitle();
    }

    public final StringProperty titleProperty() {
        return chooser.titleProperty();
    }

    public final void setInitialDirectory(File value) {
        chooser.setInitialDirectory(value);
    }

    public final File getInitialDirectory() {
        return chooser.getInitialDirectory();
    }

    public final ObjectProperty<File> initialDirectoryProperty() {
        return chooser.initialDirectoryProperty();
    }

    public final void setInitialFileName(String value) {
        chooser.setInitialFileName(value);
    }

    public final String getInitialFileName() {
        return chooser.getInitialFileName();
    }

    public final ObjectProperty<String> initialFileNameProperty() {
        return chooser.initialFileNameProperty();
    }

    @Override
    public String toString() {
        return chooser.toString();
    }

    public ObservableList<ExtensionFilter> getExtensionFilters() {
        return chooser.getExtensionFilters();
    }

    public final ObjectProperty<ExtensionFilter> selectedExtensionFilterProperty() {
        return chooser.selectedExtensionFilterProperty();
    }

    public final void setSelectedExtensionFilter(ExtensionFilter filter) {
        chooser.setSelectedExtensionFilter(filter);
    }

    public final ExtensionFilter getSelectedExtensionFilter() {
        return chooser.getSelectedExtensionFilter();
    }

    public File showOpenDialog(Window ownerWindow) {
        return chooser.showOpenDialog(ownerWindow);
    }

    public List<File> showOpenMultipleDialog(Window ownerWindow) {
        return chooser.showOpenMultipleDialog(ownerWindow);
    }

    public File showSaveDialog(Window ownerWindow) {
        return chooser.showSaveDialog(ownerWindow);
    }
}
