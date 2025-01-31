package controller.tda.graph;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controller.tda.list.LabelException;
import controller.tda.list.LinkedList;
import models.AdyacenciaJSON;
import models.Almacen;

public class GraphLabelDirect<E> extends GraphDirect {
    protected E labels[];
    protected HashMap<E, Integer> dictVertices;
    transient Class<E> clazz; // Este campo no será serializado

    public GraphLabelDirect(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices);
        this.clazz = clazz;
        labels = (E[]) Array.newInstance(clazz, nro_vertices + 1);
        dictVertices = new HashMap<>();
    }

    public Boolean is_EdgeL(E v1, E v2) throws Exception {
        if (isLabelsGraph()) {
            return is_Edge(getVerticeL(v1), getVerticeL(v2));
        } else {
            throw new LabelException("is_EdgeL: Grafo no etiquetado");
        }
    }

    public void insertEdgeL(E v1, E v2, Float weight) throws Exception {
        if (isLabelsGraph()) {
            add_edge(getVerticeL(v1), getVerticeL(v2), weight);
        } else {
            throw new LabelException("insertEdgeL: Grafo no etiquetado");
        }

    }

    public void insertEdgeL(E v1, E v2) throws Exception {
        if (isLabelsGraph()) {
            insertEdgeL(v1, v2, Float.NaN);
            // add_edge(getVerticeL(v1), getVerticeL(v2), Float.NaN);
        } else {
            throw new LabelException("insertEdgeL: Grafo no etiquetado");
        }

    }

    public LinkedList<Adyacencia> adyacenciasL(E v) throws Exception {
        if (isLabelsGraph()) {
            return adyacencias(getVerticeL(v));
        } else {
            throw new LabelException("adyacenciasL: Grafo no etiquetado");
        }
    }

    public void labelsVertices(Integer v, E data) {
        labels[v] = data;
        dictVertices.put(data, v);
    }

    public Boolean isLabelsGraph() {
        Boolean band = true;
        for (int i = 1; i < labels.length; i++) {
            if (labels[i] == null) {
                band = false;
                break;
            }

        }
        return band;
    }

    public Integer getVerticeL(E label) {
        return dictVertices.get(label);
    }

    public E getLabelL(Integer v1) {
        return labels[v1];
    }

    @Override
    public String toString() {
        String grafo = "";
        try {
            for (int i = 1; i <= this.nro_Ver(); i++) {
                grafo += "V" + i + " [" + getLabelL(i).toString() + "]" + "\n";
                LinkedList<Adyacencia> lista = adyacencias(i);
                if (!lista.isEmpty()) {
                    Adyacencia[] ady = lista.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "ady " + "V" + a.getDestination() + " weight:" + a.getWeight() + " ["
                                + getLabelL(a.getDestination()).toString() + "]" + "\n";
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error Graph:" + e);
        }

        return grafo;
    }

    public String drawGraph() {
        String grafo = "var nodes = new vis.DataSet([" + "\n";
        try {
            // Leer el archivo JSON de adyacencias
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileReader reader = new FileReader("resources" + File.separatorChar + "adyacencias.json");
            AdyacenciaJSON[] adyacencias = gson.fromJson(reader, AdyacenciaJSON[].class);
            reader.close();

            // Construir los nodos
            for (int i = 1; i <= this.nro_Ver(); i++) {
                grafo += "{ id: " + i + ", label: " + '"' + ((Almacen) getLabelL(i)).getNombre() + '"' + "}," + "\n";
            }
            grafo += "]);" + "\n";

            // Construir las aristas
            grafo += "var edges = new vis.DataSet([" + "\n";
            for (AdyacenciaJSON adyacencia : adyacencias) {
                grafo += "{ from: " + adyacencia.getOrigen() + ", to: " + adyacencia.getDestino() + ", label: " + '"'
                        + adyacencia.getPeso() + '"' + "}," + "\n";
            }
            grafo += "]);" + "\n";

            // Crear el gráfico
            grafo += "var container = document.getElementById(\"mynetwork\");" + "\n";
            grafo += "var data = {" + "\n";
            grafo += "nodes: nodes," + "\n";
            grafo += "edges: edges," + "\n";
            grafo += "};" + "\n";
            grafo += "var options = {};" + "\n";
            grafo += "var network = new vis.Network(container, data, options);";

            // Escribir el archivo
            String rutaCompleta = "/home/ariel/Documents/Taller_Domingo_LINKED_LIST/FRONT/static/js/graph.js";
            FileWriter file = new FileWriter(rutaCompleta);
            file.write(grafo);
            file.flush();
            file.close();
        } catch (Exception e) {
            System.out.println("Error Graph:" + e);
        }

        return grafo;
    }

}
