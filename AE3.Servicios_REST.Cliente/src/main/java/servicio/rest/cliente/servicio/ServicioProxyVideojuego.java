package servicio.rest.cliente.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import servicio.rest.cliente.entidad.Videojuego;


@Service
public class ServicioProxyVideojuego {
	//La URL base del servicio REST de videojuegos
		public static final String URL = "http://localhost:8089/videojuegos/";
		
		//Inyectamos el objeto de tipo RestTemplate que nos ayudara
		//a hacer las peticiones HTTP al servicio REST
		@Autowired
		private RestTemplate restTemplate;
		
		/**
		 * Metodo que obtiene un videojuego del servicio REST a partir de un id
		 * En caso de que el id no exita arrojaria una expcepcion que se captura
		 * para sacar el codigo de respuesta
		 * 
		 * @param id que queremos obtener
		 * @return retorna el videojuego que estamos buscando, null en caso de que el
		 * videojuego no se encuentre en el servidor (devuelva 404) o haya habido algun
		 * otro error.
		 */
		public Videojuego obtener(int id){
			try {
				//Como el servicio trabaja con objetos ResponseEntity, nosotros 
				//tambien podemos hacerlo en el cliente
				//Ej http://localhost:8089/videojuegos/1 GET
				ResponseEntity<Videojuego> re = restTemplate.getForEntity(URL + id, Videojuego.class);
				HttpStatus hs= re.getStatusCode();
				if(hs == HttpStatus.OK) {	
					//Si el videojuego existe viene en formato JSON en el body
					//Al ser el objeto ResponseEntity de tipo Videojuego, al obtener el 
					//body me lo convierte automaticamente a tipo Videojuego
					return re.getBody();
				}else {
					System.out.println("Respuesta no contemplada");
					return null;
				}
			}catch (HttpClientErrorException e) {
				System.out.println("obtener ->El videojuego NO se ha encontrado, id: " + id);
			    System.out.println("obtener -> Codigo de respuesta: " + e.getStatusCode());
			    return null;
			}
		}
		
		/**
		 * Metodo que da de alta un videojuego en el servicio REST
		 * 
		 * @param v el videojuego que vamos a dar de alta
		 * @return el videojuego con el id actualizado que se ha dado de alta en el
		 * servicio REST. Null en caso de que no se haya podido dar de alta
		 */
		public Videojuego alta(Videojuego v){
			try {
				//Para hacer un post de una entidad usamos el metodo postForEntity
				//El primer parametro la URL
				//El segundo parametro el videojuego que ira en body
				//El tercer parametro el objeto que esperamos que nos envie el servidor
				ResponseEntity<Videojuego> re = restTemplate.postForEntity(URL, v, Videojuego.class);
				System.out.println("alta -> Codigo de respuesta " + re.getStatusCode());
				return re.getBody();
			} catch (HttpClientErrorException e) {
				System.out.println("alta -> El videojuego NO se ha dado de alta, id: " + v);
			    System.out.println("alta -> Codigo de respuesta: " + e.getStatusCode());
			    return null;
			}
		}
		
		/**
		 * 
		 * Modifica un videojuego en el servicio REST
		 * 
		 * @param v el videojuego que queremos modificar, se hara a partir del 
		 * id por lo que tiene que estar relleno.
		 * @return true en caso de que se haya podido modificar el videojuego. 
		 * false en caso contrario.
		 */
		public boolean modificar(Videojuego v){
			try {
				//El metodo put de Spring no devuelve nada
				//si no da error se ha dado de alta y si no daria una 
				//excepcion
				restTemplate.put(URL + v.getId(), v, Videojuego.class);
				return true;
			} catch (HttpClientErrorException e) {
				System.out.println("modificar -> El videojuego NO se ha modificado, id: " + v.getId());
			    System.out.println("modificar -> Codigo de respuesta: " + e.getStatusCode());
			    return false;
			}
		}
		
		/**
		 * 
		 * Borra un videojuego en el servicio REST
		 * 
		 * @param id el id del videojuego que queremos borrar.
		 * @return true en caso de que se haya podido borrar el videojuego. 
		 * false en caso contrario.
		 */
		public boolean borrar(int id){
			try {
				//El metodo delete tampoco devuelve nada, por lo que si no 
				//ha podido borrar el id, daría un excepcion
				restTemplate.delete(URL + id);
				return true;
			} catch (HttpClientErrorException e) {
				System.out.println("borrar -> El videojuego NO se ha borrar, id: " + id);
			    System.out.println("borrar -> Codigo de respuesta: " + e.getStatusCode());
			    return false;
			}
		}
		
		/**
		 * Metodo que devuelve todos los videojuegos o todos los videojuegos filtrados
		 * por compañia del web service
		 * 
		 * @param compañia en caso de ser distinto de null, devolvera el listado
		 * filtrado por la compañia que le hayamos pasado en este parametro. En caso
		 * de que sea null, el listado de videojuegos sera completo
		 * @return el listado de videojuegos segun el parametro de entrada o 
		 * null en caso de algun error con el servicio REST
		 */
		public List<Videojuego> listar(String compañia){
			String queryParams = "";		
			if(compañia != null) {
				queryParams += "?compañia=" + compañia;
			}
			
			try {
				//Ej http://localhost:8089/videojuegos?compañia=BETHESDA GET
				ResponseEntity<Videojuego[]> response =
						  restTemplate.getForEntity(URL + queryParams,Videojuego[].class);
				Videojuego[] arrayVideojuegos = response.getBody();
				return Arrays.asList(arrayVideojuegos);//convertimos el array en un arraylist
			} catch (HttpClientErrorException e) {
				System.out.println("listar -> Error al obtener la lista de videojuegos");
			    System.out.println("listar -> Codigo de respuesta: " + e.getStatusCode());
			    return null;
			}
		}
	


}
