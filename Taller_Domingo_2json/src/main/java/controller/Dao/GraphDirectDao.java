package controller.Dao;

import controller.tda.graph.GraphDirect;
import controller.tda.graph.GraphLabelNoDirect;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class GraphDirectDao extends AdapterDao<GraphDirect> {
    private GraphDirect graphDirect = new GraphDirect();
    private LinkedList<GraphDirect> listAll;
    private Gson g = new Gson();

    public GraphDirectDao() {
        super(GraphDirect.class);
    }

    public GraphDirect getGraphDirect() { // Obtiene la graphDirect
        if (graphDirect == null) {
            graphDirect = new GraphDirect(); // En caso de que la graphDirect sea nula, crea una nueva instancia de
                                             // GraphDirect
        }
        return this.graphDirect; // Devuelve la graphDirect
    }

    public void setGraphDirect(GraphDirect graphDirect) { // Establece la graphDirect con un objeto GraphDirect
        this.graphDirect = graphDirect; // Asigna el objeto GraphDirect a la variable graphDirect
    }

    public LinkedList<GraphDirect> getlistAll() { // Obtiene la lista de objetos
        if (listAll == null) { // Si la lista es nula
            this.listAll = listAll(); // Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; // Devuelve la lista de objetos de la variable listAll
    }

    public Boolean save(GraphLabelNoDirect graphDirect) throws Exception {
        persistAR(graphDirect); // Guarda el grafo en el archivo JSON
        return true; // Retorna verdadero si se guardó correctamente
    }

    public void persistAR(GraphLabelNoDirect object) throws Exception {
        System.out.println("Persisting object: " + object); // Imprime el objeto a guardar

        // Convierte el objeto GraphDirect directamente a JSON
        String json = g.toJson(object);
        System.out.println("Escribiendo datos al archivo: " + json); // Imprime el JSON

        // Guarda el JSON en el archivo
        this.saveFileA(json);
    }

    private void saveFileA(String jsonData) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonData); // Escribe el JSON en el archivo
            System.out.println("Archivo guardado en: " + filePath);
        }
    }

    // public Boolean update() throws Exception { // Actualiza el nodo GraphDirect
    // en la lista de objetos
    // this.getlistAll();
    // this.mergeA(getGraphDirect(), recuperoIndex(graphDirect.getId())); // Envia
    // el graphDirect a actualizar con su
    // // index
    // System.out.println("valor" + recuperoIndex(graphDirect.getId()));
    // this.listAll = listAll(); // Actualiza la lista de objetos
    // System.out.println("listaaa = " + listAll.toString());
    // return true;
    // }

    // public Boolean delete(int index) throws Exception { // Elimina un objeto
    // GraphDirect por su índice
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
    // GraphDirect[] lista = listAll.toArray();
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

    public LinkedList<GraphDirect> order(String attribute, Integer type, Integer metodo) {
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
