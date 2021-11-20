package servicio.rest.cliente;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import servicio.rest.cliente.entidad.Videojuego;
import servicio.rest.cliente.servicio.ServicioProxyVideojuego;


@SpringBootApplication
public class Application implements CommandLineRunner {
	//Primero inyectaremos todos los objetos que necesitamos para acceder a nuestro ServicioRest, el ServicioProxyVideojuego 
	
	@Autowired
	private ServicioProxyVideojuego spv;
	
	//Tambien necesitaremos acceder al contexto de Spring para parar la aplicacion, ya que esta app al ser una aplicacion web se
	//lanzará en un tomcat. De esta manera le decimos a Spring que nos inyecte su propio contexto.
	
	@Autowired
	private ApplicationContext context;
	
	//En este metodo daremos de alta un objeto de tipo RestTemplate que sera el objeto mas importante de esta aplicacion. Sera usado por los 
	//objetos ServicioProxy para hacer las peticiones HTTP a nuestro servicio REST. Como no podemos anotar la clase RestTemplate porque
	//no la hemos creado nosotros, usaremos la anotacion @Bean para decirle a Spring que ejecute este metodo y meta el objeto devuelto dentro
	//del contexto de Spring con ID "restTemplate" (el nombre del metodo)
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
			return builder.build();
		}
	
	//Metodo main que lanza la aplicacion.
	public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto de Spring");
		SpringApplication.run(Application.class, args);
		//Este metodo es estatico no podemos acceder a los metodos dinamicos de la clase, como el "spv"
		//Para solucionar esto, haremos que nuestra clase implemente "CommandLineRunner" e implementaremos el metodo "run"
		//Cuando se acabe de arrancar el contexto, se llamara automaticamente al metodo run
		
	}
	
	//Este metodo es dinamico por la tanto ya podemos acceder a los atributos
	//dinamicos
	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
		boolean continuar = true;
		String opcion;
		Videojuego vid= new Videojuego();
		int id, nota;
			
		do {
			
			System.out.println(" \nElige una opción entre las siguientes: \n" + "----> 1. Dar de alta un videojuego \n" + 
								"----> 2. Dar de baja un videojuego por ID. \n" + "----> 3. Modificar un videojuego por ID. \n" + 
								"----> 4. Obtener un videojuego por ID \n" + "----> 5. Listar todos los videojuegos \n" + 
								"----> 6. Salir \n");
			
			opcion = sc.nextLine(); //recoge el número que elegiremos en el menú anterior	.
			
			switch (opcion){
				case "1":		
					System.out.println("Añade el nombre");
					vid.setNombre(sc.nextLine());
					System.out.println("Añade la compañia");
					vid.setCompañia(sc.nextLine());;
					System.out.println("Añade la nota");
					nota= Integer.parseInt(sc.nextLine());
					vid.setNota(nota);;
					
					spv.alta(vid);
					
					break;
				case "2":
					System.out.println("Introduce el ID");
					id= Integer.parseInt(sc.nextLine());
					spv.borrar(id);
				
					break;
				case "3":
					
					System.out.println("Introduce el ID del juego a modificar");
					id= Integer.parseInt(sc.nextLine());
					vid.setId(id);
					System.out.println("Añade el nuevo el nombre");
					vid.setNombre(sc.nextLine());
					System.out.println("Añade la nueva compañia");
					vid.setCompañia(sc.nextLine());;
					System.out.println("Añade la nueva nota");
					nota= Integer.parseInt(sc.nextLine());
					vid.setNota(nota);;
					
					spv.modificar(vid);
					
					break;
				case "4":
					System.out.println("Introduce el ID del juego a consultar");
					id= Integer.parseInt(sc.nextLine());
					
					spv.obtener(id);
					
					break;
				case "5":
					
					System.out.println("Si quieres puedes filtar por compañía añadiendo el nombre de la compañia, si no, pulsa enter y saldrán todos");
					String compañia = sc.nextLine();
					spv.listar(compañia);
					
					break;	
					
				case "6":
					
					continuar = false;
					
					break;	
					
				default:
				
				System.out.println("Elige la opción escribiendo un número del menu, por favor \n");
				
				
			}
		}while(continuar);
		
		sc.close();
	}

	
	public void pararAplicacion() {
		//Esta aplicacion levanta un servidor web, por lo que tenemos que darle la orden de pararlo cuando acabemos. Para ello usamos el metodo exit, 
		//de la clase SpringApplication, que necesita el contexto de Spring y un objeto que implemente la interfaz ExitCodeGenerator. 
		//Podemos usar la funcion lambda "() -> 0" para simplificar 
		
		SpringApplication.exit(context, () -> 0);
		
		//Podriamos hacerlo tambien de una manera clasica, es decir, creando la clase anonima a partir de la interfaz. 
		//El codigo de abajo sería equivalente al de arriba (pero mucho más largo)
		/*
		SpringApplication.exit(context, new ExitCodeGenerator() {
			
			@Override
			public int getExitCode() {
				return 0;
			}
		});*/
	}


	
}
