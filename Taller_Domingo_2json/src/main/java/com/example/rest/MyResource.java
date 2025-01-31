package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controller.tda.graph.Graph;
import controller.tda.graph.GraphDirect;
import controller.tda.graph.GraphNoDirect;
import controller.tda.graph.GraphLabelDirect;
import controller.tda.graph.GraphLabelNoDirect;
import controller.Dao.GraphDirectDao;
import controller.Dao.servicies.FamiliaServicies;
import controller.Dao.AlmacenDao;
import models.AdyacenciaJSON;
import models.Almacen;
import controller.tda.list.LinkedList;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        // HashMap mapa = new HashMap<>();
        // // GraphLabelNoDirect graph = null;
        // GraphLabelNoDirect graph = new GraphLabelNoDirect<>(3, Producto.class);

        // try {
        // ProductoServicies fs = new ProductoServicies();
        // LinkedList<Producto> lf = fs.listAll();
        // if (!lf.isEmpty()) {
        // graph = new GraphLabelNoDirect<>(lf.getSize(), Producto.class);
        // Producto[] m = lf.toArray();
        // for (int i = 0; i < lf.getSize(); i++) {
        // graph.labelsVertices((i + 1), m[i]);
        // }

        // }
        // Producto mouse = new Producto(1, "Meetion", "MouseGamer", 10.0f, 5.0f);
        // Producto teclado = new Producto(2, "LogiTech", "Teclado pro", 20.5f, 5.0f);
        // Producto mousepad = new Producto(2, "MousePad", "Marca ASUS", 5.0f, 4.5f);

        // graph.labelsVertices(1, mouse);
        // graph.labelsVertices(2, teclado);
        // graph.labelsVertices(3, mousepad);

        // graph.insertEdgeL(mouse, mousepad, 2.0f);

        // GraphDirectDao gdd = new GraphDirectDao();
        // // gdd.setGraphDirect(graph);

        // gdd.save(graph);

        // // graph.drawGraph();

        // // graph.add_edge(1, 2, 19.0f);
        // } catch (Exception e) {
        // System.out.println("Error MyResource " + e);
        // mapa.put("msg", "Ok");
        // mapa.put("data", e.toString());
        // return Response.status(Status.BAD_REQUEST).entity(mapa).build();
        // }
        // System.out.println(graph.toString());
        // mapa.put("msg", "OK");
        // mapa.put("data", graph.toString());
        // System.out.println(graph.toString());

        AlmacenDao ad = new AlmacenDao();

        ad.createGraph();
        LinkedList<AdyacenciaJSON> adyacencias = ad.loadAdyacenciasFromJSON();

        ad.buildGraphFromAdyacencias(adyacencias);

        long inicio = System.nanoTime(); // Medimos el tiempo desde un punto fijo

        ad.bellmanFord(ad.buscar_IdAlmacen(1));

        long fin = System.nanoTime(); // Marca de finalización
        long duracion = fin - inicio; // Tiempo transcurrido en nanosegundos
        System.out.println("Tiempo en bellmanFord: " + (duracion / 1_000_000) + " milisegundos.");

        long inicio2 = System.nanoTime(); // Medimos el tiempo desde un punto fijo

        ad.floydWarshall();

        long fin2 = System.nanoTime(); // Marca de finalización
        long duracion2 = fin2 - inicio2; // Tiempo transcurrido en nanosegundos
        System.out.println("Tiempo en floydWarshall: " + (duracion2 / 1_000_000) + " milisegundos.");

        // ad.floydWarshall();
        return Response.ok().build();

    }

}

// package com.example.rest;

// import java.util.HashMap;

// import javax.ws.rs.GET;
// import javax.ws.rs.Path;
// import javax.ws.rs.Produces;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;
// import javax.ws.rs.core.Response.Status;

// import controller.tda.graph.Graph;
// import controller.tda.graph.GraphDirect;
// import controller.tda.graph.GraphNoDirect;
// import controller.tda.graph.GraphLabelDirect;
// import controller.tda.graph.GraphLabelNoDirect;
// import controller.Dao.servicies.FamiliaServicies;
// import models.Familia;
// import controller.tda.list.LinkedList;

// /**
// * Root resource (exposed at "myresource" path)
// */
// @Path("myresource")
// public class MyResource {

// @GET
// @Produces(MediaType.APPLICATION_JSON)
// public Response getIt() {
// HashMap mapa = new HashMap<>();
// GraphLabelNoDirect graph = new GraphLabelNoDirect<>(5, String.class);

// try {
// FamiliaServicies ps = new FamiliaServicies();
// LinkedList<Familia> lp = ps.listAll();
// if (!lp.isEmpty()) {
// graph = new GraphLabelNoDirect<>(lp.getSize(), Familia.class);
// Familia[] m = lp.toArray();
// for (int i = 0; i < lp.getSize(); i++) {
// graph.labelsVertices((i + 1), m[i]);
// }

// }
// graph.labelsVertices(1, "Maria");
// graph.labelsVertices(2, "Jara");
// graph.labelsVertices(3, "Yosibel");
// graph.labelsVertices(4, "Eberson");
// graph.labelsVertices(5, "Paez");
// graph.labelsVertices(5, "Moises");
// graph.labelsVertices(5, "Caicedo");
// graph.labelsVertices(5, "Teemo");
// graph.labelsVertices(5, "Pancho");
// graph.labelsVertices(5, "Pepe");

// // graph.insertEdgeL("Maria", "Jara", 8.0f);

// // graph.drawGraph();

// // graph.add_edge(1, 10, 19.0f);
// } catch (Exception e) {
// System.out.println("Error MyResource " + e);
// mapa.put("msg", "Ok");
// mapa.put("data", e.toString());
// return Response.status(Status.BAD_REQUEST).entity(mapa).build();
// }
// System.out.println(graph.toString());
// mapa.put("msg", "OK");
// mapa.put("data", graph.toString());
// System.out.println(graph.toString());
// return Response.ok(mapa).build();

// }

// }