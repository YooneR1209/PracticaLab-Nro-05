package controller.Dao.graphDao;

import controller.tda.graph.ArteUrbano;
import controller.tda.graph.GraphLabelNoDirect;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class ArteUrbanoDao extends AdapterDao<ArteUrbano> {
    private ArteUrbano arteUrbano = new ArteUrbano();
    private LinkedList<ArteUrbano> listAll;
    private Gson g = new Gson();

    public ArteUrbanoDao() {
        super(ArteUrbano.class);
    }

    public ArteUrbano getArteUrbano() { // Obtiene la arteUrbano
        if (arteUrbano == null) {
            arteUrbano = new ArteUrbano(); // En caso de que la arteUrbano sea nula, crea una nueva instancia de
                                           // ArteUrbano
        }
        return this.arteUrbano; // Devuelve la arteUrbano
    }

    public void setArteUrbano(ArteUrbano arteUrbano) { // Establece la arteUrbano con un objeto ArteUrbano
        this.arteUrbano = arteUrbano; // Asigna el objeto ArteUrbano a la variable arteUrbano
    }

    public LinkedList<ArteUrbano> getlistAll() { // Obtiene la lista de objetos
        if (listAll == null) { // Si la lista es nula
            this.listAll = listAll(); // Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; // Devuelve la lista de objetos de la variable listAll
    }

    // public Boolean save(GraphLabelNoDirect arteUrbano) throws Exception {
    // persistAR(arteUrbano); // Guarda el grafo en el archivo JSON
    // return true; // Retorna verdadero si se guardó correctamente
    // }

    public Boolean save() throws Exception { // Guarda la variable arteUrbano en la lista de objetos
        Integer id = 0;
        if (!getlistAll().isEmpty()) {
            id = getlistAll().getLast().getId(); // Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo id
        }
        arteUrbano.setId(id + 1); // Asigna el id a arteUrbano
        this.persist(this.arteUrbano); // Guarda la arteUrbano en la lista de objetos LinkedList y en el archivo JSON
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se guardó correctamente
    }

    public Boolean update() throws Exception { // Actualiza el nodo Lote en la lista de objetos
        this.getlistAll();
        this.mergeA(getArteUrbano(), recuperoIndex(arteUrbano.getId())); // Envia el arteUrbano a actualizar con su
                                                                         // index
        System.out.println("valor" + recuperoIndex(arteUrbano.getId()));
        this.listAll = listAll(); // Actualiza la lista de objetos
        System.out.println("listaaa = " + listAll.toString());
        return true;
    }

    public Boolean delete(int index) throws Exception { // Elimina un objeto Lote por su índice
        Gson g = new Gson();
        System.out.println("intentamos eliminar el elemento con id " + index);
        this.listAll = listAll();
        System.out.println("lista:" + this.listAll.toString());
        ;
        this.listAll.remove(recuperoIndex(index));
        String info = g.toJson(this.listAll.toArray()); // Convierte la lista en un String JSON
        super.saveFile(info);
        return true; // Retorna verdadero si se eliminó correctamente
    }

    public void persistAR(GraphLabelNoDirect object) throws Exception {
        System.out.println("Persisting object: " + object); // Imprime el objeto a guardar

        // Convierte el objeto ArteUrbano directamente a JSON
        String json = g.toJson(object);
        System.out.println("Escribiendo datos al archivo: " + json); // Imprime el JSON

        // Guarda el JSON en el archivo
        this.saveFileA(json);
    }

    public Integer recuperoIndex(Integer id) {
        ArteUrbano[] lista = listAll.toArray();
        Integer count = 0;
        System.out.println("Entramos acá " + id);
        try {
            for (int i = 0; i < lista.length; i++) {
                if (lista[i].getId() != id) {
                    count++;
                } else if (lista[i].getId() == id) {
                    return count;
                }
            }
        } catch (Exception e) {
            System.out.println("borrado fallido");
        }
        return null;
    }

    private void saveFileA(String jsonData) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonData); // Escribe el JSON en el archivo
            System.out.println("Archivo guardado en: " + filePath);
        }
    }

    // public Boolean update() throws Exception { // Actualiza el nodo ArteUrbano
    // en la lista de objetos
    // this.getlistAll();
    // this.mergeA(getArteUrbano(), recuperoIndex(arteUrbano.getId())); // Envia
    // el arteUrbano a actualizar con su
    // // index
    // System.out.println("valor" + recuperoIndex(arteUrbano.getId()));
    // this.listAll = listAll(); // Actualiza la lista de objetos
    // System.out.println("listaaa = " + listAll.toString());
    // return true;
    // }

    // public Boolean delete(int index) throws Exception { // Elimina un objeto
    // ArteUrbano por su índice
    // Gson g = new Gson();
    // System.out.println("intentamos eliminar el elemento con id " + index);
    // this.listAll = listAll();
    // System.out.println("lista:" + this.listAll.toString());
    // ;
    // this.listAll.remove(recuperoIndex(index));
    // String info = g.toJson(this.listAll.toArray()); // Convierte la lista en un
    // String JSON
    // super.saveFile(info);
    // return true; // Retorna verdadero si se eliminó correctamente
    // }

    // public Integer recuperoIndex(Integer id) {
    // ArteUrbano[] lista = listAll.toArray();
    // Integer count = 0;
    // System.out.println("Entramos acá " + id);
    // try {
    // for (int i = 0; i < lista.length; i++) {
    // if (lista[i].getId() != id) {
    // count++;
    // } else if (lista[i].getId() == id) {
    // return count;
    // }
    // }
    // } catch (Exception e) {
    // System.out.println("borrado fallido");
    // }
    // return null;
    // }

    public LinkedList<ArteUrbano> order(String attribute, Integer type, Integer metodo) {
        try {
            getlistAll();
            System.out.println("Lista antes de ordenar " + listAll.toString());
            switch (metodo) {
                case 1:
                    System.out.println("Método de ordenación Lineal ");
                    return this.listAll.order(attribute, type);

                case 2:
                    System.out.println("Método de ordenación shellSort ");
                    return this.listAll.shellSort(attribute, type);

                case 3:
                    System.out.println("Método de ordenación quickSort ");
                    return this.listAll.quickSort(attribute, type);

                default:
                    System.out.println("Método de ordenación mergeSort ");
                    return this.listAll.mergeSort(attribute, type);
            }

        } catch (Exception e) {
            return null;
        }
    }

}
