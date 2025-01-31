package controller.Dao;

import models.Familia;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class FamiliaDao extends AdapterDao<Familia> {
    private Familia familia = new Familia();
    private LinkedList<Familia> listAll;

    public FamiliaDao() {
        super(Familia.class);
    }

    public Familia getFamilia() { // Obtiene la familia
        if (familia == null) {
            familia = new Familia(); // En caso de que la familia sea nula, crea una nueva instancia de Familia
        }
        return this.familia; // Devuelve la familia
    }

    public void setFamilia(Familia familia) { // Establece la familia con un objeto Familia
        this.familia = familia; // Asigna el objeto Familia a la variable familia
    }

    public LinkedList getlistAll() { // Obtiene la lista de objetos
        if (listAll == null) { // Si la lista es nula
            this.listAll = listAll(); // Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; // Devuelve la lista de objetos de la variable listAll
    }

    public Boolean save() throws Exception { // Guarda la variable familia en la lista de objetos
        Integer id = getlistAll().getSize() + 1; // Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo id
        familia.setId(id); // Asigna el id a familia
        this.persist(this.familia); // Guarda la familia en la lista de objetos LinkedList y en el archivo JSON
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se guardó correctamente
    }

    public Boolean update() throws Exception { // Actualiza el nodo Familia en la lista de objetos
        this.merge(getFamilia(), getFamilia().getId() - 1); // Envia la familia a actualizar con su index
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true;
    }

    public Boolean delete(int index) throws Exception { // Elimina un objeto Familia por su índice
        this.supreme(index);
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se eliminó correctamente
    }

    public int contarFamiliasConGenerador() {
        int contador = 0;
        LinkedList<Familia> familias = listAll(); // lista todas las familias
        Familia[] familiasArray = familias.toArray(); // Convierte la lista enlazada en un arreglo

        for (Familia familia : familiasArray) { // Usa el bucle for-each en el arreglo
            if (familia.getTieneGenerador()) { // Verifica si la familia tiene generador
                contador++;
            }
        }
        return contador;
    }

    public LinkedList<Familia> order(String attribute, Integer type, Integer metodo) {
        try {
            getlistAll();
            System.out.println("Lista antes de ordenar " + listAll.toString());
            switch (metodo) {
                case 1:
                    System.out.println("Método de ordenación Lineal ");
                    return this.listAll.order(attribute, type);

                case 2:
                    System.out.println("Método de ordenación shellSort ");
                    return this.listAll.shellSort(attribute, type);

                case 3:
                    System.out.println("Método de ordenación quickSort ");
                    return this.listAll.quickSort(attribute, type);

                default:
                    System.out.println("Método de ordenación mergeSort ");
                    return this.listAll.mergeSort(attribute, type);
            }

        } catch (Exception e) {
            return null;
        }
    }

    public LinkedList<Familia> buscar_Apellido_Paterno(String texto) {
        System.out.println("Estamos buscando por Metodo Lineal");
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();
        if (!listita.isEmpty()) {
            Familia[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {
                if (aux[i].getApellidoPaterno().toLowerCase().startsWith(texto.toLowerCase())) {
                    lista.add(aux[i]);

                }
            }
        }
        return lista;
    }

    public LinkedList<Familia> buscar_Apellido_Materno(String texto) {
        System.out.println("Estamos buscando por Metodo Lineal");
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();
        if (!listita.isEmpty()) {
            Familia[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {
                if (aux[i].getApellidoMaterno().toLowerCase().startsWith(texto.toLowerCase())) {
                    lista.add(aux[i]);

                }
            }
        }
        return lista;
    }

    // BUSQUEDA BINARIA STRINGS

    public LinkedList<Familia> buscar_Apellido_Materno_Binaria(String texto) {

        System.out.println("Estamos buscando por Metodo Lineal-Binario AM");
        LinkedList<Familia> resultados = new LinkedList<>();

        try {
            getlistAll();

            if (!listAll.isEmpty()) {

                listAll.quickSort("apellidoMaterno", 0);
                int derecha = 0;
                int izquierda = listAll.getSize() - 1;

                while (derecha <= izquierda) {
                    int mid = (derecha + izquierda) / 2;
                    Familia midFamilia = listAll.get(mid);
                    String apellidoMaterno = midFamilia.getApellidoMaterno().toLowerCase();

                    if (apellidoMaterno.startsWith(texto.toLowerCase())) { // Añadimos elementos que coincidan hacia
                                                                           // ambos lados

                        int izqL = mid;
                        int derL = mid;
                        while (izqL >= 0
                                && listAll.get(izqL).getApellidoMaterno().toLowerCase()
                                        .startsWith(texto.toLowerCase())) {
                            resultados.addF(listAll.get(izqL--));
                        }
                        while (derL < listAll.getSize()
                                && listAll.get(derL).getApellidoMaterno().toLowerCase()
                                        .startsWith(texto.toLowerCase())) {
                            if (derL != mid)
                                resultados.add(listAll.get(derL));
                            derL++;
                        }
                        break;
                    } else if (apellidoMaterno.compareTo(texto.toLowerCase()) < 0) {
                        derecha = mid + 1;
                    } else {
                        izquierda = mid - 1;
                    }
                }
            }

            return resultados.quickSort("id", 0);

        } catch (Exception e) {
            System.out.println(e);
        }
        return resultados;

    }

    public LinkedList<Familia> buscar_Canton_LB(String texto) {

        System.out.println("Estamos buscando por Método Lineal-Binario C con arreglo");
        LinkedList<Familia> resultados = new LinkedList<>();

        try {
            getlistAll();

            if (!listAll.isEmpty()) {

                listAll.mergeSort("canton", 0);
                Familia[] arrayListAll = listAll.toArray();
                int derecha = 0;
                int izquierda = arrayListAll.length - 1;

                while (derecha <= izquierda) {
                    int mid = (derecha + izquierda) / 2;
                    Familia midFamilia = arrayListAll[mid];
                    String canton = midFamilia.getCanton().toLowerCase();

                    if (canton.startsWith(texto.toLowerCase())) { // Añadimos elementos que coincidan hacia ambos lados
                        int izqL = mid;
                        int derL = mid;

                        while (izqL >= 0
                                && arrayListAll[izqL].getCanton().toLowerCase().startsWith(texto.toLowerCase())) {
                            resultados.addF(arrayListAll[izqL--]);
                        }

                        while (derL < arrayListAll.length
                                && arrayListAll[derL].getCanton().toLowerCase().startsWith(texto.toLowerCase())) {
                            if (derL != mid) {
                                resultados.add(arrayListAll[derL]);
                            }
                            derL++;
                        }
                        break;
                    } else if (canton.compareTo(texto.toLowerCase()) < 0) {
                        derecha = mid + 1;
                    } else {
                        izquierda = mid - 1;
                    }
                }
            }

            return resultados.quickSort("id", 0);

        } catch (Exception e) {
            System.out.println("Error durante la búsqueda: " + e.getMessage());
        }
        return resultados;
    }

    public Familia buscar_Canton_Binaria(String texto) {
        System.out.println("Estamos buscando por Método Binario puro");

        try {
            getlistAll();

            if (!listAll.isEmpty()) {

                listAll.mergeSort("canton", 0);
                int izq = 0;
                int der = listAll.getSize() - 1;

                while (izq <= der) {
                    int mid = (izq + der) / 2;
                    Familia midFamilia = listAll.get(mid);
                    String canton = midFamilia.getCanton().toLowerCase();

                    if (canton.startsWith(texto.toLowerCase())) {
                        return midFamilia;
                    } else if (canton.compareTo(texto.toLowerCase()) < 0) {
                        izq = mid + 1;
                    } else {
                        der = mid - 1;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error durante la búsqueda: " + e.getMessage());
        }

        return null; // No se encontró coincidencia
    }

    public LinkedList<Familia> buscar_Apellido_Paterno_Binaria(String texto) {

        System.out.println("Estamos buscando por Metodo Lineal Binario AP");
        LinkedList<Familia> resultados = new LinkedList<>();

        try {
            this.getlistAll();

            if (!listAll.isEmpty()) {

                listAll.quickSort("apellidoPaterno", 0);
                int derecha = 0;
                int izquierda = listAll.getSize() - 1;

                while (derecha <= izquierda) {
                    int mid = (derecha + izquierda) / 2;
                    Familia midFamilia = listAll.get(mid);
                    String apellidoPaterno = midFamilia.getApellidoPaterno().toLowerCase();

                    if (apellidoPaterno.startsWith(texto.toLowerCase())) { // Añadimos elementos que coincidan hacia
                                                                           // ambos lados
                        int izqL = mid;
                        int derL = mid;
                        while (izqL >= 0
                                && listAll.get(izqL).getApellidoPaterno().toLowerCase()
                                        .startsWith(texto.toLowerCase())) {
                            resultados.addF(listAll.get(izqL--));
                        }
                        while (derL < listAll.getSize()
                                && listAll.get(derL).getApellidoPaterno().toLowerCase()
                                        .startsWith(texto.toLowerCase())) {
                            if (derL != mid)
                                resultados.add(listAll.get(derL));
                            derL++;
                        }
                        break;
                    } else if (apellidoPaterno.compareTo(texto.toLowerCase()) < 0) {
                        derecha = mid + 1;
                    } else {
                        izquierda = mid - 1;
                    }
                }
                return resultados.quickSort("id", 0);

            }

        } catch (

        Exception e) {
            System.out.println("Error " + e);
        }

        return resultados;

    }

    // BUSQUEDA ID BINARIA

    public Familia buscar_Id_Binaria(Integer id) {
        System.out.println("Estamos buscando por Metodo Binario Id");

        try {
            this.getlistAll();

            if (!listAll.isEmpty()) {

                listAll.mergeSort("id", 0);
                Familia[] listaOrdenada = listAll.toArray();
                int izquierda = 0;
                int derecha = listaOrdenada.length - 1;

                while (izquierda <= derecha) {
                    int medio = (derecha + izquierda) / 2;
                    Integer idMedio = listaOrdenada[medio].getId();

                    if (idMedio.equals(id)) {
                        return listaOrdenada[medio];
                    }

                    if (idMedio < id) {
                        izquierda = medio + 1;
                    } else {
                        derecha = medio - 1;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        return null;

    }
    // FIN BUSQUEDA NUMERICA BINARIA

    // Buscar Integrantes:
    // Busqueda Lineal Binaria

    public LinkedList<Familia> buscar_Integrantes_LinealBinaria(int integrantes) {
        System.out.println("Estamos buscando por Método Binario para integrantes");
        LinkedList<Familia> resultados = new LinkedList<>();
        try {
            this.getlistAll();

            if (!listAll.isEmpty()) {

                this.listAll.quickSort("integrantes", 0);
                Familia[] listaOrdenada = this.listAll.toArray();
                int izquierda = 0;
                int derecha = listaOrdenada.length - 1;

                while (izquierda <= derecha) {
                    int medio = (derecha + izquierda) / 2;
                    int integrantesMedio = listaOrdenada[medio].getIntegrantes();

                    if (integrantesMedio == integrantes) { // Añadimos elementos que coincidan hacia ambos lados
                        resultados.add(listaOrdenada[medio]);

                        int izqL = medio - 1;
                        int derL = medio + 1;

                        while (izqL >= 0 && listaOrdenada[izqL].getIntegrantes() == integrantes) {
                            resultados.addF(listaOrdenada[izqL]);
                            izqL--;
                        }

                        while (derL < listaOrdenada.length && listaOrdenada[derL].getIntegrantes() == integrantes) {
                            resultados.add(listaOrdenada[derL]);
                            derL++;
                        }

                        break;

                    } else if (integrantesMedio < integrantes) {
                        izquierda = medio + 1;
                    } else {
                        derecha = medio - 1;
                    }

                }
            }
            return resultados.quickSort("id", 0);

        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return resultados;
    }

    // Busqueda Binaria

    public Familia buscar_Integrantes_Binaria(Integer integrantes) {
        System.out.println("Estamos buscando por Metodo Binario integrantes");

        try {
            this.getlistAll();

            if (!listAll.isEmpty()) {

                listAll.quickSort("integrantes", 0);
                Familia[] listaOrdenada = listAll.toArray();
                int izquierda = 0;
                int derecha = listaOrdenada.length - 1;

                while (izquierda <= derecha) {
                    int medio = (derecha + izquierda) / 2;
                    Integer integrantesMedio = listaOrdenada[medio].getIntegrantes();

                    if (integrantesMedio.equals(integrantes)) {
                        return listaOrdenada[medio]; // Se encontró la coincidencia
                    }

                    if (integrantesMedio < integrantes) {
                        izquierda = medio + 1; // Buscar en la mitad derecha
                    } else {
                        derecha = medio - 1; // Buscar en la mitad izquierda
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        return null; // No se encontró la coincidencia

    }
    // FIN busqueda integrantes

    public Familia buscar_Id(Integer id) {
        Familia familia = new Familia();
        LinkedList<Familia> listita = listAll();
        if (!listita.isEmpty()) {
            Familia[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {

                if (aux[i].getId().equals(id)) {
                    familia = aux[i];
                    System.out.println(familia.getApellidoPaterno());
                    break;
                }
            }
        }
        return familia;
    }

    // PRUEBA BUSQUEDA

    public LinkedList<Familia> buscar_Integrantes_LinealBinaria_P(LinkedList<Familia> lista, int integrantes) {
        LinkedList<Familia> resultados = new LinkedList<>();
        try {
            this.getlistAll();

            if (!lista.isEmpty()) {

                lista.mergeSort("integrantes", 0);
                Familia[] listaOrdenada = lista.toArray();
                int izquierda = 0;
                int derecha = listaOrdenada.length - 1;

                while (izquierda <= derecha) {
                    int medio = (derecha + izquierda) / 2;
                    int integrantesMedio = listaOrdenada[medio].getIntegrantes();

                    if (integrantesMedio == integrantes) { // Añadimos elementos que coincidan hacia ambos lados
                        resultados.add(listaOrdenada[medio]);

                        int izqL = medio - 1;
                        int derL = medio + 1;

                        while (izqL >= 0 && listaOrdenada[izqL].getIntegrantes() == integrantes) {
                            resultados.addF(listaOrdenada[izqL]);
                            izqL--;
                        }

                        while (derL < listaOrdenada.length && listaOrdenada[derL].getIntegrantes() == integrantes) {
                            resultados.add(listaOrdenada[derL]);
                            derL++;
                        }
                        break;
                    } else if (integrantesMedio < integrantes) {
                        izquierda = medio + 1;
                    } else {
                        derecha = medio - 1;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return resultados;
    }

    public LinkedList<Familia> buscar_Integrantes_LinealBinaria_PL(LinkedList<Familia> listita, Integer valor) {
        System.out.println("Estamos buscando por Metodo Lineal");
        LinkedList<Familia> lista = new LinkedList<>();
        if (!listita.isEmpty()) {
            Familia[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {
                if (aux[i].getIntegrantes() == valor) {
                    lista.add(aux[i]);

                }
            }
        }
        return lista;
    }

    public LinkedList<Familia> duplicateLinkedList(LinkedList<Familia> lista) {
        LinkedList<Familia> nuevaLista = new LinkedList<>();
        Familia original = new Familia();
        Familia copia = new Familia();

        try {
            for (int i = 0; i < lista.getSize(); i++) {
                original = lista.get(i);
                copia = new Familia(original); // Uso constructor de Familia
                nuevaLista.add(copia);
            }
            return nuevaLista;
        } catch (Exception e) {
            System.out.println("Error dupli " + e);
        }
        return nuevaLista;

    }

}
