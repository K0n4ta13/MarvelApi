# Documentación del Proyecto Marvel API

## Descripción
Este proyecto **tiene como objetivo mostrar cómo se evaluan las habilidades de un desarrollador en la implementación de APIs, el manejo de bases de datos y la autenticación de usuarios** y este proyecto es una aplicación que interactúa con la API de Marvel para obtener información sobre personajes y cómics. A continuación, se proporciona una descripción general de las principales componentes y funcionalidades del proyecto.

- Documento de prueba técnica: [click aquí](./src/main/resources/Pruebatécnica%20para%20desarrollador%20backend%20v1%20-%20davivienda.docx)

## Características
El proyecto de prueba técnica para desarrollador backend consiste en el desarrollo de una API **con tecnología Java utilizando el framework Spring Boot**. Esta API tiene como objetivo consumir la API de Marvel, cuya documentación se encuentra en **[este enlace](https://developer.marvel.com/)**. A continuación, se detallan las principales características de este proyecto:

1. **Consumo de la API de Marvel**: La aplicación desarrollada se encarga de consumir la API de Marvel, obteniendo información relevante sobre personajes, cómics, series y más.

2. **Almacenamiento en Base de Datos**: Se implementa un esquema de base de datos, preferiblemente MySQL, para almacenar toda la información necesaria. Esto incluye datos relacionados con personajes, cómics y series.

3. **API de Suministro de Información**: Se crea una API que ofrece diversas funcionalidades para obtener información de Marvel, incluyendo:
    - Búsqueda de personajes de Marvel por nombre, historietas y series.
    - Obtención del listado de cómics asociados a un personaje específico.
    - Acceso a la imagen y descripción de un personaje en particular.
    - Listado completo de cómics disponibles.
    - Filtrado de cómics por identificador.
    - Visualización de búsquedas relacionadas a historietas realizadas por cualquier usuario.
    - Registro de búsquedas específicas de un usuario particular.

5. **Autenticación con Spring Security**: La API implementa un medio y esquema de autenticación utilizando Spring Security. Esto permite identificar qué usuario está realizando búsquedas de información de Marvel.

6. **Scripts de Base de Datos**: El archivo README de la aplicación incluye scripts de base de datos, que contienen datos iniciales necesarios para ejecutar las APIs en su primer uso.


## Configuración
Antes de ejecutar la aplicación, es necesario configurar las siguientes propiedades en el archivo `application.properties`:

```properties
integration.marvel.public-key=<Tu clave pública de Marvel>
integration.marvel.private-key=<Tu clave privada de Marvel>
```
Asegúrate de obtener estas claves de la API de Marvel ([click para obtener](https://developer.marvel.com/)) y reemplazar `<Tu clave pública de Marvel>` y `<Tu clave privada de Marvel>` con tus propias claves.


Además se debe de configurar en el archivo application-dev.properties las propiedades correspondientes para MySQL:
```properties
spring.datasource.url=jdbc:mysql://<Tu host>:<Tu puerto>/<Tu base de datos>
spring.datasource.username=<Tu username>
spring.datasource.password=<Tu password>
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
```

