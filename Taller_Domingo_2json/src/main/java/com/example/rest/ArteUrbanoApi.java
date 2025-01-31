// package com.example.rest;

// import controller.Dao.servicies.ArteUrbanoServicies;
// import controller.Dao.servicies.ProductoServicies;

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

// @Path("arteUrbano")
// public class ArteUrbanoApi {

// @Path("/list")
// @GET
// @Produces(MediaType.APPLICATION_JSON)
// public Response getAllPersons() {

// HashMap map = new HashMap<>();
// ArteUrbanoServicies ls = new ArteUrbanoServicies();
// map.put("msg", "Ok");

// try {
// map.put("data", ls.listAll());
// if (ls.listAll().isEmpty()) {
// map.put("data", new Object[] {});

// }
// return Response.ok(map).build();
// } catch (Exception e) {
// map.put("data", new Object[] {});

// // TODO: handle exception
// }
// return Response.ok(map).build();

// }

// @Path("/get/{id}")
// @GET
// @Produces(MediaType.APPLICATION_JSON)
// public Response getPerson(@PathParam("id") Integer id) {
// HashMap map = new HashMap<>();
// ArteUrbanoServicies ls = new ArteUrbanoServicies();
// try {
// ls.setArteUrbano(ls.get(id));
// } catch (Exception e) {
// System.out.println("Error " + e);
// }
// map.put("msg", "Ok");
// map.put("data", ls.getArteUrbano());
// if (ls.getArteUrbano().getId() == null) {
// map.put("data", "No existe la ArteUrbano con ese identificador");
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

// ArteUrbanoServicies ls = new ArteUrbanoServicies();
// ProductoServicies productoServicies = new ProductoServicies();
// ls.getArteUrbano().setNombre(map.get("nombre").toString());
// ls.getArteUrbano().setDescripcion(map.get("descripcion").toString());
// ls.getArteUrbano().setAuthors(map.get("authors").toString());
// ls.getArteUrbano().setLng(Double.parseDouble(map.get("lng").toString()));
// ls.getArteUrbano().setLat(Double.parseDouble(map.get("lat").toString()));
// ls.getArteUrbano().setYear(Integer.parseInt(map.get("year").toString()));
// ls.getArteUrbano().setIsMuaral(
// ((map.get("ismural").toString().equalsIgnoreCase("true")) ? Boolean.TRUE :
// Boolean.FALSE));

// ls.save();

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

// ArteUrbanoServicies ls = new ArteUrbanoServicies();
// ls.setArteUrbano(ls.get(Integer.parseInt(map.get("id").toString())));
// System.out.println(ls.getArteUrbano().getId());
// ls.getArteUrbano().setNombre(map.get("nombre").toString());
// ls.getArteUrbano().setDescripcion(map.get("descripcion").toString());
// ls.getArteUrbano().setAuthors(map.get("authors").toString());
// ls.getArteUrbano().setLng(Double.parseDouble(map.get("lng").toString()));
// ls.getArteUrbano().setLat(Double.parseDouble(map.get("lat").toString()));
// ls.getArteUrbano().setYear(Integer.parseInt(map.get("year").toString()));
// ls.getArteUrbano().setIsMuaral(
// ((map.get("ismural").toString().equalsIgnoreCase("true")) ? Boolean.TRUE :
// Boolean.FALSE));

// ls.update();

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
// ArteUrbanoServicies ls = new ArteUrbanoServicies();

// map.put("msg", "Ok");
// map.put("data", ls.getArteUrbano());
// return Response.ok(map).build();
// }

// @Path("/delete/{id}")
// @POST
// @Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
// public Response deleteArteUrbano(@PathParam("id") int id) {
// HashMap<String, Object> res = new HashMap<>();

// try {
// ArteUrbanoServicies fs = new ArteUrbanoServicies();

// boolean ArteUrbanoDeleted = fs.delete(id); // Intentamos eliminar el
// ArteUrbano

// if (!ArteUrbanoDeleted) {

// res.put("message", "ArteUrbano no encontrada o no eliminada"); // Si no se
// eliminó, enviar un error 404
// return Response.status(Response.Status.NOT_FOUND).entity(res).build();

// } else {

// res.put("message", "ArteUrbano eliminados exitosamente");
// return Response.ok(res).build();

// }
// } catch (Exception e) {

// res.put("message", "Error al intentar eliminar la ArteUrbano"); // En caso de
// error, devolver una respuesta
// // de
// // error interno
// res.put("error", e.getMessage());
// return
// Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
// }
// }

// }
