import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

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

    // Acción para el botón "Finalizar" (Registro)
    @FXML
    private void registrarCuenta() {
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
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Perfiles.fxml"));
            Stage stage = (Stage) nombrePadreTutor.getScene().getWindow();
            Scene scene = new Scene(root);
            
            // Agregar el CSS a la nueva escena
            String css = getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setScene(scene);
            
            // Ajustar el tamaño de la ventana
            stage.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth());
            stage.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight());
            stage.setMaximized(true);
            
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al cargar Perfiles.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Acción para el botón "Iniciar sesión"
    @FXML
    private void iniciarSesion() {
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
        } else {
            mostrarAlerta("Error", "Credenciales incorrectas. Verifica tu número de teléfono y contraseña.");
        }
    }

    @FXML
    private void irAIniciarSesion(ActionEvent event) {
        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("IncioSesion.fxml"));
        

            // Obtener el escenario actual a partir del evento
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Crear una nueva escena con el archivo cargado
            Scene scene = new Scene(root);

            // Establecer la nueva escena en el escenario
            stage.setScene(scene);

            // Ajustar manualmente el tamaño de la ventana para que esté maximizada
            stage.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth());
            stage.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight());
            stage.setMaximized(true);

            stage.show();
        } catch (IOException e) {
            System.out.println("Error al cargar IncioSesion.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void irARegistro(ActionEvent event) {
        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        

            // Obtener el escenario actual a partir del evento
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Crear una nueva escena con el archivo cargado
            Scene scene = new Scene(root);

            // Establecer la nueva escena en el escenario
            stage.setScene(scene);

            // Ajustar manualmente el tamaño de la ventana para que esté maximizada
            stage.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth());
            stage.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight());
            stage.setMaximized(true);

            stage.show();
        } catch (IOException e) {
            System.out.println("Error al cargar Main.fxml: " + e.getMessage());
            e.printStackTrace();
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
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
