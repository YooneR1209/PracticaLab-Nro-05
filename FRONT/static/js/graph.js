var nodes = new vis.DataSet([
{ id: 1, label: "Zerimar"},
{ id: 2, label: "Mercado del Sol"},
{ id: 3, label: "Mercado Vital"},
]);
var edges = new vis.DataSet([
{ from: 1, to: 2, label: "3.8040270129852316"},
{ from: 2, to: 1, label: "3.8040270129852316"},
{ from: 3, to: 2, label: "1.1606785117488192"},
]);
var container = document.getElementById("mynetwork");
var data = {
nodes: nodes,
edges: edges,
};
var options = {};
var network = new vis.Network(container, data, options);