package controller.Dao;

import models.Almacen;
import models.AdyacenciaJSON;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import controller.Dao.implement.AdapterDao;
import controller.tda.graph.Adyacencia;
import controller.tda.graph.Graph;
import controller.tda.graph.GraphLabelDirect;
import controller.tda.graph.GraphLabelNoDirect;
import controller.tda.list.LinkedList;
import com.google.gson.GsonBuilder;

public class AlmacenDao extends AdapterDao<Almacen> {
    private Almacen almacen = new Almacen();
    private LinkedList<Almacen> listAll;
    private GraphLabelDirect graph;

    public AlmacenDao() {
        super(Almacen.class);
    }

    public Almacen getAlmacen() { // Obtiene la almacen
        if (almacen == null) {
            almacen = new Almacen(); // En caso de que la almacen sea nula, crea una nueva instancia de Almacen
        }
        return this.almacen; // Devuelve la almacen
    }

    public void setAlmacen(Almacen almacen) { // Establece la almacen con un objeto Almacen
        this.almacen = almacen; // Asigna el objeto Almacen a la variable almacen
    }

    public LinkedList<Almacen> getlistAll() { // Obtiene la lista de objetos
        if (listAll == null) { // Si la lista es nula
            this.listAll = listAll(); // Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; // Devuelve la lista de objetos de la variable listAll
    }

    public Boolean save() throws Exception { // Guarda la variable almacen en la lista de objetos
        Integer id = 0;
        if (!getlistAll().isEmpty()) {
            id = (getlistAll().getLast()).getId(); // Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo id
        }
        almacen.setId(id + 1); // Asigna el id a almacen
        this.persist(this.almacen); // Guarda la almacen en la lista de objetos LinkedList y en el archivo JSON
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se guardó correctamente
    }

    public Boolean update() throws Exception { // Actualiza el nodo Almacen en la lista de objetos
        this.getlistAll();
        this.mergeA(getAlmacen(), recuperoIndex(almacen.getId())); // Envia el almacen a actualizar con su index
        System.out.println("valor" + recuperoIndex(almacen.getId()));
        this.listAll = listAll(); // Actualiza la lista de objetos
        System.out.println("listaaa = " + listAll.toString());
        return true;
    }

    public Boolean delete(int index) throws Exception { // Elimina un objeto Almacen por su índice
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

    public Integer recuperoIndex(Integer id) {
        Almacen[] lista = listAll.toArray();
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

    public LinkedList<Almacen> order(String attribute, Integer type, Integer metodo) {
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

    public Almacen buscar_IdAlmacen(int id) {
        Almacen a = new Almacen();
        Almacen[] lista = listAll.toArray();

        if (!listAll.isEmpty()) {
            for (int i = 0; i < lista.length; i++) {
                System.out.println(lista[i].getId());
                if (lista[i].getId().intValue() == id) {
                    System.out.println("ENCONTRAMOOS");
                    a = lista[i];
                    break;
                }
            }
            return a;
        }
        return null;
    }

    public void createGraph() {
        System.out.println("entramos acá bro");
        LinkedList<Almacen> lista = listAll();
        if (!lista.isEmpty()) {
            graph = new GraphLabelDirect<>(lista.getSize(), Almacen.class);
            Almacen[] m = lista.toArray();
            for (int i = 0; i < m.length; i++) {
                this.graph.labelsVertices((i + 1), m[i]);
            }
            paintMap(this.graph);
            setAdyacencias(lista);
            this.graph.drawGraph();

        }

    }

    public void paintMap(Graph grafo) {
        try {
            FileWriter file = new FileWriter("resources" + File.separatorChar + "maps" + File.separatorChar + "map.js");
            String mapa = "";

            if (grafo instanceof GraphLabelDirect) {
                mapa = paint((GraphLabelDirect) grafo);
            } else if (grafo instanceof GraphLabelNoDirect) {
                mapa = paint((GraphLabelNoDirect) grafo);
            }

            file.write(mapa);
            file.flush();
            file.close();

        } catch (Exception e) {
            System.out.println("Error al crear el mapa: " + e);
        }
    }

    private String paint(GraphLabelDirect<Almacen> graph) {
        String map = "var osmUrl = 'https://tile.openstreetmap.org/{z}/{x}/{y}.png', " +
                "osmAttrib = '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a>'; " +
                "osm = L.tileLayer(osmUrl, {maxZoom: 15, attribution: osmAttrib}); " +
                "var map = L.map('map').setView([-3.996716943365505, -79.20174502581729], 15).addLayer(osm);";

        for (int i = 1; i <= graph.nro_Ver(); i++) {
            Almacen au = (Almacen) graph.getLabelL(i);
            map += "L.marker([" + au.getLongitud() + ", " + au.getLatitud() + "])" +
                    ".bindPopup('" + au.getNombre() + "')" +
                    ".openPopup(); ";
        }
        return map;
    }

    // private void loadGraph() {
    // createGraph();
    // try {
    // BufferedReader br = new BufferedReader(
    // new FileReader("media" + File.separatorChar + "graph_" +
    // Almacen.class.getSimpleName()));
    // String line = br.readLine();
    // while (line != null) {
    // String[] aux = line.split(",");
    // Integer a1 = Integer.parseInt(aux[0]);
    // Integer a2 = Integer.parseInt(aux[1]);
    // graph.add_edge(a1, a2,
    // distance((Almacen) graph.getLabelL(a1), (Almacen)
    // graph.getLabelL(a2)).floatValue());
    // line = br.readLine();
    // }
    // br.close();
    // graph.drawGraph();
    // } catch (Exception e) {
    // System.out.println("error en generar el grafo " + e);
    // }
    // }

    public void saveGraph() {
        if (graph != null) {
            try {
                FileWriter fw = new FileWriter(
                        "media" + File.separatorChar + "graph_" + Almacen.class.getSimpleName() + ".txt");
                for (int i = 1; i < graph.nro_Ver(); i++) {
                    Almacen al = (Almacen) graph.getLabelL(i);

                    LinkedList<Adyacencia> lista = graph.adyacenciasL(al);
                    if (!lista.isEmpty()) {

                        Adyacencia[] matriz = lista.toArray();
                        for (int j = 0; j < matriz.length; j++) {
                            Adyacencia ad = matriz[j];
                            String ady = i + "," + ad.getDestination() + "\n";
                            fw.write(ady);
                            fw.flush();
                        }

                    }
                }
                fw.close();
            } catch (Exception e) {
                System.out.println("Error en guardar grafo " + e);
            }
        }
    }

    public void setAdyacencias(LinkedList<Almacen> lista) {
        LinkedList<AdyacenciaJSON> adyacencias = new LinkedList<>();

        try {
            for (int i = 0; i < lista.getSize(); i++) {
                Almacen almacenI = lista.get(i);

                // Generar un índice aleatorio diferente de i
                int randomIndex;
                do {
                    randomIndex = (int) (Math.random() * lista.getSize());
                } while (randomIndex == i); // Repetir hasta que randomIndex sea diferente de i

                Almacen almacenRandom = lista.get(randomIndex);

                double peso = calcularDistancia(almacenI.getLatitud(), almacenI.getLongitud(),
                        almacenRandom.getLatitud(), almacenRandom.getLongitud());

                AdyacenciaJSON adyacencia = new AdyacenciaJSON(almacenI.getId(), almacenRandom.getId(), peso);
                adyacencias.add(adyacencia);

                System.out.println(
                        "Peso entre " + almacenI.getId() + " y " + almacenRandom.getId() + ": " + peso + " km");
            }

            saveAdyacenciasToJSON(adyacencias);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAdyacenciasToJSON(LinkedList<AdyacenciaJSON> adyacencias) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            AdyacenciaJSON[] adyacenciasArray = adyacencias.toArray();
            String json = gson.toJson(adyacenciasArray);

            FileWriter fileWriter = new FileWriter("resources" + File.separatorChar + "adyacencias.json");
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();

            System.out.println("Adyacencias guardadas en adyacencias.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int RADIO_TIERRA = 6371; // Radio de la Tierra en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIO_TIERRA * c;
    }

    private Integer generate(Integer n) {
        return (int) ((Math.random() * n) + 1);
    }

    public LinkedList<AdyacenciaJSON> loadAdyacenciasFromJSON() {
        LinkedList<AdyacenciaJSON> adyacencias = new LinkedList<>();
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileReader reader = new FileReader("resources" + File.separatorChar + "adyacencias.json");
            AdyacenciaJSON[] adyacenciasArray = gson.fromJson(reader, AdyacenciaJSON[].class);
            reader.close();

            for (AdyacenciaJSON adyacencia : adyacenciasArray) {
                adyacencias.add(adyacencia);
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo JSON: " + e);
        }
        return adyacencias;
    }

    public void buildGraphFromAdyacencias(LinkedList<AdyacenciaJSON> adyacencias) {
        try {
            getlistAll();

            for (int i = 0; i < listAll.getSize(); i++) {
                Almacen almacen = listAll.get(i);
                graph.labelsVertices(almacen.getId(), almacen); // Asignar el almacén como etiqueta del vértice
            }

            // Agregar aristas al grafo
            for (int i = 0; i < adyacencias.getSize(); i++) {
                AdyacenciaJSON adyacencia = adyacencias.get(i); // Obtener la adyacencia en la posición i
                int origen = adyacencia.getOrigen();
                int destino = adyacencia.getDestino();
                double peso = adyacencia.getPeso();

                // Agregar la arista al grafo
                graph.add_edge(origen, destino, (float) peso);
            }

        } catch (Exception e) {
            System.out.println("Error en buildGraphFromAdy: " + e);
        }
    }

    public void bellmanFord(Almacen origen) {
        try {
            int V = graph.nro_Ver(); // Número de vértices
            double[] distancias = new double[V + 1]; // +1 porque los IDs empiezan en 1

            // Inicializar distancias
            for (int i = 1; i <= V; i++) {
                distancias[i] = Double.POSITIVE_INFINITY; // Infinito para todos los nodos
            }

            // Obtener el ID del nodo de origen
            Integer origenId = graph.getVerticeL(origen);
            if (origenId == null) {
                throw new Exception("El almacén no está registrado en el grafo.");
            }
            distancias[origenId] = 0.0; // La distancia al nodo de origen es 0

            for (int i = 1; i < V; i++) {
                for (int u = 1; u <= V; u++) {
                    LinkedList<Adyacencia> adyacentes = graph.adyacenciasL(graph.getLabelL(u));
                    if (!adyacentes.isEmpty()) {
                        Adyacencia[] ady = adyacentes.toArray();
                        for (Adyacencia a : ady) {
                            int v = a.getDestination();
                            double peso = a.getWeight();
                            if (distancias[u] != Double.POSITIVE_INFINITY && distancias[u] + peso < distancias[v]) {
                                distancias[v] = distancias[u] + peso;
                            }
                        }
                    }
                }
            }

            // Verificar ciclos negativos
            for (int u = 1; u <= V; u++) {
                LinkedList<Adyacencia> adyacentes = graph.adyacenciasL(graph.getLabelL(u));
                if (!adyacentes.isEmpty()) {
                    Adyacencia[] ady = adyacentes.toArray();
                    for (Adyacencia a : ady) {
                        int v = a.getDestination();
                        double peso = a.getWeight();
                        if (distancias[u] != Double.POSITIVE_INFINITY && distancias[u] + peso < distancias[v]) {
                            throw new Exception("El grafo contiene un ciclo negativo.");
                        }
                    }
                }
            }

            // Crear un mapa para almacenar los resultados
            Map<String, Object> resultados = new HashMap<>();
            resultados.put("origen", origen.toString());
            Map<String, String> distanciasMap = new HashMap<>();
            for (int i = 1; i <= V; i++) {
                if (distancias[i] == Double.POSITIVE_INFINITY) {
                    distanciasMap.put(graph.getLabelL(i).toString(), "INF");
                } else {
                    distanciasMap.put(graph.getLabelL(i).toString(), String.valueOf(distancias[i]));
                }
            }
            resultados.put("distancias", distanciasMap);

            // Convertir el mapa a JSON
            Gson gson = new Gson();
            String json = gson.toJson(resultados);

            // Escribir el JSON en un archivo
            String rutaCompleta = "/home/ariel/Documents/Taller_Domingo_LINKED_LIST/FRONT/static/js/bellmanFord.js";
            try (FileWriter file = new FileWriter(rutaCompleta)) {
                file.write(json);
                System.out.println("Resultados de Bellman-Ford guardados en " + rutaCompleta);
            } catch (IOException e) {
                System.out.println("Error al escribir el archivo JSON: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error en bellmanFord: " + e.getMessage());
        }
    }

    public void floydWarshall() {
        try {
            int V = graph.nro_Ver(); // Número de vértices
            double[][] distancias = new double[V + 1][V + 1]; // +1 porque los IDs empiezan en 1

            // Inicializar la matriz de distancias
            for (int i = 1; i <= V; i++) {
                for (int j = 1; j <= V; j++) {
                    if (i == j) {
                        distancias[i][j] = 0; // Distancia de un nodo a sí mismo es 0
                    } else {
                        distancias[i][j] = Double.POSITIVE_INFINITY; // Infinito para los demás
                    }
                }
            }

            // Llenar la matriz con las distancias conocidas
            for (int u = 1; u <= V; u++) {
                LinkedList<Adyacencia> adyacentes = graph.adyacenciasL(graph.getLabelL(u));
                if (!adyacentes.isEmpty()) {
                    Adyacencia[] ady = adyacentes.toArray();
                    for (Adyacencia a : ady) {
                        int v = a.getDestination();
                        double peso = a.getWeight();
                        distancias[u][v] = peso;
                    }
                }
            }

            // Aplicar Floyd-Warshall
            for (int k = 1; k <= V; k++) {
                for (int i = 1; i <= V; i++) {
                    for (int j = 1; j <= V; j++) {
                        if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                            distancias[i][j] = distancias[i][k] + distancias[k][j];
                        }
                    }
                }
            }

            // Crear un mapa para almacenar los resultados
            Map<String, Object> resultados = new HashMap<>();
            Map<String, Map<String, String>> matrizDistancias = new HashMap<>();
            for (int i = 1; i <= V; i++) {
                Map<String, String> fila = new HashMap<>();
                for (int j = 1; j <= V; j++) {
                    if (distancias[i][j] == Double.POSITIVE_INFINITY) {
                        fila.put(graph.getLabelL(j).toString(), "INF");
                    } else {
                        fila.put(graph.getLabelL(j).toString(), String.valueOf(distancias[i][j]));
                    }
                }
                matrizDistancias.put(graph.getLabelL(i).toString(), fila);
            }
            resultados.put("matriz_distancias", matrizDistancias);

            // Convertir el mapa a JSON
            Gson gson = new Gson();
            String json = gson.toJson(resultados);

            // Escribir el JSON en un archivo
            String rutaCompleta = "/home/ariel/Documents/Taller_Domingo_LINKED_LIST/FRONT/static/js/floydWarshall.js";
            try (FileWriter file = new FileWriter(rutaCompleta)) {
                file.write(json);
                System.out.println("Resultados de Floyd-Warshall guardados en " + rutaCompleta);
            } catch (IOException e) {
                System.out.println("Error al escribir el archivo JSON: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error en floydWarshall: " + e.getMessage());
        }
    }

}
