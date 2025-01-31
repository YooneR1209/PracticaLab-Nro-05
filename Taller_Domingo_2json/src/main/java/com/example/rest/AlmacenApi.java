// package com.example.rest;

// import controller.Dao.servicies.AlmacenServicies;

// import java.util.HashMap;

// import javax.ws.rs.GET;
// import javax.ws.rs.POST;
// import javax.ws.rs.Path;
// import javax.ws.rs.PathParam;
// import javax.ws.rs.Produces;
// import javax.ws.rs.Consumes;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;
// import javax.ws.rs.core.Response.Status;

// @Path("almacen")
// public class AlmacenApi {

// @Path("/list")
// @GET
// @Produces(MediaType.APPLICATION_JSON)
// public Response getAllPersons() {

// HashMap map = new HashMap<>();
// AlmacenServicies ps = new AlmacenServicies();
// map.put("msg", "Ok");
// map.put("data", ps.listAll().toArray());

// if (ps.listAll().isEmpty()) {
// map.put("data", new Object[] {});

// }
// return Response.ok(map).build();
// }

// @Path("/get/{id}")
// @GET
// @Produces(MediaType.APPLICATION_JSON)
// public Response getPerson(@PathParam("id") Integer id) {
// HashMap map = new HashMap<>();
// AlmacenServicies ps = new AlmacenServicies();
// try {
// ps.setAlmacen(ps.get(id));
// } catch (Exception e) {
// System.out.println("Error " + e);
// }
// map.put("msg", "Ok");
// map.put("data", ps.getAlmacen());
// if (ps.getAlmacen().getId() == null) {
// map.put("data", "No existe la almacen con ese identificador");
// return Response.status(Status.BAD_REQUEST).entity(map).build();
// }

// return Response.ok(map).build();
// }

// @Path("/save")
// @POST
// @Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
// public Response save(HashMap map) {
// // todo
// // Validation

// HashMap res = new HashMap<>();

// try {

// AlmacenServicies ps = new AlmacenServicies();
// ps.getAlmacen().setNombre(map.get("nombre").toString());
// ps.getAlmacen().setLatitud(Double.parseDouble(map.get("latitud").toString()));
// ps.getAlmacen().setLongitud(Double.parseDouble(map.get("longitud").toString()));
// ps.getAlmacen().setCapacidad(Integer.parseInt(map.get("capacidad").toString()));

// ps.save();
// res.put("msg", "Ok");
// res.put("data", "Guardado correctamente");
// return Response.ok(res).build();

// } catch (Exception e) {
// res.put("msg", "Error");
// res.put("data", e.toString());
// return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
// }
// }

// @Path("/update")
// @POST
// @Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
// public Response update(HashMap map) {

// HashMap res = new HashMap<>();

// try {

// AlmacenServicies ps = new AlmacenServicies();
// ps.setAlmacen(ps.get(Integer.parseInt(map.get("id").toString())));
// ps.getAlmacen().setNombre(map.get("nombre").toString());
// ps.getAlmacen().setLatitud(Double.parseDouble(map.get("latitud").toString()));
// ps.getAlmacen().setLongitud(Double.parseDouble(map.get("longitud").toString()));
// ps.getAlmacen().setCapacidad(Integer.parseInt(map.get("capacidad").toString()));

// ps.update();

// res.put("msg", "Ok");
// res.put("data", "Guardado correctamente");
// return Response.ok(res).build();

// } catch (Exception e) {
// res.put("msg", "Error");
// res.put("data", e.toString());
// return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
// }
// }

// @Path("/listType")

// @GET
// @Produces(MediaType.APPLICATION_JSON)
// public Response getType() {
// HashMap map = new HashMap<>();
// AlmacenServicies ps = new AlmacenServicies();

// map.put("msg", "Ok");
// map.put("data", ps.getAlmacen());
// return Response.ok(map).build();
// }

// @Path("/delete/{id}")
// @POST
// @Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
// public Response deleteAlmacen(@PathParam("id") int id) {
// HashMap<String, Object> res = new HashMap<>();

// try {
// AlmacenServicies ps = new AlmacenServicies();

// boolean AlmacenDeleted = ps.delete(id); // Intentamos eliminar el Almacen

// if (!AlmacenDeleted) {

// res.put("message", "Lote no encontrada o no eliminada"); // Si no se eliminó,
// enviar un error 404
// return Response.status(Response.Status.NOT_FOUND).entity(res).build();

// } else {

// res.put("message", "Lote eliminados exitosamente");
// return Response.ok(res).build();

// }
// } catch (Exception e) {

// res.put("message", "Error al intentar eliminar la Lote"); // En caso de
// error, devolver una respuesta de
// // error interno
// res.put("error", e.getMessage());
// return
// Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
// }
// }
// }
