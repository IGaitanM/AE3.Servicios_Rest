# Actividad 3 - Sevicios REST

### Miembros del grupo:

- [Iván Gaitán Muñoz](https://github.com/IGaitanM)
- [Luz Maria Lozano Asimbaya](https://github.com/luzlozas)
- [Miguel Pérez Larren](https://github.com/miguelperezlarren)
- [Guillermo Pérez Arias](https://github.com/guiller91)

## REQUISITOS

# Requerimiento 1
Se pide hacer un servicio REST, dicho servicio gestionará una serie de Videojuegos.

Los videojuegos tendrán un ID, un nombre, una compañía y una nota. Los videojuegos se encontrarán alojados en el servidor REST. Dicho servidor cuando arranque tendrá 5 videojuegos preestablecidos con todos los datos rellenos. Los videojuegos se guardarán en memoria en cualquier tipo de estructura de datos (como puede ser un lista).

El servicio REST proporcionará un servicio CRUD completo, y podrá ser consumido mediante un cliente como Postman y todo el intercambio de mensajes se hará a través de JSON.

Además, se pide a través de Postman realizar las siguientes tareas y apreciar los resultados:

    Dar de alta un videojuego
    Dar de baja un videojuego por ID
    Modificar un videojuego por ID
    Obtener un videojuego por ID
    Listar todos los videojuegos


# Requerimiento 2

Se pide que no pueda haber dos videojuegos con id o nombre repetido



# Requerimiento 3

Se pide crear una aplicación java que sea capaz de trabajar con el servidor REST de videojuegos. La aplicación mostrara un menú que sea capaz de realizar todas las operaciones del servidor, como puede ser:

    Dar de alta un videojuego
    Dar de baja un videojuego por ID
    Modificar un videojuego por ID
    Obtener un videojuego por ID
    Listar todos los videojuegos
    Salir

Para cada opción, se tendrá que pedir los datos necesarios al usuario y/o mostrar los resultados pertinentes. La aplicación se ejecutará hasta que se pulse la opción de “salir”.