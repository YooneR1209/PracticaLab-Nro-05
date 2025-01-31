// Configuración del mapa base
var osmUrl = 'https://tile.openstreetmap.org/{z}/{x}/{y}.png';
var osmAttrib = '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>';
var osm = L.tileLayer(osmUrl, { maxZoom: 15, attribution: osmAttrib });

// Inicializar el mapa y centrarlo en Loja
var map = L.map('map').setView([-3.996716943365505, -79.20174502581729], 15).addLayer(osm);

// Añadir marcadores
L.marker([-4.01066, -79.20467])  // Marcador 1: Coordenadas correctas
    .bindPopup('General')
    .addTo(map);  // Añadir al mapa

L.marker([-3.972330837690825, -79.20612766640531])  // Marcador 2: Coordenadas corregidas
    .bindPopup('Almacen David')
    .addTo(map);  // Añadir al mapa