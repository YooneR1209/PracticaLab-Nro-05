 var nodes = new vis.DataSet([
{ id: 1, label: "Almacen{id=1, nombre='General', latitud=-4.006534609038159, longitud=-79.20545073861888, capacidad=69}"},{ id: 2, label: "Almacen{id=2, nombre='Almacen David', latitud=-3.972330837690825, longitud=-79.20612766640531, capacidad=100}"},]);
var edges = new vis.DataSet([
]);
var container = document.getElementById("mynetwork");
var data = {
nodes: nodes,
edges: edges,
};
var options = {};
var network = new vis.Network(container, data, options);