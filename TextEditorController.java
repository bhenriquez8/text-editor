import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.scene.Node;

public class TextEditorController {

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

}

