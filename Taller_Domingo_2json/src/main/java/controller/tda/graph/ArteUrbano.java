package controller.tda.graph;

public class ArteUrbano {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String authors;
    private Double lng;
    private Double lat;
    private Integer year;
    private Boolean isMuaral;

    public Boolean getIsMuaral() {
        return isMuaral;
    }

    public void setIsMuaral(Boolean isMuaral) {
        this.isMuaral = isMuaral;
    }

    public Integer getId() {
        return id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
