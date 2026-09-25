# Filmout

### Grupo 02 Proyecto para Sistemas Distribuidos.

---

## Carlos Martin Garcia

## Anass Chikou El Mahraoui

## Manuel Alos de la Vega

---

Universidad Rey Juan Carlos I. Tercero de Ingeniería Informática. Campus de Vicálvaro
---

<!-- INSERTA AQUÍ LA URL DE LA IMAGEN DE LA PANTALLA PRINCIPAL -->
<img width="100%" alt="Pantalla principal de Filmout" src="URL_DE_TU_IMAGEN_AQUI" />

> [Vista principal de la aplicación web Filmout]

<!-- INSERTA AQUÍ LA URL DE OTRA IMAGEN RELEVANTE (Ej: Vista de eventos o diagrama de BD) -->
<img width="100%" alt="Vista de eventos" src="URL_DE_TU_IMAGEN_AQUI" />

> [Prototipo digital y vista de organización de quedadas cinematográficas]

---

## Sobre el proyecto

Filmout es una aplicación web que sirve para organizar eventos de quedadas para ver películas. El sistema permite a los usuarios registrarse, crear eventos en diferentes cines, unirse a eventos creados por otros usuarios, consultar información sobre películas y dejar reseñas o valoraciones sobre las mismas. 

**Nota importante sobre la Base de Datos:** Actualmente la web soporta una base de datos online (MySQL a través de Aiven) configurada para el entorno de producción, pero esta solo estará activa hasta el **31 de octubre de 2026**. Por lo que también se ha implementado en el `pom.xml` y se ha dejado comentada la implementación de la base de datos H2 en local. Si se desea probar el proyecto después de esa fecha, bastará con descomentar dicha dependencia para hacerlo funcionar localmente.

---
## Diseño Inicial y Planificación

Antes de comenzar con el desarrollo del código, se elaboraron una serie de bocetos y diagramas de secuencia para definir la arquitectura visual y el flujo de interacciones del usuario con la plataforma. Estos esquemas sirvieron como guía principal durante todo el proceso de implementación.

### Wireframes de la Interfaz

El diseño inicial contemplaba una estructura muy clara dividida en dos ventanas principales:

*   **Ventana de Inicio (Movies):** Diseñada exclusivamente para visualizar el catálogo de películas disponibles. Desde aquí, el usuario puede explorar las opciones y acceder a la creación de eventos.
*   **Ventana de Eventos (Events):** Un panel de control personalizado donde el usuario puede gestionar su actividad social. Se divide en los eventos que ha creado (con permisos para modificarlos o eliminarlos) y los eventos a los que se ha unido (con la opción de abandonarlos).

<!-- INSERTA AQUÍ LA URL DE LA IMAGEN DE LOS BOCETOS (image_9cb145.png) -->
<img width="100%" alt="Bocetos iniciales de las ventanas Movies y Events" src="URL_DE_TU_IMAGEN_AQUI" />

> [Bocetos conceptuales de la distribución de la interfaz de usuario]

### Diagramas de Secuencia

Para asegurar una correcta comunicación entre el frontend, el backend y la base de datos, se plantearon los flujos de las operaciones clave del sistema a través de diagramas de secuencia:

#### Gestión de Eventos (Creación, Modificación y Eliminación)

*   **Creación de evento:** El flujo comienza cuando el usuario selecciona una película. El sistema consulta la base de datos de cines para ofrecer las localizaciones disponibles. Tras introducir lugar, fecha, aforo y descripción, el evento queda registrado.
*   **Modificación:** Sigue un patrón similar a la creación, permitiendo actualizar los detalles de un evento existente, siempre y cuando el usuario sea el creador del mismo.
*   **Eliminación:** El usuario creador solicita borrar el evento, y el gestor ejecuta la orden directamente sobre la base de datos de eventos, confirmando la acción.

<!-- INSERTA AQUÍ LA URL DE LA IMAGEN DE CREACIÓN (image_9cb160.png) -->
<img width="100%" alt="Diagrama de secuencia: Crear un evento" src="URL_DE_TU_IMAGEN_AQUI" />

> [Flujo de interacción para la creación de un nuevo evento]

<!-- INSERTA AQUÍ LAS URL DE LAS IMÁGENES DE ELIMINAR Y MODIFICAR (image_9cb165.png y image_9cb181.png) -->
<img width="100%" alt="Diagrama de secuencia: Eliminar y modificar un evento" src="URL_DE_TU_IMAGEN_AQUI" />

> [Flujo de interacción para la eliminación y modificación de eventos]

#### Interacción Social (Unirse y Salirse)

*   **Unirse a un evento:** El gestor filtra y muestra al usuario únicamente aquellos eventos que aún no han alcanzado su aforo máximo. Una vez seleccionado, el usuario se añade a la lista de asistentes.
*   **Salirse de un evento:** Una operación directa donde el usuario indica el evento que desea abandonar, y el sistema lo elimina de la lista de asistentes correspondientes en la base de datos.

<!-- INSERTA AQUÍ LAS URL DE LAS IMÁGENES DE UNIRSE Y SALIRSE (image_9cb187.png y image_9cb1a2.png) -->
<img width="100%" alt="Diagramas de secuencia: Unirse y salirse de un evento" src="URL_DE_TU_IMAGEN_AQUI" />

> [Flujo de interacción para la gestión de asistencia a eventos]
--- 

## Carpetas de este repositorio

### src/main/java/group02/filmout

Carpeta la cual contiene todos los códigos backend desarrollados en Java con Spring Boot. No hay fotos ni explicaciones de diseño visual aquí, solo la arquitectura interna. 
En este código se encuentra la lógica y el funcionamiento final del proyecto Filmout. Una vez se tenga el servidor arrancado, este código hace que funcione tal y como se espera, dividiendo el sistema en Controladores (MVC y API REST), Servicios, Repositorios y Entidades.

### src/main/resources/templates y static

Contiene los planos visuales y estructurales para nuestra web. En la carpeta `templates` se encuentran los archivos HTML integrados con el motor Mustache. En la carpeta `static` se alojan los archivos CSS y JS. Si se deseara modificar el diseño visual o la interactividad del proyecto, se recomienda alterar estos archivos.

### postman

Carpeta que contiene las colecciones y variables exportadas de Postman. Son archivos JSON y YAML para experimentar el correcto funcionamiento de cada módulo de la API REST (usuarios, cines, películas, eventos y reseñas) por separado, simulando peticiones HTTP externas.

### application.properties y schema.sql

Archivos de configuración ubicados en `src/main/resources`. `schema.sql` contiene el diseño digitalizado de las tablas de nuestra base de datos. El archivo de propiedades contiene las credenciales de conexión al servidor online y las claves de APIs externas (como TMDB) necesarias para poblar el catálogo de películas.

<img width="1468" height="756" alt="image" src="https://github.com/user-attachments/assets/7afa717b-5f09-4bb2-9563-81b11dbad6f1" />

<img width="1514" height="735" alt="image" src="https://github.com/user-attachments/assets/2bdd3f1e-6044-4f91-8b56-431837d215fa" />

<img width="1085" height="526" alt="image" src="https://github.com/user-attachments/assets/ddeff42a-e7fa-4c31-9e13-13cec19e2da0" />

<img width="1484" height="696" alt="image" src="https://github.com/user-attachments/assets/9b73fa65-76ca-48bb-a845-7345c327f520" />

<img width="1092" height="621" alt="image" src="https://github.com/user-attachments/assets/ecb39dd0-20cc-4072-acd3-f891690e39d8" />

<img width="1077" height="496" alt="image" src="https://github.com/user-attachments/assets/76f480ef-7fe6-4016-8819-fb35aa7641ad" />
