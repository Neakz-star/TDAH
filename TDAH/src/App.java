import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        
        // Configurar la escena
        Scene scene = new Scene(root);

        //Agregamos el css
        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        // Configurar el escenario (ventana)
        primaryStage.setTitle("B.E.E.S.");
        primaryStage.setScene(scene);

        primaryStage.setMaximized(true);

        primaryStage.show();
        
        
    }

    public static void main(String[] args) {
        launch(args); // Iniciar la aplicación JavaFX
    }
}
