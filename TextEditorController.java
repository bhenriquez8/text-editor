import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.FileChooser;
import javafx.scene.Node;

public class TextEditorController {

    public static boolean isChanged = false;
    public static boolean isOpened = false;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem newMenuItem;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private TextArea textArea;

    @FXML
    void exitMenuItemSelected(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void newMenuItemSelected(ActionEvent event) {

    }

    @FXML
    void openMenuItemSelected(ActionEvent event) {
        // TODO: optimize opening a file.
        FileChooser fileChooser = new FileChooser();
        File fileSelected = fileChooser.showOpenDialog(null);

        if (fileSelected != null) {
            try {
                FileReader fReader = new FileReader(fileSelected);
                BufferedReader bufferedReader = new BufferedReader(fReader);
                String line;
                while ((line = bufferedReader.readLine()) != null)
                    textArea.appendText(line + "\n");

                fReader.close();

                isOpened = true;
            } catch (FileNotFoundException e) {
                System.err.println("File failed to open");
            } catch (IOException e) {
                System.err.println("File not found");
            }
        }
    }

    @FXML
    void saveMenuItemSelected(ActionEvent event) {
        Stage stage = (Stage) menuBar.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
            new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveToFile(file);
        }
    }

    public void saveToFile(File file) {
        String content = textArea.getText();

        if ("".equals(content)) {
            System.out.println("Emptry TextArea");
        } else {
            try {
                FileWriter fW = new FileWriter(file);
                fW.write(content);
                fW.close();
            } catch (IOException ex) {
                System.err.println("Failed to write to file");
            }
        }
    }

    public void initialize() {
        // TODO: initiazlie File objects here for better
        // code readability
        textArea.textProperty().addListener(
            new ChangeListener<String>() {
                @Override
                public void changed(final ObservableValue<? extends
                String> observable, String ov, String nv) {
                    isChanged = true;
                }
            }
        );

        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.W,
                    KeyCombination.CONTROL_DOWN));
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
                    KeyCombination.CONTROL_DOWN));
    }

}

