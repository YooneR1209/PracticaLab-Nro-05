package controller.Dao.servicies;

import controller.tda.list.LinkedList;
import models.Familia;
import controller.Dao.FamiliaDao;

public class FamiliaServicies {
    private FamiliaDao obj;

    public FamiliaServicies() { // Constructor de la clase
        obj = new FamiliaDao(); // Instancia un objeto de la clase FamiliaDao
    }

    public Boolean update() throws Exception { // Guarda la variable familia en la lista de objetos
        return obj.update(); // Invoca el método save() de la clase FamiliaDao
    }

    public Boolean save() throws Exception { // Guarda la variable familia en la lista de objetos
        return obj.save(); // Invoca el método save() de la clase FamiliaDao
    }

    public LinkedList<Familia> listAll() { // Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll(); // Invoca el método getlistAll() de la clase FamiliaDao

    }

    public Familia getFamilia() { // Obtiene la familia
        return obj.getFamilia(); // Invoca el método getFamilia() de la clase FamiliaDao
    }

    public void setFamilia(Familia familia) { // Recibe un objeto Familia
        obj.setFamilia(familia); // Invoca el método setFamilia() de la clase FamiliaDao y envía el objeto
                                 // Familia
    }

    public Familia get(Integer id) throws Exception { // Obtiene un objeto Familia por su id
        return obj.get(id); // Invoca el método get() de la clase FamiliaDao y envía el id
    }

    public Boolean delete(int index) throws Exception { // Elimina un objeto Familia por su índice
        return obj.delete(index); // Invoca el método delete() de la clase FamiliaDao y envía el índice
    }

    public int contarFamiliasConGenerador() {
        return obj.contarFamiliasConGenerador();
    }

    public LinkedList<Familia> order(String attribute, Integer type, Integer metodo) {
        return obj.order(attribute, type, metodo);
    }

    public LinkedList<Familia> buscar_Apellido_Paterno(String texto) {
        return obj.buscar_Apellido_Paterno_Binaria(texto);
    }

    public LinkedList<Familia> buscar_Apellido_Materno(String texto){
        // return obj.buscar_Apellido_Materno(texto);
        return obj.buscar_Apellido_Materno_Binaria(texto);
    }

    public LinkedList<Familia> buscar_Canton(String texto){
        return obj.buscar_Canton_LB(texto);
    }

    public Familia buscar_Id(Integer id) {
        return obj.buscar_Id_Binaria(id);
    }

    public LinkedList<Familia> buscar_Integrantes(Integer integrantes) {
        return obj.buscar_Integrantes_LinealBinaria(integrantes);
    }
    
    public Familia buscar_Integrantes_Binario(Integer integrantes) {
        return obj.buscar_Integrantes_Binaria(integrantes);
    }

    //PRUEBA BUSQUEDA
    public LinkedList<Familia> buscar_Integrantes_Prueba(LinkedList<Familia> lista, Integer integrantes) {
        return obj.buscar_Integrantes_LinealBinaria_P(lista, integrantes);
    }

    public LinkedList<Familia> buscar_Integrantes_Prueba_Lineal(LinkedList<Familia> lista, Integer integrantes) {
        return obj.buscar_Integrantes_LinealBinaria_PL(lista, integrantes);
    }

    public LinkedList<Familia> duplicar_LinkedList(LinkedList<Familia> lista) {
        return obj.duplicateLinkedList(lista);
    }

    
}
