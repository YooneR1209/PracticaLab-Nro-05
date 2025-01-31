package controller.tda.graph;

import java.util.HashMap;
import java.util.Map;

import controller.tda.list.LinkedList;
import controller.tda.list.OverFlowException;

public class GraphDirect extends Graph {
    private Integer nro_vertices;
    private Integer nro_edges;
    private LinkedList<Adyacencia> listAdyacencias[];

    public GraphDirect(Integer nro_vertices) {
        this.nro_vertices = nro_vertices;
        this.nro_edges = 0;
        listAdyacencias = new LinkedList[nro_vertices + 1];
        for (int i = 1; i <= nro_vertices; i++) {
            listAdyacencias[i] = new LinkedList<>();

        }
    }

    public GraphDirect() {
    };

    @Override
    public Integer nro_Edges() {
        return this.nro_edges;
    }

    @Override
    public Integer nro_Ver() {
        return this.nro_vertices;
    }

    @Override
    public Boolean is_Edge(Integer v1, Integer v2) throws Exception {
        Boolean band = false;
        if (v1.intValue() <= nro_vertices && v2.intValue() <= nro_vertices) {
            LinkedList<Adyacencia> lista = listAdyacencias[v1];
            if (!lista.isEmpty()) {
                Adyacencia[] matrix = lista.toArray();
                for (int i = 0; i < matrix.length; i++) {
                    Adyacencia aux = matrix[i];
                    if (aux.getDestination().intValue() == v2.intValue()) {
                        band = true;
                        break;

                    }

                }

            }
        } else {
            throw new OverFlowException("Los vertices están fuera de rango");
        }
        return band;
    }

    @Override
    public Float weight_edge(Integer v1, Integer v2) throws Exception {
        Float weight = Float.NaN;
        if (is_Edge(v1, v2)) {
            LinkedList<Adyacencia> lista = listAdyacencias[v1];
            if (!lista.isEmpty()) {
                Adyacencia[] matrix = lista.toArray();
                for (int i = 0; i < matrix.length; i++) {
                    Adyacencia aux = matrix[i];
                    if (aux.getDestination().intValue() == v2.intValue()) {
                        weight = aux.getWeight();
                        break;

                    }

                }

            }
        } else {
            throw new OverFlowException("Los vertices están fuera de rango");
        }

        return weight;
    }

    @Override
    public void add_edge(Integer v1, Integer v2, Float weight) throws Exception {
        if (v1.intValue() <= nro_vertices && v2.intValue() <= nro_vertices) {
            if (!is_Edge(v1, v2)) {
                nro_edges++;
                Adyacencia aux = new Adyacencia();
                aux.setWeight(weight);
                aux.setDestination(v2);
                listAdyacencias[v1].add(aux);
            }
        } else {
            throw new OverFlowException("Los vertices están fuera de rango");
        }

    }

    @Override
    public void add_edge(Integer v1, Integer v2) throws Exception {
        this.add_edge(v1, v2, Float.NaN);

    }

    @Override
    public LinkedList<Adyacencia> adyacencias(Integer v1) {
        return listAdyacencias[v1];
    }

    public void setNro_edges(Integer nro_edges) {
        this.nro_edges = nro_edges;
    }

    public LinkedList<Adyacencia>[] getListAdyacencias() {
        return listAdyacencias;
    }

    public Map<Integer, Float> bellmanFord(Integer v1) throws Exception {
        if (v1 < 1 || v1 > nro_vertices) {
            throw new OverFlowException("El vértice está fuera de rango.");
        }

        // Tabla de distancias mínimas
        Map<Integer, Float> distancias = new HashMap<>();
        for (int i = 1; i <= nro_vertices; i++) {
            distancias.put(i, Float.POSITIVE_INFINITY); // Inicializa todas las distancias a infinito
        }
        distancias.put(v1, 0.0f); // La distancia al nodo fuente es 0

        // Realizamos el algoritmo de Bellman-Ford
        for (int i = 1; i <= nro_vertices - 1; i++) {
            // Recorremos todas las aristas
            for (int j = 1; j <= nro_vertices; j++) {
                Adyacencia[] neighbors = listAdyacencias[j].toArray();
                for (int k = 0; k < neighbors.length; k++) {
                    Adyacencia neighbor = neighbors[k];
                    Integer destinationNode = neighbor.getDestination();
                    Float weight = neighbor.getWeight();

                    // Si la distancia al nodo de destino puede ser relajada
                    if (distancias.get(j) + weight < distancias.get(destinationNode)) {
                        distancias.put(destinationNode, distancias.get(j) + weight);
                    }
                }
            }
        }

        // Comprobamos si hay ciclos negativos
        for (int j = 1; j <= nro_vertices; j++) {
            Adyacencia[] neighbors = listAdyacencias[j].toArray();
            for (int k = 0; k < neighbors.length; k++) {
                Adyacencia neighbor = neighbors[k];
                Integer destinationNode = neighbor.getDestination();
                Float weight = neighbor.getWeight();

                // Si la distancia al nodo de destino puede seguir relajándose, hay un ciclo
                // negativo
                if (distancias.get(j) + weight < distancias.get(destinationNode)) {
                    throw new Exception("El grafo contiene un ciclo negativo");
                }
            }
        }

        return distancias; // Retorna el mapa de distancias mínimas
    }

    public Map<String, Float> floydWarshall() {
        // Inicializar la matriz de distancias
        float[][] distancias = new float[nro_vertices + 1][nro_vertices + 1];

        // Inicializamos la matriz de distancias con valores altos (infinito)
        for (int i = 1; i <= nro_vertices; i++) {
            for (int j = 1; j <= nro_vertices; j++) {
                if (i == j) {
                    distancias[i][j] = 0.0f; // La distancia a sí mismo es 0
                } else {
                    distancias[i][j] = Float.POSITIVE_INFINITY; // Inicializa con infinito
                }
            }
        }

        // Llenamos la matriz de distancias con las aristas del grafo
        for (int i = 1; i <= nro_vertices; i++) {
            Adyacencia[] vecinos = listAdyacencias[i].toArray();
            for (int j = 0; j < vecinos.length; j++) {
                Integer destino = vecinos[j].getDestination();
                Float peso = vecinos[j].getWeight();
                distancias[i][destino] = peso; // Distancia directa entre los nodos
            }
        }

        // Algoritmo de Floyd-Warshall
        for (int k = 1; k <= nro_vertices; k++) {
            for (int i = 1; i <= nro_vertices; i++) {
                for (int j = 1; j <= nro_vertices; j++) {
                    // Si el camino pasando por k es más corto, actualizamos la distancia
                    if (distancias[i][j] > distancias[i][k] + distancias[k][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                    }
                }
            }
        }

        // Convertir la matriz de distancias a un mapa
        Map<String, Float> resultado = new HashMap<>();
        for (int i = 1; i <= nro_vertices; i++) {
            for (int j = 1; j <= nro_vertices; j++) {
                if (distancias[i][j] != Float.POSITIVE_INFINITY) {
                    resultado.put(i + "->" + j, distancias[i][j]);
                }
            }
        }

        return resultado; // Retorna el mapa con las distancias más cortas entre todos los pares
    }

}
