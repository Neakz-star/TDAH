import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import java.io.File;

public class App extends Application {
    private static final String APP_TITLE = "B.E.E.S.";
    private static final String FXML_PATH = "Main.fxml";
    private static final String CSS_PATH = "application.css";
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Create images directory if it doesn't exist
            File imagesDir = new File("src/images");
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
            }
            
            // Load FXML with specific version
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(FXML_PATH));
            Parent root = loader.load();
            
            // Configure scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            
            // Configure stage
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(scene);
            
            // Set window size based on screen
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            
            primaryStage.setX(bounds.getMinX());
            primaryStage.setY(bounds.getMinY());
            primaryStage.setWidth(bounds.getWidth());
            primaryStage.setHeight(bounds.getHeight());
            
            // Show maximized window
            primaryStage.setMaximized(true);
            primaryStage.show();
            
        } catch (Exception e) {
            System.err.println("Error initializing application: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
