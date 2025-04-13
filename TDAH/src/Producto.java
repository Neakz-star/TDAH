public class Producto {
    private int id;
    private String nombre;
    private int precio;
    private boolean comprado;
    private boolean seleccionado;

    public Producto(int id, String nombre, int precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.comprado = false;
        this.seleccionado = false;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getPrecio() { return precio; }
    public boolean isComprado() { return comprado; }
    public void setComprado(boolean comprado) { this.comprado = comprado; }
    public boolean isSeleccionado() { return seleccionado; }
    public void setSeleccionado(boolean seleccionado) { this.seleccionado = seleccionado; }
}
