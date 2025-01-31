package controller.Dao.servicies;

import controller.tda.list.LinkedList;
import controller.Dao.graphDao.ArteUrbanoDao;
import controller.tda.graph.ArteUrbano;

public class ArteUrbanoServicies {
    private ArteUrbanoDao obj;

    public ArteUrbanoServicies() { // Constructor de la clase
        obj = new ArteUrbanoDao(); // Instancia un objeto de la clase ArteUrbanoDao
    }

    public Boolean update() throws Exception { // Guarda la variable arteUrbano en la lista de objetos
        return obj.update(); // Invoca el método save() de la clase ArteUrbanoDao
    }

    public Boolean save() throws Exception { // Guarda la variable arteUrbano en la lista de objetos
        return obj.save(); // Invoca el método save() de la clase ArteUrbanoDao
    }

    public LinkedList<ArteUrbano> listAll() { // Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll(); // Invoca el método getlistAll() de la clase ArteUrbanoDao

    }

    public ArteUrbano getArteUrbano() { // Obtiene la arteUrbano
        return obj.getArteUrbano(); // Invoca el método getArteUrbano() de la clase ArteUrbanoDao
    }

    public void setArteUrbano(ArteUrbano arteUrbano) { // Recibe un objeto ArteUrbano
        obj.setArteUrbano(arteUrbano); // Invoca el método setArteUrbano() de la clase ArteUrbanoDao y envía el objeto
        // ArteUrbano
    }

    public ArteUrbano get(Integer id) throws Exception { // Obtiene un objeto ArteUrbano por su id
        return obj.get(id); // Invoca el método get() de la clase ArteUrbanoDao y envía el id
    }

    public Boolean delete(int index) throws Exception { // Elimina un objeto ArteUrbano por su índice
        return obj.delete(index); // Invoca el método delete() de la clase ArteUrbanoDao y envía el índice
    }

}
