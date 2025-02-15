from flask import Flask, Blueprint, abort, request, render_template, redirect
import json
import requests
from flask import flash
from flask import Blueprint, jsonify
from flask import send_from_directory
import os


router = Blueprint("router", __name__)


@router.route("/")
def home():

    return render_template("fragmento/inicial/login.html")


@router.route("/admin")
def admin():
    return render_template("fragmento/inicial/admin.html")


@router.route("/admin/familia/register")
def view_register_familia():
    r_familia = requests.get("http://localhost:8086/api/familia/list")
    data_familia = r_familia.json()
    r_generador = requests.get("http://localhost:8086/api/generador/list")
    data_generador = r_generador.json()

    return render_template(
        "fragmento/familia/registro.html",
        lista_familia=data_familia["data"],
        lista_generador=data_generador["data"],
    )


BELLMAN_JSON_PATH = (
    "/home/ariel/Documents/Taller_Domingo_LINKED_LIST/FRONT/static/js/bellmanFord.js"
)
FLOYD_JSON_PATH = (
    "/home/ariel/Documents/Taller_Domingo_LINKED_LIST/FRONT/static/js/floydWarshall.js"
)


@router.route("/admin/grafo")
def view_graph():
    try:
        with open(BELLMAN_JSON_PATH, "r") as file:
            bellman_data = json.load(file)

        with open(FLOYD_JSON_PATH, "r") as file:
            floyd_data = json.load(file)

        def simplificar_almacen(almacen_str):
            nombre = almacen_str.split("nombre='")[1].split("'")[0]
            return f"Almacen{{nombre='{nombre}'}}"

        bellman_data["origen"] = simplificar_almacen(bellman_data["origen"])
        bellman_data["distancias"] = {
            simplificar_almacen(almacen): distancia
            for almacen, distancia in bellman_data["distancias"].items()
        }

        floyd_data["matriz_distancias"] = {
            simplificar_almacen(origen): {
                simplificar_almacen(destino): distancia
                for destino, distancia in destinos.items()
            }
            for origen, destinos in floyd_data["matriz_distancias"].items()
        }

        return render_template(
            "grafo.html", bellman_data=bellman_data, floyd_data=floyd_data
        )
    except Exception as e:
        flash(f"Error al cargar los datos del grafo: {str(e)}", "error")
        return redirect("/admin")


@router.route("/admin/familia/list")
def list_person(msg=""):
    r_familia = requests.get("http://localhost:8086/api/familia/list")
    data_familia = r_familia.json()
    r_generador = requests.get("http://localhost:8086/api/generador/list")
    data_generador = r_generador.json()
    print(data_familia)

    return render_template(
        "fragmento/familia/lista.html",
        lista_familia=data_familia["data"],
        lista_generador=data_generador["data"],
    )


@router.route("/admin/familia/edit/<id>", methods=["GET"])
def view_edit_person(id):

    r = requests.get("http://localhost:8086/api/familia/listType")
    lista_tipos = r.json()  # Guardamos la respuesta JSON

    r1 = requests.get(
        f"http://localhost:8086/api/familia/get/{id}"
    )  # Obtenemos los datos de la familia por ID

    if r1.status_code == 200:
        data_familia = r1.json()

        if (
            "data" in data_familia and data_familia["data"]
        ):  # Verificamos que la respuesta contenga datos válidos
            familia = data_familia["data"]

            if familia[
                "tieneGenerador"
            ]:  # Obtenemos los datos del generador asociado a la familia
                r_generador = requests.get(
                    f"http://localhost:8086/api/generador/get/{familia['id']}"
                )
                if r_generador.status_code == 200:
                    data_generador = r_generador.json()
                    generador = (
                        data_generador["data"] if "data" in data_generador else None
                    )
                else:
                    generador = None  # Si no se encontraron datos del generador, se inicializa a None

            else:
                generador = (
                    None  # Si la familia no tiene generador, se inicializa a None
                )

            return render_template(
                "fragmento/familia/editar.html",
                lista=lista_tipos["data"],
                familia=familia,
                generador=generador,
            )
        else:
            flash("No se encontraron datos para la familia.", category="error")
            return redirect("/admin/familia/list")
    else:
        flash("Error al obtener la familia", category="error")
        return redirect("/admin/familia/list")


@router.route("/admin/familia/save", methods=["POST"])
def save_person():
    headers = {"Content-Type": "application/json"}
    form = request.form

    data_familia = {
        "canton": form["can"],
        "apellidoPaterno": form["ape"],
        "apellidoMaterno": form["apem"],
        "integrantes": form["inte"],
        "tieneGenerador": form["tieneg"] == "true",
    }

    if form["tieneg"] == "true":  # Solo si tiene generador
        data_generador = {
            "costo": form["cost"],
            "consumoXHora": form["conxh"],
            "energiaGenerada": form["energen"],
            "uso": form["uso"],
        }
    else:
        data_generador = {  # Inicializa el generador a valores predeterminados
            "costo": 0,
            "consumoXHora": 0,
            "energiaGenerada": 0,
            "uso": "ninguno",
        }

    r_familia = requests.post(
        "http://localhost:8086/api/familia/save",
        data=json.dumps(data_familia),
        headers=headers,
    )  # Hacer la petición para guardar la familia

    requests.post(
        "http://localhost:8086/api/generador/save",
        data=json.dumps(data_generador),
        headers=headers,
    )  # Hacer la petición para guardar el generador

    if r_familia.status_code == 200:

        flash("Registro guardado correctamente", category="info")
        return redirect("/admin/familia/list")
    else:
        flash(
            r_familia.json().get("data", "Error al guardar la familia"),
            category="error",
        )
        return redirect("/admin/familia/list")


@router.route("/admin/familia/update", methods=["POST"])
def update_person():
    headers = {"Content-Type": "application/json"}
    form = request.form

    data_familia = {
        "id": form["id"],
        "canton": form["can"],
        "apellidoPaterno": form["ape"],
        "apellidoMaterno": form["apem"],
        "integrantes": form["inte"],
        "tieneGenerador": form["tieneg"] == "true",
    }

    if form["tieneg"] == "true":  # Solo si tiene generador
        data_generador = {
            "id": form["id"],  # Usamos el mismo ID que la familia
            "costo": form["cost"],
            "consumoXHora": form["conxh"],
            "energiaGenerada": form["energen"],
            "uso": form["uso"],
        }
    else:

        data_generador = {  # Inicializa el generador a valores predeterminados
            "id": form["id"],
            "costo": 0,
            "consumoXHora": 0,
            "energiaGenerada": 0,
            "uso": "ninguno",
        }

    r_generador = requests.post(
        "http://localhost:8086/api/generador/update",
        data=json.dumps(data_generador),
        headers=headers,
    )

    if r_generador.status_code != 200:
        flash(
            "Error al actualizar el generador: " + r_generador.json().get("data", ""),
            category="error",
        )

    r_familia = requests.post(
        "http://localhost:8086/api/familia/update",
        data=json.dumps(data_familia),
        headers=headers,
    )

    if r_familia.status_code == 200:
        flash("Registro de familia guardado correctamente", category="info")
        return redirect("/admin/familia/list")
    else:
        flash(
            r_familia.json().get("data", "Error al actualizar la familia"),
            category="error",
        )
        return redirect("/admin/familia/list")


@router.route("/admin/familia/delete/<int:id>", methods=["POST"])
def delete_familia(id):

    requests.delete(
        f"http://localhost:8086/api/familia/delete/{id}"
    )  # Solicitar la eliminación de la familia y el generador asociado

    requests.delete(f"http://localhost:8086/api/generador/delete/{id}")

    return redirect(
        "/admin/familia/list"
    )  # Redirigimos al usuario a la lista de familias


def load_data(file_path):
    with open(file_path, "r") as file:
        return json.load(file)


@router.route("/admin/familia_generador", methods=["GET"])
def get_familia_generador_data():
    # Cargamos datos de familias y generadores desde JSON
    familias = load_data(
        "/home/ariel/Documents/Taller_Domingo_LINKED_LIST/Taller_Domingo_2json/src/main/java/Data/Familia.json"
    )
    generadores = load_data(
        "/home/ariel/Documents/Taller_Domingo_LINKED_LIST/Taller_Domingo_2json/src/main/java/Data/Generador.json"
    )

    familias_generadores = (
        []
    )  # Creamos lista para almacenar la relación de familias y generadores
    for familia in familias:
        generador = next((g for g in generadores if g["id"] == familia["id"]), None)
        familias_generadores.append({"familia": familia, "generador": generador})

    response = requests.get(
        "http://localhost:8086/api/familia/contadorGeneradores"
    )  # Obtenemos  el conteo de familias con generador
    data = response.json()
    familias_con_generador = data["familiasConGenerador"]

    total_familias = len(familias)  # Calcular el total de familias encuestadas

    return render_template(  # Juntamos los datos
        "fragmento/familia/familia_generador.html",
        familias_generadores=familias_generadores,
        familias_con_generador=familias_con_generador,
        total_familias=total_familias,
    )


@router.route("/admin/familia/search/<criterio>/<texto>")
def view_buscar_familia(criterio, texto):
    url = "http://localhost:8086/api/familia/list/search/"
    if criterio == "apellidoP":
        r = requests.get(url + "apellidoP/" + texto)
    elif criterio == "apellidoM":
        r = requests.get(url + "apellidoM/" + texto)
    elif criterio == "canton":
        r = requests.get(url + "canton/" + texto)
    elif criterio == "integrantes":
        r = requests.get(url + "integrantes/" + texto)
    elif criterio == "integrantesBinario":
        r = requests.get(url + "integrantesBinario/" + texto)
    elif criterio == "id":
        r = requests.get(url + "id/" + texto)

    data1 = r.json()
    print(f"Response JSON: {data1}")
    if r.status_code == 200:
        if type(data1["data"]) is dict:
            list = []
            list.append(data1["data"])
            return render_template("fragmento/familia/lista.html", lista_familia=list)
            print(f"Lista procesada (dict): {lista}")

        else:
            print(f"Lista procesada (lista): {data1['data']}")
            return render_template(
                "fragmento/familia/lista.html", lista_familia=data1["data"]
            )

    else:
        return render_template(
            "fragmento/familia/lista.html",
            lista_familia=[],
            message="No existe el elemento",
        )


@router.route("/admin/familia/order/<atributo>/<tipo>/<metodo>")
def view_order_familia(atributo, tipo, metodo):
    url = (
        "http://localhost:8086/api/familia/list/order/"
        + atributo
        + "/"
        + tipo
        + "/"
        + metodo
    )

    r = requests.get(url)

    data1 = r.json()
    if r.status_code == 200:
        return render_template(
            "fragmento/familia/lista.html", lista_familia=data1["data"]
        )

    else:
        return render_template(
            "fragmento/familia/lista.html",
            lista_familia=[],
            message="No existe el elemento",
        )


# Rutas a los archivos JSON
BELLMAN_JSON_PATH = (
    "/home/ariel/Documents/Taller_Domingo_LINKED_LIST/FRONT/static/js/bellmanFord.js"
)
FLOYD_JSON_PATH = (
    "/home/ariel/Documents/Taller_Domingo_LINKED_LIST/FRONT/static/js/floydWarshall.js"
)
