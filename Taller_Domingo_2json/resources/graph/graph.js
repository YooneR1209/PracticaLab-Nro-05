 var nodes = new vis.DataSet([
{ id: 1, label: "General"},{ id: 2, label: "Almacen David"},]);
var edges = new vis.DataSet([
]);
var container = document.getElementById("mynetwork");
var data = {
nodes: nodes,
edges: edges,
};
var options = {};
var network = new vis.Network(container, data, options);