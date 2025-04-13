import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;  // Add this import
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.stage.Modality;
import java.net.URL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controllers {

    @FXML private TextField nombrePadreTutor; // Campo para el nombre del padre o tutor
    @FXML private TextField numeroTelefono;  // Campo para el número de teléfono
    @FXML private PasswordField contraseña;  // Campo para la contraseña
    @FXML private Hyperlink linkIniciarSesion; // Hipervínculo para iniciar sesión

    @FXML private TextField log_numeroTelefono; // Campo para el número de teléfono en inicio de sesión
    @FXML private PasswordField log_contraseña; // Campo para la contraseña en inicio de sesión
    @FXML private Button btnIniciarsesion; // Botón para iniciar sesión

    @FXML private GridPane itemsContainer;
    @FXML private Label monedas; // Para mostrar el saldo actual
    @FXML private Label selectedItemName;
    @FXML private Button btnEquipar;
    @FXML private ImageView characterPreview;

    @FXML private GridPane closetGrid;

    private static Niño niño;
    private static List<Producto> productos;
    private Producto selectedItem;

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int TELEFONO_LENGTH = 10;

    // Lista de cuentas predefinidas
    private List<Cuenta> cuentas = new ArrayList<>();

    // Constructor
    public Controllers() {
        // Agregar cuentas predefinidas para pruebas
        cuentas.add(new Cuenta(1234567890, "Juan Pérez", "12345678"));
        cuentas.add(new Cuenta(1234500000, "María López", "abcdef12"));
    }

    public static void inicializarNiño() {
        if (niño == null) {
            niño = new Niño("Usuario");
        }
        if (productos == null) {
            inicializarProductos();
        }
    }

    private static void inicializarProductos() {
        productos = new ArrayList<>();
        productos.add(new Producto(1, "Sombrero", 100));
        productos.add(new Producto(2, "Skin 2", 200));
        productos.add(new Producto(3, "Skin 3", 300));
        productos.add(new Producto(4, "Skin 4", 400));
        productos.add(new Producto(5, "Skin 5", 500));
        productos.add(new Producto(6, "Skin 6", 600));
        productos.add(new Producto(7, "Skin 7", 700));
        productos.add(new Producto(8, "Skin 8", 800));
    }

    @FXML
    public void initialize() {
        System.out.println("Initializing controller...");
        
        // Initialize products if needed
        if (productos == null) {
            inicializarProductos();
        }

        // Initialize child if needed
        if (niño == null) {
            niño = new Niño("Usuario");
        }

        // Update UI elements
        if (monedas != null) {
            monedas.setText(String.valueOf(niño.getMonedas()));
        }

        if (itemsContainer != null) {
            configureItemsContainer();
            mostrarProductos();
        }

        if (closetGrid != null) {
            configureClosetGrid();
            mostrarProductosComprados();
        }
    }

    private void configureItemsContainer() {
        itemsContainer.getColumnConstraints().clear();
        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.SOMETIMES);
            column.setMinWidth(200);
            column.setPrefWidth(200);
            itemsContainer.getColumnConstraints().add(column);
        }
    }

    private void configureClosetGrid() {
        closetGrid.getColumnConstraints().clear();
        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.SOMETIMES);
            column.setMinWidth(150);
            column.setPrefWidth(150);
            closetGrid.getColumnConstraints().add(column);
        }
    }

    private void mostrarProductos() {
        if (itemsContainer == null) {
            System.err.println("itemsContainer is null in mostrarProductos()");
            return;
        }

        try {
            // Clear existing items
            itemsContainer.getChildren().clear();

            // Create and add item cards to grid
            for (int i = 0; i < productos.size(); i++) {
                int row = i / 4;    // 4 columns per row
                int col = i % 4;    // Get column position
                
                StackPane itemCard = crearTarjetaProducto(productos.get(i));
                itemsContainer.add(itemCard, col, row);
            }
        } catch (Exception e) {
            System.err.println("Error showing products: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private StackPane crearTarjetaProducto(Producto producto) {
        StackPane itemCard = new StackPane();
        itemCard.getStyleClass().add("item-card");
        itemCard.setUserData(producto);
        
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        
        Label nombreLabel = new Label(producto.getNombre());
        nombreLabel.getStyleClass().add("product-name");
        
        HBox priceTag = new HBox(5);
        priceTag.setAlignment(Pos.CENTER);
        
        Label coinIcon = new Label("●");
        coinIcon.getStyleClass().add("coin-icon");
        
        Label priceValue = new Label(String.valueOf(producto.getPrecio()));
        priceValue.getStyleClass().add("price-value");
        
        priceTag.getChildren().addAll(coinIcon, priceValue);
        content.getChildren().addAll(nombreLabel, priceTag);
        
        if (producto.isComprado()) {
            itemCard.getStyleClass().add("item-purchased");
        }
        
        itemCard.getChildren().add(content);
        itemCard.setOnMouseClicked(this::mostrarDialogoCompra);
        
        return itemCard;
    }

    @FXML
    private void mostrarDialogoCompra(MouseEvent event) {
        try {
            // Change VBox to StackPane in the cast
            StackPane itemCard = (StackPane) event.getSource();
            Producto producto = (Producto) itemCard.getUserData();
            
            if (producto == null) {
                mostrarAlerta("Error", "No se pudo obtener la información del producto.");
                return;
            }

            // Load the dialog FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CompraDialog.fxml"));
            Parent dialogContent = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Confirmar compra");
            
            // Setup dialog content
            Label nombreProducto = (Label) dialogContent.lookup("#nombreProducto");
            Label precioProducto = (Label) dialogContent.lookup("#precioProducto");
            Label saldoActual = (Label) dialogContent.lookup("#saldoActual");
            
            nombreProducto.setText(producto.getNombre());
            precioProducto.setText(String.valueOf(producto.getPrecio()));
            saldoActual.setText("Saldo actual: " + niño.getMonedas());
            
            // Setup buttons
            Button btnComprar = (Button) dialogContent.lookup("#btnComprar");
            Button btnCancelar = (Button) dialogContent.lookup("#btnCancelar");
            
            btnComprar.setOnAction(e -> confirmarCompra(producto, dialogStage));
            btnCancelar.setOnAction(e -> dialogStage.close());
            
            Scene dialogScene = new Scene(dialogContent);
            dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            // Center the dialog on screen
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            dialogStage.setWidth(400);
            dialogStage.setHeight(300);
            dialogStage.setX((bounds.getWidth() - 400) / 2);
            dialogStage.setY((bounds.getHeight() - 300) / 2);
            
            dialogStage.setScene(dialogScene);
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo mostrar el diálogo de compra: " + e.getMessage());
        }
    }

    @FXML
    private void confirmarCompra(Producto producto, Stage dialogStage) {
        if (niño.getMonedas() >= producto.getPrecio()) {
            niño.comprarProducto(producto);
            monedas.setText(String.valueOf(niño.getMonedas()));
            dialogStage.close();
            mostrarAlerta("Éxito", "¡Producto comprado con éxito!");
        } else {
            mostrarAlerta("Error", "No tienes suficientes monedas");
        }
    }

    private void mostrarProductosComprados() {
        closetGrid.getChildren().clear();
        int col = 0;
        int row = 0;
        final int ITEMS_PER_ROW = 3;

        for (Producto producto : niño.getProductosComprados()) {
            StackPane itemSlot = crearSlotItem(producto);
            closetGrid.add(itemSlot, col, row);
            
            col++;
            if (col == ITEMS_PER_ROW) {
                col = 0;
                row++;
            }
        }
    }

    private StackPane crearSlotItem(Producto producto) {
        StackPane slot = new StackPane();
        slot.getStyleClass().addAll("item-slot", "closet-item");
        slot.setMinSize(100, 100);
        slot.setPrefSize(120, 120);
        
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        
        Label nameLabel = new Label(producto.getNombre());
        nameLabel.getStyleClass().add("item-name");
        
        content.getChildren().addAll(nameLabel);
        slot.getChildren().add(content);
        
        // Add hover effect and click handler
        slot.setOnMouseClicked(e -> seleccionarItem(producto, slot));
        
        if (producto.isSeleccionado()) {
            slot.getStyleClass().add("equipped");
        }
        
        return slot;
    }

    private void seleccionarItem(Producto producto, StackPane slot) {
        selectedItem = producto;
        selectedItemName.setText(producto.getNombre());
        btnEquipar.setDisable(false);
        
        // Update visual selection
        closetGrid.getChildren().forEach(node -> 
            node.getStyleClass().remove("equipped"));
        slot.getStyleClass().add("equipped");
    }

    @FXML
    private void equiparItem() {
        if (selectedItem != null) {
            niño.equiparProducto(selectedItem);
            mostrarProductosComprados();
            actualizarPreview();
        }
    }

    private void actualizarPreview() {
        // Update character preview based on equipped item
        if (niño.getProductoEquipado() != null) {
            selectedItemName.setText(niño.getProductoEquipado().getNombre());
            // Here you would load the appropriate image:
            // characterPreview.setImage(new Image(getClass().getResourceAsStream("/images/" + niño.getProductoEquipado().getImageName())));
        }
    }

    // Método común para cambiar escenas y aplicar CSS
    private void cambiarEscena(String fxml, Event event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            
            // Agregar CSS base
            String mainCss = getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(mainCss);
            
            // Agregar CSS específico si existe
            String specificCss = fxml.replace(".fxml", ".css");
            URL specificCssUrl = getClass().getResource(specificCss);
            if (specificCssUrl != null) {
                scene.getStylesheets().add(specificCssUrl.toExternalForm());
            }

            // Obtener dimensiones de la pantalla excluyendo la barra de tareas
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            
            // Configurar la ventana
            stage.setScene(scene);
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());
            
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al cargar " + fxml + ": " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la pantalla.");
        }
    }

    // Acción para el botón "Finalizar" (Registro)
    @FXML
    private void registrarCuenta(ActionEvent event) {  // Add event parameter
        String nombre = nombrePadreTutor.getText().trim();
        String telefono = numeroTelefono.getText().trim();
        String pass = contraseña.getText().trim();

        // Validación de campos
        if (!validarNombre(nombre)) return;
        if (!validarTelefono(telefono)) return;
        if (!validarPassword(pass)) return;

        // Crear una nueva cuenta y agregarla a la lista
        cuentas.add(new Cuenta(cuentas.size() + 1, nombre, pass));
        System.out.println("Cuenta registrada: " + nombre + ", Tel: " + telefono);
        mostrarAlerta("Éxito", "Cuenta registrada correctamente.");
        
        cambiarEscena("Perfiles.fxml", event);  // Use the event parameter
    }

    // Acción para el botón "Iniciar sesión"
    @FXML
    private void iniciarSesion(ActionEvent event) {  // Add event parameter
        String telefono = log_numeroTelefono.getText().trim();
        String pass = log_contraseña.getText().trim();

        // Validar que los campos no estén vacíos
        if (telefono.isEmpty() || pass.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        // Validar que el número de teléfono tenga el formato correcto
        if (!telefono.matches("\\d{" + TELEFONO_LENGTH + "}")) {
            mostrarAlerta("Error", "El número de teléfono debe tener exactamente " + TELEFONO_LENGTH + " dígitos.");
            return;
        }

        // Convertir el teléfono a número para comparar
        int numeroTelefono;
        try {
            numeroTelefono = Integer.parseInt(telefono);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El número de teléfono debe contener solo dígitos.");
            return;
        }

        // Buscar la cuenta en la lista de cuentas
        Cuenta cuentaEncontrada = null;
        for (Cuenta cuenta : cuentas) {
            System.out.println("Comparando con cuenta: " + cuenta.getNumero());
            if (cuenta.getNumero() == numeroTelefono && cuenta.getContraseña().equals(pass)) {
                cuentaEncontrada = cuenta;
                break;
            }
        }

        // Verificar si se encontró la cuenta
        if (cuentaEncontrada != null) {
            mostrarAlerta("Éxito", "Inicio de sesión exitoso. Bienvenido, " + cuentaEncontrada.getNombre() + "!");
            cambiarEscena("Perfiles.fxml", event);  // Use the event parameter
        } else {
            mostrarAlerta("Error", "Credenciales incorrectas. Verifica tu número de teléfono y contraseña.");
        }
    }

    @FXML
    private void irAIniciarSesion(ActionEvent event) {
        cambiarEscena("IncioSesion.fxml", event);
    }
    
    @FXML
    private void irARegistro(ActionEvent event) {
        cambiarEscena("Main.fxml", event);
    }

    @FXML
    private void irAPerfilNiño(Event event) {
        cambiarEscena("Menuniño.fxml", event);
    }

    @FXML
    private void irATienda(ActionEvent event) {
        cambiarEscena("Tienda.fxml", event);
    }

    @FXML
    private void volverAPerfiles(ActionEvent event) {
        cambiarEscena("Perfiles.fxml", event);
    }

    @FXML
    private void irAPerfilDelNiño(Event event) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Perfilniño.fxml"));
            Parent root = loader.load();
            
            // Get current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Create new scene
            Scene scene = new Scene(root);
            
            // Add stylesheets
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            // Configure stage
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setScene(scene);
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());
            
            // Initialize products if not already done
            if (productos == null) {
                inicializarProductos();
            }
            
            stage.show();
            
        } catch (IOException e) {
            System.err.println("Error loading Perfilniño.fxml: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el perfil del niño.");
        }
    }

    @FXML
    public void irAMenuNiño(ActionEvent event) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menuniño.fxml"));
            Parent root = loader.load();
            
            // Get current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Create and configure scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            // Configure and show stage
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            System.err.println("Error loading Menuniño.fxml: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el menú.");
        }
    }

    @FXML
    public void irARacha(ActionEvent event) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Racha.fxml"));
            Parent root = loader.load();
            
            // Get current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Create and configure scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            // Configure stage
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setScene(scene);
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());
            
            stage.show();
            
        } catch (IOException e) {
            System.err.println("Error loading Racha.fxml: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la pantalla de racha.");
        }
    }

    // Validar el nombre
    private boolean validarNombre(String nombre) {
        if (nombre.isEmpty()) {
            mostrarAlerta("Campo vacío", "El nombre no puede estar vacío.");
            return false;
        }
        if (nombre.length() < 3) {
            mostrarAlerta("Nombre muy corto", "Introduce tu nombre completo.");
            return false;
        }
        return true;
    }

    // Validar el número de teléfono
    private boolean validarTelefono(String telefono) {
        if (!telefono.matches("\\d{" + TELEFONO_LENGTH + "}")) {
            mostrarAlerta("Teléfono inválido", "Debes ingresar un número de 10 dígitos.");
            return false;
        }
        return true;
    }

    // Validar la contraseña
    private boolean validarPassword(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            mostrarAlerta("Contraseña insegura", "La contraseña debe tener al menos " + MIN_PASSWORD_LENGTH + " caracteres.");
            return false;
        }
        return true;
    }

    // Mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        
        // Get the Stage
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        
        // Add the stylesheet
        alert.getDialogPane().getStylesheets().add(
            getClass().getResource("application.css").toExternalForm()
        );
        
        // Add style classes
        alert.getDialogPane().getStyleClass().add("dialog-pane");
        
        // Center on screen
        stage.setOnShown(e -> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        });
        
        alert.showAndWait();
    }
}
