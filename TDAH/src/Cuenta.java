public class Cuenta {
    private int numero;
    private String nombre;
    private String contraseña;

    // Constructor vacío
    public Cuenta() {
    }

    // Constructor con parámetros
    public Cuenta(int numero, String nombre, String contraseña) {
        this.numero = numero;
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    // Getters y Setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
