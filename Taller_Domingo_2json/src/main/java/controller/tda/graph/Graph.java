package controller.tda.graph;

import controller.tda.list.LinkedList;

public abstract class Graph {

    public abstract Integer nro_Ver();

    public abstract Integer nro_Edges();

    public abstract Boolean is_Edge(Integer v1, Integer v2) throws Exception;

    public abstract Float weight_edge(Integer v1, Integer v2) throws Exception;

    public abstract void add_edge(Integer v1, Integer v2) throws Exception;

    public abstract void add_edge(Integer v1, Integer v2, Float weight) throws Exception;

    public abstract LinkedList<Adyacencia> adyacencias(Integer v1);

    @Override

    public String toString() {
        String grafo = "";
        try {
            for (int i = 1; i <= this.nro_Ver(); i++) {
                grafo += "V" + i + "\n";
                LinkedList<Adyacencia> lista = adyacencias(i);
                if (!lista.isEmpty()) {
                    Adyacencia[] ady = lista.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "ady " + "V" + a.getDestination() + " weight:" + a.getWeight() + "\n";
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error Graph:" + e);
        }

        return grafo;
    }

}
