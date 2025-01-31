package controller.tda.graph;

import controller.tda.list.OverFlowException;

public class GraphNoDirect extends GraphDirect {
    public GraphNoDirect(Integer nro_vertices) {
        super(nro_vertices);
    }

    @Override
    public void add_edge(Integer v1, Integer v2, Float weight) throws Exception {
        if (v1.intValue() <= nro_Ver() && v2.intValue() <= nro_Ver()) {
            if (!is_Edge(v1, v2)) {
                // nro_edges++;
                Adyacencia aux = new Adyacencia();
                aux.setWeight(weight);
                aux.setDestination(v2);
                getListAdyacencias()[v1].add(aux);

                Adyacencia auxD = new Adyacencia();
                auxD.setWeight(weight);
                auxD.setDestination(v1);
                getListAdyacencias()[v2].add(auxD);
            }
        } else {
            throw new OverFlowException("Los vertices están fuera de rango");
        }

    }

}
