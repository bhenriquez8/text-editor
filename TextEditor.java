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
import java.net.URL;
import javafx.fxml.JavaFXBuilderFactory;

public class TextEditor extends Application {

    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root =
            //FXMLLoader.load(getClass().getResource("TextEditor.fxml"));
        URL location = getClass().getResource("TextEditor.fxml");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = (Parent) fxmlLoader.load(location.openStream());

        Scene scene = new Scene(root);
        stage.setTitle("Text Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        // Prompt user to save any changes made before exiting application
        if (TextEditorController.isChanged) {
            // Make reference to Controller class to implement 'alertDialog'
            ((TextEditorController) fxmlLoader.getController()).alertDialog();
        }
    }
}
