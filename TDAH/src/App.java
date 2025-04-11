import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        
        
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setTitle("B.E.E.S.");
        primaryStage.setScene(scene);

        primaryStage.setMaximized(true);

        primaryStage.show();
        
        
    }

    public static void main(String[] args) {
        launch(args); // Iniciar la aplicación JavaFX
    }
}
