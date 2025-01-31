package models;

public class Almacen {
    private Integer id; // Identificador único del almacén
    private String nombre; // Nombre descriptivo del almacén
    private double latitud; // Coordenada de latitud
    private double longitud; // Coordenada de longitud
    private Integer capacidad; // Capacidad máxima de almacenamiento (en unidades de productos)

    public Almacen(Integer id, String nombre, double latitud, double longitud, Integer capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.capacidad = capacidad;
    }

    public Almacen() {
    }

    // Métodos getter y setter
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos getter y setter
    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public String toString() {
        return "Almacen{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", capacidad=" + capacidad +
                '}';
    }
}
