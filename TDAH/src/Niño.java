import java.util.ArrayList;
import java.util.List;

public class Niño {
    private String nombre;
    private int monedas;
    private List<Producto> productosComprados;
    private Producto productoEquipado;

    public Niño(String nombre) {
        this.nombre = nombre;
        this.monedas = 1000; // Initial coins
        this.productosComprados = new ArrayList<>();
    }

    public void comprarProducto(Producto producto) {
        if (monedas >= producto.getPrecio() && !productosComprados.contains(producto)) {
            monedas -= producto.getPrecio();
            producto.setComprado(true);
            productosComprados.add(producto);
        }
    }

    public void equiparProducto(Producto producto) {
        if (productosComprados.contains(producto)) {
            if (productoEquipado != null) {
                productoEquipado.setSeleccionado(false);
            }
            producto.setSeleccionado(true);
            productoEquipado = producto;
        }
    }

    public String getNombre() { return nombre; }
    public int getMonedas() { return monedas; }
    public List<Producto> getProductosComprados() { return productosComprados; }
    public Producto getProductoEquipado() { return productoEquipado; }
}