package servicio.rest.modelo.persistecia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import servicio.rest.modelo.entidad.Videojuego;


/**
 * Patron DAO (Data Access Object), objeto que se encarga de hacer las consultas
 * a algun motor de persistencia (BBDD, Ficheros, etc). En este caso vamos a 
 * simular que los datos estan guardados en una BBDD trabajando con una lista
 * de objetos cargada en memoria para simplificar el ejemplo.
 * 
 * Hay que tener en cuenta que para simplificar el ejemplo tambien se ha hecho
 * que el ID con el que se dan de alta las personas en la lista coincide exactamente
 * con la posicion del array que ocupan.
 * 
 * Mediante la anotacion @Component, damos de alta un unico objeto de esta clase
 * dentro del contexto de Spring, su ID sera el nombre de la case en notacion
 * lowerCamelCase
 * 
 */
@Component
public class DaoVideojuego {

	public List<Videojuego> listaVideojuegos;
	public int contador;
	
	/**
	 * Cuando se cree el objeto dentro del contexto de Spring, se ejecutara
	 * su constructor, que creara los videojuegos y los metera en una lista
	 * para que puedan ser consumidos por nuestros clientes
	 */
	public DaoVideojuego() {
		System.out.println("DaoVideojuego -> Creando la lista de videojuegos!");
		listaVideojuegos = new ArrayList<Videojuego>();
		Videojuego v1 = new Videojuego(contador++,"FFVII", "SQUARE", 99);//ID: 0
		Videojuego v2 = new Videojuego(contador++,"GOTHIC", "PIRANHA BYTES", 95);//ID: 1
		Videojuego v3 = new Videojuego(contador++,"FALLOUT 4", "BETHESDA", 96);//ID: 2
		Videojuego v4 = new Videojuego(contador++,"SKYRIM", "BETHESDA", 98);//ID:3
		Videojuego v5 = new Videojuego(contador++,"THE WITCHER 3", "CD PROJEKT RED", 94);//ID:4
		listaVideojuegos.add(v1);
		listaVideojuegos.add(v2);
		listaVideojuegos.add(v3);
		listaVideojuegos.add(v4);
		listaVideojuegos.add(v5);
	}
	
	/**
	 * Devuelve un videojuego a partir de su posicion del array que es igual a su ID
	 * @param posicion la posicion del arrya que buscamos
	 * @return el videojuego que ocupe en la posicion del array, null en caso de
	 * que no exista o se haya ido fuera de rango del array
	 */
	public Videojuego get(int posicion) {
		try {
			return listaVideojuegos.get(posicion);
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("Videojuego fuera de rango");
			return null;
		}
	}
	
	/**
	 * Metodo que devuelve todos los videojuegos del array
	 * @return una lista con todos los videojuegos del array
	 */
	public List<Videojuego> list() {
		return listaVideojuegos;
	}
	
	/**
	 * Metodo que introduce un videojuego al final de la lista. A través de un bucle for
	 * recorremos listaVideojuegos y en caso de que el nombre que introduzcamos coincida
	 * con un nombre dentro de la lista, devolveremos "nombre repetido, escribe otro".
	 * @param v el videojuego que queremos introducir
	 * 
	 */
	public String add(Videojuego v) {
		for (Videojuego vid : listaVideojuegos) {
			if (vid.getNombre().equalsIgnoreCase(v.getNombre())) {
				System.out.println("nombre repetido, escribe otro");
				return null;
			}
		}
		v.setId(contador++);
		listaVideojuegos.add(v);
		return "videojuego añadido";
	}
	
	/**
	 * Borramos un videojuego de una posicion del array
	 * @param posicion la posicion a borrar
	 * @return devolvemos el videojuego que hemos quitado del array, 
	 * o null en caso de que no exista.
	 */
	public Videojuego delete(int posicion) {
		try {
			return listaVideojuegos.remove(posicion);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("delete -> Videojuego fuera de rango");
			return null;
		}
	}
	
	/**
	 * Metodo que modifica un videojuego de una posicion del array
	 * @param v contiene todos los datos que queremos modificar, pero 
	 * v.getId() contiene la posicion del array que queremos eliminar
	 * @return el videojuego modificado en caso de que exista, null en caso
	 * contrario
	 */
	public Videojuego update(Videojuego v) {
		try {
			Videojuego pAux = listaVideojuegos.get(v.getId());
			pAux.setNombre(v.getNombre());
			pAux.setCompañia(v.getCompañia());
			pAux.setNota(v.getNota());

			return pAux;
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("update -> Videojuego fuera de rango");
			return null;
		}
	}
	
	/**
	 * Metodo que devuelve todas los videojuegos por nombre. Como puede
	 * haber varios videojuegos con el mismo nombre tengo que
	 * devolver una lista con todas las encontradas
	 * @param nombre representa el nombre por el que vamos a hacer la
	 * busqueda
	 * @return una lista con las personas que coincidan en el nombre.
	 * La lista estará vacia en caso de que no hay coincidencias
	 */
	public List<Videojuego> listByCompañia(String compañia){
		List<Videojuego> listaVideojuegosAux = new ArrayList<Videojuego>();
		for(Videojuego v : listaVideojuegos) {
			if(v.getCompañia().contains(compañia)) {
				listaVideojuegosAux.add(v);
			}
		}
		return listaVideojuegosAux;
	}
}
