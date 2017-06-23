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
import javafx.print.PrinterJob;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.FileChooser;
import javafx.scene.Node;
import java.util.Optional;

public class TextEditorController {

    public File file = null;

    public static boolean isChanged = false;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem newMenuItem;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private MenuItem printMenuItem;

    @FXML
    private MenuItem saveAsMenuItem;

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
        if (this.isSavedFile()) {
            textArea.clear();
            file = null;
            isChanged = false;
        } else {
            alertDialog();
        }
    }

    @FXML
    void openMenuItemSelected(ActionEvent event) {
        // TODO: optimize opening a file.
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                FileReader fReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fReader);
                String line;
                while ((line = bufferedReader.readLine()) != null)
                    textArea.appendText(line + "\n");

                fReader.close();
            } catch (FileNotFoundException e) {
                System.err.println("File failed to open");
            } catch (IOException e) {
                System.err.println("File not found");
            }
            isChanged = false;
        }
    }

    @FXML 
    void printMenuItemSelected(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            job.showPrintDialog((Stage)menuBar.getScene().getWindow());
            System.out.println("It detected services");
            boolean success = job.printPage(textArea);
            if (success) {
                System.out.println("It was successful?");
                job.endJob();
            }
        }
    }

    @FXML
    void saveAsMenuItemSelected(ActionEvent event) {
        //Stage stage = (Stage) menuBar.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
            new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        file = fileChooser.showSaveDialog(null);

        if (file != null) {
            saveToFile(file);
        }
    }

    @FXML
    void saveMenuItemSelected(ActionEvent event) {
        if (file != null) {
            saveToFile(file);
        } else {
            saveAsMenuItemSelected(event);
        }
    }

    private void saveToFile(File file) {
        String content = textArea.getText();

        try {
            FileWriter fW = new FileWriter(file);
            fW.write(content);
            fW.close();
        } catch (IOException ex) {
            System.err.println("Failed to write to file");
        }

        isChanged = false;
    }

    public void initialize() {
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
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N,
            KeyCombination.CONTROL_DOWN));
        printMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.P,
            KeyCombination.CONTROL_DOWN));
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
            KeyCombination.CONTROL_DOWN));
    }

    public void alertDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Unsaved Changes");
        alert.setHeaderText(null);
        alert.setContentText("Close without saving?");

        ButtonType save = new ButtonType(
            file != null && file.exists() ? "Save" : "Save As");
        ButtonType cancel = new ButtonType("Cancel",
                ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(save, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == save) {
            saveMenuItemSelected(null);
        }
    }

    public boolean isSavedFile() {
        return isChanged ? false : true;
    }
}

