# Filmout

### Grupo 02 Proyecto para Sistemas Distribuidos.

---

## Carlos Martin Garcia

## Edward Andrei Radoi

## Anass Chikou El Mahraoui

## Manuel Alos de la Vega

---

Universidad Rey Juan Carlos I. Tercero de Ingeniería Informática. Campus de Vicálvaro
---

<img width="1462" height="755" alt="Pantalla principal de Filmout" src="https://github.com/user-attachments/assets/bf20425c-425c-4c55-ba7d-9cc82a3e0328" />

> [Vista principal de la aplicación web Filmout]

<img width="1462" height="755" alt="Pantalla de vista de eventos" src="https://github.com/user-attachments/assets/ae100fde-b92f-4e13-b3e3-a87ea2cde774" />

> [Prototipo digital y vista de organización de quedadas cinematográficas]

---

## Sobre el proyecto

Filmout es una página web que sirve para organizar quedadas para ver películas. El sistema permite a los usuarios registrarse, crear eventos en diferentes cines, unirse a eventos creados por otros usuarios, consultar información sobre películas y dejar reseñas sobre las mismas. 

**Nota sobre la Base de Datos:** Actualmente la web soporta una base de datos online de MySQL, pero esta solo estará activa hasta el **31 de octubre de 2026**. Por lo que también se ha implementado en el `pom.xml` y se ha dejado comentada la implementación de la base de datos H2 en local. Si se desea probar el proyecto después de esa fecha, bastará con descomentar dicha dependencia para hacerlo funcionar localmente.

---
## Diseño Inicial y Planificación

Antes de comenzar con el desarrollo del código, se elaboraron una serie de bocetos y diagramas de secuencia para definir la arquitectura visual y el flujo de interacciones del usuario con la plataforma. Estos esquemas sirvieron como guía principal durante fases iniciales del proceso de implementación.

### Conexiones en la Interfaz

El diseño inicial contemplaba una estructura clara dividida en dos ventanas principales:

*   **Ventana de Inicio:** Diseñada para visualizar el catálogo de películas disponibles. Desde aquí, el usuario puede explorar las opciones y acceder a la creación de eventos.
*   **Ventana de Eventos:** Un panel de control personalizado donde el usuario puede gestionar su actividad. Se divide en los eventos que ha creado y los eventos a los que se ha unido.

<img width="1468" height="756" alt="Bocetos iniciales de las ventanas Movies y Events" src="https://github.com/user-attachments/assets/7afa717b-5f09-4bb2-9563-81b11dbad6f1" />

> [Bocetos conceptuales de la distribución de la interfaz de usuario]

### Diagramas de Secuencia

Para asegurar una correcta comunicación entre el frontend, el backend y la base de datos, se plantearon los flujos de las operaciones clave del sistema a través de diagramas de secuencia:

#### Gestión de Eventos: Creación, Modificación y Eliminación

*   **Creación de evento:** El flujo comienza cuando el usuario selecciona una película. El sistema consulta la base de datos de cines para ofrecer las localizaciones disponibles. Tras introducir lugar, fecha, aforo y descripción, el evento queda registrado.
*   
*   **Modificación:** Sigue un patrón similar a la creación, permitiendo actualizar los detalles de un evento existente, siempre y cuando el usuario sea el creador del mismo.
*   
*   **Eliminación:** El usuario creador solicita borrar el evento, y el gestor ejecuta la orden directamente sobre la base de datos de eventos, confirmando la acción.


<img width="1514" height="735" alt="Diagrama de secuencia: Crear un Evento" src="https://github.com/user-attachments/assets/2bdd3f1e-6044-4f91-8b56-431837d215fa" />


> [Flujo de interacción para la creación de un nuevo evento]


<img width="1085" height="526" alt="Diagrama de secuencia: Eliminar" src="https://github.com/user-attachments/assets/ddeff42a-e7fa-4c31-9e13-13cec19e2da0" />

<img width="1484" height="696" alt="Diagrama de secuencia: Modificar" src="https://github.com/user-attachments/assets/9b73fa65-76ca-48bb-a845-7345c327f520" />


> [Flujo de interacción para la eliminación y modificación de eventos]

#### Interacción: Unirse y Salirse

*   **Unirse a un evento:** El gestor filtra y muestra al usuario únicamente aquellos eventos que aún no han alcanzado su aforo máximo. Una vez seleccionado, el usuario se añade a la lista de asistentes.
*   **Salirse de un evento:** Una operación directa donde el usuario indica el evento que desea abandonar, y el sistema lo elimina de la lista de asistentes correspondientes en la base de datos.

<img width="1092" height="621" alt="Unirse a un evento" src="https://github.com/user-attachments/assets/ecb39dd0-20cc-4072-acd3-f891690e39d8" />

<img width="1077" height="496" alt="Salirse de un evento" src="https://github.com/user-attachments/assets/76f480ef-7fe6-4016-8819-fb35aa7641ad" />

> [Flujo de interacción para la gestión de asistencia a eventos]
--- 
## Flujo y funcionamiento final

Previamente hemos especificado unos flujos de eventos que usamos para comenzar nuestro desarrollo, pero la versión final de la página web tiene ciertos cambios con respecto a la idea original, además originalmente no se concibió la función de escribir reseñas

### Pantalla principal

En la versión final, la pantalla principal mantiene varias ideas de los bocetos originales. El flujo comienza cuando seleccionas una película, para escribir una reseña se ha de cumplir el requisito de tener la sesión iniciada, si no se ha iniciado se podrá iniciar desde esta pantalla o incluso crear la cuenta, lo mismo si se clica en el botón de crear evento. Una vez cumplido este requisito, se podrá escribir una reseña y dejar una puntuación de 0 a 5 estrellas, esta reseña se podrá editar y eliminar desde la misma pestaña de la página.

Por otro lado, para crear un evento se podrá hacer desde esta pantalla y su proceso es idéntico a como se pensó en un principio.

### Pantalla de Eventos

En esta pantalla se puede consultar los eventos que has creado y los eventos publicos a los que te puedes unir, desde aquí se puede gestionar el eliminar eventos, o entrar o salirse a un evento público.

---
## Carpetas y ficheros destacadas de este repositorio

### filmout

Carpeta la cual contiene todos los códigos desarrollados en Java con Spring Boot. En este código se encuentra la lógica y el funcionamiento final del proyecto Filmout. Una vez se tenga el servidor arrancado, este código hace que funcione tal y como se espera, dividiendo el sistema en Controladores, Servicios, Repositorios y Entidades.

### templates y static

Contiene los planos visuales y estructurales para nuestra web. En la carpeta `templates` se encuentran los archivos HTML. En la carpeta `static` se alojan los archivos CSS y JS. Si se deseara modificar el diseño visual o la interactividad del proyecto, se deberían alterar estos archivos.

### postman

Carpeta que contiene las variables exportadas de Postman. Son archivos JSON y YAML para experimentar el correcto funcionamiento de cada módulo de la API REST por separado, simulando peticiones HTTP externas.

### application.properties y schema.sql

Archivos de configuración ubicados en `resources`. `schema.sql` contiene el diseño digitalizado de las tablas de nuestra base de datos. El archivo de propiedades contiene las credenciales de conexión al servidor online y la clave API externa de TMDB necesarias para poblar el catálogo de películas.






