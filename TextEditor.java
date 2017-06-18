import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import java.util.Optional;

public class TextEditor extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root =
            FXMLLoader.load(getClass().getResource("TextEditor.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Text Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // TODO: add a dialog before closing window to ask to save file
    @Override
    public void stop() {
        if (TextEditorController.isChanged) {
            TextEditorController.alertDialog();
        }
    }
}
