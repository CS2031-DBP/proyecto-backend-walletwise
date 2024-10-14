[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/42utwHoA)

---
# Proyecto: WalletWise 💳
![text (3)](https://github.com/user-attachments/assets/e7c2bb85-86dc-47f9-9811-ade6cf120054)

## Curso: CS 2031 Desarrollo Basado en Plataforma

### Integrantes:
- Adrian Aaron Urbina Mendoza
- Alejandro Mateo Ruiz Torres
- Adriana Celeste Sevillano Común
- Carla Alejandra Quispe Meza

---

## Índice
1. [Introducción](#introducción)
2. [Identificación del Problema o Necesidad](#identificación-del-problema-o-necesidad)
3. [Descripción de la Solución](#descripción-de-la-solución)
4. [Modelo de Entidades](#modelo-de-entidades)
5. [Testing y Manejo de Errores](#testing-y-manejo-de-errores)
6. [Medidas de Seguridad Implementadas](#medidas-de-seguridad-implementadas)
7. [Eventos y Asincronía](#eventos-y-asincronía)
8. [GitHub](#github)
9. [Conclusión](#conclusión)
10. [Apéndices](#apéndices)

---

## Introducción
### Contexto:
Nuestro sistema de gestión financiera "WalletWise" surge como una solución integral para que los usuarios puedan manejar sus finanzas personales de manera eficiente y controlada. La plataforma no solo permite registrar transacciones financieras de forma simple, sino que también integra una serie de funcionalidades avanzadas que convierten el manejo del dinero en una experiencia dinámica y completamente automatizada. 

El sistema permite a los usuarios crear y gestionar cuentas financieras, controlar sus presupuestos, categorizar sus ingresos y gastos, y recibir alertas personalizadas cuando se acercan a sus límites financieros. A diferencia de soluciones tradicionales, hemos optado por un enfoque en tiempo real, donde cada transacción realizada actualiza de inmediato el estado del presupuesto y genera notificaciones automáticas vía correo electrónico.

### Objetivos del Proyecto:
- Desarrollar una aplicación que permita a los usuarios gestionar de manera eficiente sus finanzas personales.
- Proporcionar funcionalidades para registrar, categorizar y monitorear transacciones financieras.
- Implementar presupuestos con alertas para que los usuarios controlen mejor sus gastos y no excedan sus límites financieros.
- Ofrecer una experiencia segura mediante autenticación JWT y roles diferenciados para usuarios y administradores.
- Enviar notificaciones por correo para que los usuarios se mantengan informados sobre sus movimientos financieros.
- Facilitar la categorización de transacciones para un análisis detallado de los ingresos y gastos.
  
---

## Identificación del Problema o Necesidad
### Descripción del Problema:
Muchos usuarios tienen dificultades para gestionar sus finanzas de manera eficiente, lo que puede llevar a un descontrol en sus gastos y, en algunos casos, a endeudamiento. Existen pocas herramientas personalizadas que ofrezcan un seguimiento preciso de los presupuestos asignados y las transacciones realizadas en tiempo real, dificultando el análisis de gastos e ingresos.

### Justificación:
La solución es importante porque ofrece a los usuarios una plataforma integral de gestión financiera que, además de registrar sus transacciones, les permite establecer presupuestos y recibir alertas antes de alcanzar sus límites de gasto. Además, ofrece una categorización detallada de ingresos y gastos, lo que ayuda a los usuarios a tomar decisiones informadas sobre sus finanzas. Esta plataforma también facilita el monitoreo de cuentas bancarias y el manejo de presupuestos, mejorando la salud financiera del usuario.

---

## Descripción de la Solución
### Funcionalidades Implementadas:
- **Gestión de Cuentas:** Los usuarios pueden crear y gestionar múltiples cuentas financieras (corriente, ahorro, inversión) y realizar un seguimiento de sus saldos.
- **Registro y Categorización de Transacciones:** Cada transacción se puede asociar a una cuenta específica y a una categoría (como Alimentación, Transporte, etc.), lo que facilita la organización de los gastos y los ingresos.
- **Asignación de Ítems en Transacciones:** Las transacciones permiten desglosar ítems, lo que ofrece un nivel de detalle más alto para el análisis financiero.
- **Presupuestos Personalizados:** Los usuarios pueden definir presupuestos para cada categoría de gasto y recibir alertas cuando están cerca de superar su límite mensual.
- **Notificaciones por Correo Electrónico:** Cada vez que se registra una transacción, el usuario recibe un correo con información detallada de su transacción y una actualización sobre su presupuesto.
- **Autenticación Segura con JWT:** El sistema asegura las cuentas de usuario mediante autenticación con JWT, protegiendo la información personal y financiera.


### Tecnologías Utilizadas:
- **Backend:** Java con Spring Boot para el desarrollo del backend, gestionando la lógica de negocio y las APIs REST.
- **Base de Datos:** PostgresSQL para almacenar datos de usuarios, cuentas, transacciones y presupuestos.
- **Autenticación JWT:** Para asegurar que solo los usuarios autenticados puedan acceder a las funcionalidades del sistema.
- **Correos Electrónicos:** Gmail API para el envío de notificaciones por correo.
- **Herramientas de Desarrollo:** Postman para pruebas de APIs, y Git para control de versiones.

---

## Modelo de Entidades
### Diagrama de Entidades:
![image](https://github.com/user-attachments/assets/9959c941-b9bb-4f3c-afe1-3ba8817c4581)

### Descripción de Entidades:
## **Entidades del Proyecto**

### Autenticación de Usuarios

| Método | Endpoint         | Roles Autorizados  | Descripción                                  |
|--------|------------------|--------------------|----------------------------------------------|
| POST   | /auth/register    | Público            | Registra un nuevo usuario en el sistema.     |
| POST   | /auth/login       | Público            | Inicia sesión y genera un token JWT.         |

### **1. Usuario**

**Descripción:**  
Representa a los usuarios de la aplicación. Cada usuario puede tener múltiples cuentas, presupuestos, transacciones, etc.

**Atributos Clave:**
- **id:** Identificador único del usuario.
- **nombre:** Nombre completo del usuario.
- **email:** Correo electrónico único del usuario.
- **password:** Contraseña encriptada para autenticación.
- **fechaRegistro:** Fecha en que el usuario se registró.
- **role:** Rol del usuario (`USER`, `ADMIN`).

**Relaciones:**
- **OneToMany** con **Cuenta:** Un usuario puede tener múltiples cuentas.
- **OneToMany** con **Presupuesto:** Un usuario puede tener múltiples presupuestos.
- **OneToMany** con **Reporte:** Un usuario puede generar múltiples reportes.

**Propósito:**  
Gestionar la autenticación y autorización, así como mantener la información personal y financiera de cada usuario.

**ENDPOINTS:**
| Método | Endpoint                       | Roles Permitidos         | Descripción                                         |
|--------|--------------------------------|-----------------|-----------------------------------------------------|
| POST   | /api/usuarios/crear            | PUBLICO         | Crea un nuevo usuario en el sistema.                |
| POST   | /api/usuarios/admin/crear      | ADMIN           | Crea un nuevo usuario con rol de administrador.     |
| GET    | /api/usuarios/{id}             | USER/ADMIN      | Obtiene los detalles de un usuario específico por ID.|
| GET    | /api/usuarios/email/{email}     | USER/ADMIN      | Obtiene los detalles de un usuario por su email.    |
| GET    | /api/usuarios/listar           | ADMIN           | Lista todos los usuarios del sistema.               |
| PUT    | /api/usuarios/actualizar/{id}   | ADMIN           | Actualiza los detalles de un usuario existente por ID.|
| DELETE | /api/usuarios/eliminar/{id}     | ADMIN           | Elimina un usuario específico por ID.               |


---

### **2. Categoría**

**Descripción:**  
Clasifica los ingresos y gastos en diferentes tipos para facilitar la organización y el análisis financiero.

**Atributos Clave:**
- **id:** Identificador único de la categoría.
- **nombre:** Nombre de la categoría (e.g., "Salario", "Alquiler").
- **descripcion:** Descripción detallada de la categoría.
- **tipo:** Tipo de categoría (`INGRESO`, `GASTO`).

**Relaciones:**
- **OneToMany** con **Subcategoria:** Una categoría puede tener múltiples subcategorías.
- **OneToMany** con **Transaccion:** Una categoría puede estar asociada a múltiples transacciones.
- **OneToMany** con **Presupuesto:** Una categoría puede estar asociada a múltiples presupuestos.

**Propósito:**  
Organizar los diferentes tipos de ingresos y gastos para facilitar la gestión y el análisis financiero.

**ENDPOINTS:**
| Método | Endpoint                | Roles Permitidos        | Descripción                                         |
|--------|-------------------------|-----------------|-----------------------------------------------------|
| POST   | /api/categorias         | USER            | Crea una nueva categoría en el sistema.             |
| GET    | /api/categorias         | ADMIN           | Lista todas las categorías disponibles.             |
| GET    | /api/categorias/{id}    | USER/ADMIN      | Obtiene los detalles de una categoría específica por ID. |
| PUT    | /api/categorias/{id}    | USER/ADMIN      | Actualiza una categoría existente por ID.           |
| DELETE | /api/categorias/{id}    | ADMIN           | Elimina una categoría específica por ID.            |

---

### **3. Subcategoria**

**Descripción:**  
Proporciona una clasificación más detallada dentro de una categoría principal.

**Atributos Clave:**
- **id:** Identificador único de la subcategoría.
- **nombre:** Nombre de la subcategoría (e.g., "Bonificaciones" dentro de "Salario").
- **descripcion:** Descripción detallada de la subcategoría.

**Relaciones:**
- **ManyToOne** con **Categoría:** Cada subcategoría pertenece a una categoría principal.

**Propósito:**  
Ofrecer una granularidad mayor en la clasificación de ingresos y gastos, permitiendo un análisis financiero más detallado.

**ENDPOINTS:**
| Método | Endpoint                       | Roles Permitidos          | Descripción                                         |
|--------|--------------------------------|-----------------|-----------------------------------------------------|
| POST   | /api/subcategorias             | USER/ADMIN      | Crea una nueva subcategoría en el sistema.          |
| GET    | /api/subcategorias             | ADMIN           | Obtiene todas las subcategorías del sistema.        |
| GET    | /api/subcategorias/{id}        | USER/ADMIN      | Obtiene los detalles de una subcategoría específica por ID.|
| PUT    | /api/subcategorias/{id}        | USER/ADMIN      | Actualiza los detalles de una subcategoría existente por ID.|
| DELETE | /api/subcategorias/{id}        | ADMIN           | Elimina una subcategoría específica por ID.         |

---

### **4. Cuenta**

**Descripción:**  
Representa una cuenta financiera del usuario, como cuentas bancarias, tarjetas de crédito, etc.

**Atributos Clave:**
- **id:** Identificador único de la cuenta.
- **nombre:** Nombre de la cuenta (e.g., "Cuenta Corriente", "Tarjeta de Crédito").
- **saldo:** Saldo actual de la cuenta.
- **tipoCuenta:** Tipo de cuenta (  AHORRO,CORRIENTE,INVERSION).
- **moneda:** ( USD,PEN,EUR )

**Relaciones:**
- **ManyToOne** con **Usuario:** Cada cuenta pertenece a un usuario.
- **OneToMany** con **Transaccion:** Una cuenta puede tener múltiples transacciones.

**Propósito:**  
Gestionar y monitorear el estado financiero de diferentes cuentas del usuario.

**ENDPOINTS:**
| Método | Endpoint                           | Roles Permitidos         | Descripción                                                          |
|--------|------------------------------------|----------------|----------------------------------------------------------------------|
| POST   | /api/cuentas                       | USER/ADMIN     | Crea una nueva cuenta en el sistema.                                 |
| GET    | /api/cuentas                       | ADMIN          | Lista todas las cuentas en el sistema.                              |
| GET    | /api/cuentas/usuario/{usuarioId}  | USER/ADMIN     | Obtiene todas las cuentas asociadas a un usuario específico.        |
| GET    | /api/cuentas/{id}                  | USER/ADMIN     | Obtiene los detalles de una cuenta específica por ID.               |
| PUT    | /api/cuentas/{id}                  | USER/ADMIN     | Actualiza los detalles de una cuenta existente por ID.              |
| DELETE | /api/cuentas/{id}                  | ADMIN          | Elimina una cuenta específica por ID.                               |


---

### **5. Transaccion**

**Descripción:**  
Registra cada movimiento financiero, ya sea un ingreso o un gasto.

**Atributos Clave:**
- **id:** Identificador único de la transacción.
- **monto:** Monto de la transacción.
- **destinatario:** Destinatario o beneficiario de la transacción.
- **fecha:** Fecha en que se realizó la transacción.
- **tipo:** Tipo de transacción (`INGRESO`, `GASTO`).

**Relaciones:**
- **ManyToOne** con **Cuenta:** Cada transacción está asociada a una cuenta específica.
- **ManyToOne** con **Categoría:** Cada transacción está clasificada dentro de una categoría.

**Propósito:**  
Registrar y categorizar cada movimiento financiero para llevar un seguimiento detallado de los ingresos y gastos del usuario.

**ENDPOINTS:**
| Método | Endpoint                                   | Roles Permitidos          | Descripción                                                            |
|--------|--------------------------------------------|----------------|------------------------------------------------------------------------|
| POST   | /api/transacciones                         | USER/ADMIN     | Crea una nueva transacción en el sistema.                               |
| GET    | /api/transacciones                         | ADMIN          | Lista todas las transacciones en el sistema.                          |
| GET    | /api/transacciones/usuario/{usuarioId}    | USER/ADMIN     | Obtiene todas las transacciones asociadas a un usuario específico.    |
| GET    | /api/transacciones/{id}                    | USER/ADMIN     | Obtiene los detalles de una transacción específica por ID.            |
| PUT    | /api/transacciones/{id}                    | USER/ADMIN     | Actualiza los detalles de una transacción existente por ID.           |
| DELETE | /api/transacciones/{id}                    | ADMIN          | Elimina una transacción específica por ID.                            |


---

### **6. Presupuesto**

**Descripción:**  
Define un límite o meta financiera para un período específico, ayudando al usuario a controlar sus gastos.

**Atributos Clave:**
- **id:** Identificador único del presupuesto.
- **montoTotal:** Monto total asignado para el presupuesto.
- **gastoActual:** Gasto actual dentro del presupuesto.
- **alerta:** Mensaje de alerta cuando se alcanza cierto porcentaje del presupuesto.
- **periodo:** Período del presupuesto (`MENSUAL`, `ANUAL`, `SEMANAL`).

**Relaciones:**
- **ManyToOne** con **Usuario:** Cada presupuesto pertenece a un usuario.
- **ManyToOne** con **Categoría:** Cada presupuesto está asociado a una categoría específica.

**Propósito:**  
Ayudar al usuario a planificar y controlar sus gastos dentro de diferentes categorías y períodos de tiempo.

**ENDPOINTS:**
| Método | Endpoint                                   | Roles Permitidos      | Descripción                                                            |
|--------|--------------------------------------------|----------------|------------------------------------------------------------------------|
| POST   | /api/presupuestos                         | USER/ADMIN     | Crea un nuevo presupuesto en el sistema.                               |
| GET    | /api/presupuestos                         | ADMIN          | Lista todos los presupuestos en el sistema.                          |
| GET    | /api/presupuestos/usuario/{usuarioId}    | USER/ADMIN     | Obtiene todos los presupuestos asociados a un usuario específico.    |
| GET    | /api/presupuestos/{id}                    | USER/ADMIN     | Obtiene los detalles de un presupuesto específico por ID.            |
| PUT    | /api/presupuestos/{id}                    | USER/ADMIN     | Actualiza los detalles de un presupuesto existente por ID.           |
| DELETE | /api/presupuestos/{id}                    | ADMIN          | Elimina un presupuesto específico por ID.                            |


---

### **7. Ítem**

**Descripción:**  
Detalla cada componente o elemento dentro de una transacción , proporcionando una desagregación más específica.

**Atributos Clave:**
- **id:** Identificador único del ítem.
- **nombre:** Nombre del ítem (e.g., "Compra de comida", "Pago de alquiler").
- **precio:** Monto asignado al ítem.
- **descripcion:** Descripción detallada del ítem.

**Relaciones:**
- **ManyToOne** con **Transaccion:** Cada ítem está asociado a una transacción específica.

**Propósito:**  
Proporcionar una descomposición detallada de las transacciones o presupuestos, facilitando un análisis más granular de los gastos e ingresos.

**ENDPOINTS**:

| Método | Endpoint              | Roles Permitidos         | Descripción                                                           |
|--------|-----------------------|----------------|-----------------------------------------------------------------------|
| POST   | /api/items           | USER/ADMIN     | Crea un nuevo item en el sistema.                                    |
| GET    | /api/items           | ADMIN          | Lista todos los items en el sistema.                                |
| GET    | /api/items/{id}      | USER/ADMIN     | Obtiene los detalles de un item específico por ID.                 |
| PUT    | /api/items/{id}      | USER/ADMIN     | Actualiza los detalles de un item existente por ID.                |
| DELETE | /api/items/{id}      | ADMIN          | Elimina un item específico por ID.                                  |


---

### **8. Reporte**

**Descripción:**  
Genera informes financieros basados en las transacciones y otros datos relevantes del usuario.

**Atributos Clave:**
- **id:** Identificador único del reporte.
- **fechaGeneracion:** Fecha en que se generó el reporte.
- **tipoReporte:** Tipo de reporte (`FINANCIERO`, `GASTOS`, `INGRESOS`).
- **contenido:** Contenido del reporte (puede ser en formato PDF, CSV, etc.).
- **rangoFechas:** Rango de fechas que abarca el reporte.
- **formato:** Formato del reporte (`PDF`, `CSV`, `EXCEL`).

**Relaciones:**
- **ManyToOne** con **Usuario:** Cada reporte pertenece a un usuario.

**Propósito:**  
Ofrecer al usuario una visión consolidada y estructurada de su situación financiera, facilitando la toma de decisiones informadas.


---

## Testing y Manejo de Errores
### Niveles de Testing Realizados:

**Testing de Integración a Nivel de los Controladores:**
Se realizaron pruebas de integración para validar los endpoints de la capa de aplicación, asegurando que las interacciones con los servicios funcionan correctamente a través de peticiones HTTP. Las pruebas incluyeron:

| **Prueba**                        | **Descripción**                                                                                                                                                              |
|-----------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Creación de una Transacción**   | Se validó que al enviar una petición para crear una transacción, el servicio maneja correctamente la solicitud y devuelve un estado `201 Created`.                         |
| **Listado de Transacciones**      | Se probaron los endpoints que permiten listar todas las transacciones y aquellas específicas por usuario, confirmando que el servicio retorna los datos esperados.          |
| **Actualización de Transacción**  | Se evaluó el flujo de actualización de una transacción, verificando que los cambios se reflejan correctamente en la respuesta.                                              |
| **Eliminación de Transacción**    | Se probó el endpoint de eliminación, asegurando que una transacción se elimine exitosamente cuando se proporciona un ID válido.                                              |

**Testing a Nivel de la Capa de Persistencia:**
Además de las pruebas en los controladores, se implementaron pruebas que validan la correcta interacción con la base de datos. Estas pruebas aseguran que las entidades se guardan, actualizan y eliminan adecuadamente en la base de datos, utilizando contenedores de TestContainers para simular un entorno de base de datos PostgreSQL.

### Resultados:
Los resultados de las pruebas realizadas muestran un desempeño positivo en la interacción con los endpoints de la aplicación. A continuación, se resumen los resultados obtenidos:

| **Operación**                  | **Resultado**                                            | **Descripción**                                                                                                                                          |
|--------------------------------|---------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| Creación de Transacción        | `201 Created`                                          | La transacción se creó correctamente, confirmando que el servicio maneja adecuadamente los datos de entrada y devuelve la respuesta esperada.            |
| Listado de Transacciones       | `200 OK`                                              | Se verificó que el listado de transacciones fue exitoso y que el número de transacciones retornadas coincide con los datos esperados.                     |
| Actualización de Transacción    | `200 OK`                                              | Los datos de la transacción fueron actualizados correctamente y el servicio devolvió la transacción modificada con los valores esperados.                  |
| Eliminación de Transacción     | `204 No Content`                                      | Las transacciones fueron eliminadas exitosamente al utilizar un ID válido, y la operación retornó el estado de no contenido, confirmando la eliminación. |

### Manejo de Errores:
El manejo de errores se realiza mediante la clase `GlobalExceptionHandler`, que utiliza la anotación `@RestControllerAdvice` para gestionar las excepciones globales lanzadas en los controladores. Esta clase proporciona respuestas personalizadas para diferentes tipos de errores, como se detalla a continuación:

| **Excepción**                                  | **Código de Estado**   | **Descripción**                                                                                             |
|------------------------------------------------|-------------------------|-------------------------------------------------------------------------------------------------------------|
| `ResourceNotFoundException`                     | `404 NOT FOUND`         | Maneja la excepción cuando un recurso no se encuentra.                                                    |
| `BadRequestException`                           | `400 BAD REQUEST`       | Maneja la excepción cuando hay una solicitud incorrecta.                                                   |
| `InsufficientFundsException`                    | `400 BAD REQUEST`       | Maneja la excepción cuando no hay suficientes fondos para realizar una transacción.                         |
| `UnauthorizedActionException`                   | `403 FORBIDDEN`         | Maneja la excepción cuando un usuario no tiene permisos para realizar la acción solicitada.                |
| `UserAlreadyExistException`                     | `400 BAD REQUEST`       | Maneja la excepción cuando se intenta crear un usuario que ya existe.                                     |
| `UsernameNotFoundException`                     | `404 NOT FOUND`         | Maneja la excepción cuando no se encuentra el nombre de usuario.                                          |
| `UnauthorizeOperationException`                 | `401 UNAUTHORIZED`      | Maneja la excepción cuando se intenta realizar una operación no autorizada.                                 |
| `AuthorizationDeniedException`                  | `401 UNAUTHORIZED`      | Maneja la excepción cuando el acceso a un recurso está denegado.                                          |
| `MethodArgumentNotValidException`               | `400 BAD REQUEST`       | Maneja la excepción cuando hay errores de validación en los argumentos del método.                         |
| `BadCredentialsException`                        | `400 BAD REQUEST`       | Maneja la excepción cuando el email o la contraseña son incorrectos.                                       |
| `Exception` (excepción global)                  | `500 INTERNAL SERVER ERROR` | Maneja excepciones generales, devolviendo un mensaje de error inesperado.                                 |
---

## Medidas de Seguridad Implementadas
### Seguridad de Datos:
La aplicación implementa varias técnicas y mecanismos de seguridad para proteger los datos de los usuarios. Uno de los principales métodos es el uso de **JSON Web Tokens (JWT)** para la autenticación y autorización. El proceso de autenticación se gestiona a través del **AuthController**, donde se registran y validan usuarios. Las contraseñas se almacenan de forma segura mediante el uso de **BCrypt**, un algoritmo de hashing que añade un factor de complejidad a las contraseñas almacenadas.

Además, todas las solicitudes están sujetas a **filtros de seguridad** que verifican la validez de los tokens JWT y autenticar al usuario antes de permitir el acceso a recursos protegidos. Esto asegura que solo los usuarios autenticados puedan acceder a sus datos personales y realizar transacciones. La aplicación también registra cada intento de inicio de sesión y registro, lo que permite auditar y detectar cualquier actividad sospechosa.


### Prevención de Vulnerabilidades:
Para prevenir vulnerabilidades, se han implementado varias medidas de seguridad:
- **Validación de Entradas:** Se utilizan anotaciones de validación en los DTOs (`LoginReq` y `RegisterReq`) para garantizar que los datos recibidos cumplen con los requisitos necesarios, como que el email sea válido y que la contraseña tenga al menos seis caracteres. Esto ayuda a prevenir inyecciones de SQL y otros ataques basados en datos maliciosos.
- **Configuración de Seguridad:** Se ha establecido una configuración de seguridad que deshabilita CSRF (Cross-Site Request Forgery) y utiliza una política de creación de sesiones sin estado (`STATELESS`), lo que significa que no se guardan sesiones de usuario en el servidor. Esto ayuda a mitigar ataques relacionados con sesiones.
- **Filtrado de Autenticación:** El **JwtAuthenticationFilter** se asegura de que cada solicitud que requiere autenticación contenga un token válido. Si el token no es válido o ha expirado, se devuelve un error de autorización.

---

## Eventos y Asincronía
En WalletWise, los eventos y la asincronía son fundamentales para mejorar la eficiencia del sistema, particularmente en las tareas que no requieren una respuesta inmediata. El envío de correos electrónicos de notificación es un caso importante que aprovecha este enfoque, ya que permite procesar transacciones sin demoras mientras se envían notificaciones en segundo plano.

### Implementación de Eventos Asíncronos
#### Configuración de la Asincronía**

El proyecto utiliza la anotación `@EnableAsync` en la clase `AsyncConfiguration` para habilitar el procesamiento de tareas asíncronas. Esto permite que ciertos métodos, como los que manejan eventos, se ejecuten en segundo plano sin bloquear el hilo principal.

```@Configuration
@EnableAsync
public class AsyncConfiguration {
}
```


#### Manejador de Eventos de Transacciones

La clase `TransactionEventListener` se encarga de escuchar los eventos de transacción que se disparan cuando un usuario realiza una transacción en la plataforma. Este evento es capturado de manera asíncrona, lo que significa que la operación principal de la transacción (como actualizar saldos y presupuestos) no se ve afectada por el procesamiento del evento.

```@Component
public class TransactionEventListener {

    private final EmailService emailService;

    public TransactionEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    @EventListener
    public void handleTransactionEvent(TransactionEvent event) {
        Transaccion transaccion = event.getTransaccion();
        String userEmail = transaccion.getCuenta().getUsuario().getEmail(); 

        // Enviar correo de notificación al usuario
        emailService.sendTransactionNotification(userEmail, transaccion);
    }
}
```


#### Envío Asíncrono de Correos

El servicio `EmailService` se utiliza para enviar correos electrónicos en respuesta a eventos como la creación de una nueva transacción. Este envío se realiza de manera asíncrona, utilizando la anotación `@Async` en el método que maneja el evento, lo que permite mejorar la experiencia del usuario al no bloquear la ejecución mientras se envía el correo.

```@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlMessage(String to, String subject, String htmlBody) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            // Manejar la excepción de envío de correo
        }
    }
}
```

### Casos de Uso de Eventos y Asincronía

**Registro de una Nueva Transacción:** Cada vez que un usuario realiza una nueva transacción, se dispara un evento que maneja el envío de un correo electrónico de notificación. Este correo contiene detalles sobre la transacción, como el monto, el destinatario, y el saldo restante en la cuenta, y se envía de manera asíncrona, permitiendo que la operación de la transacción sea rápida y fluida.

**Actualización de Presupuesto:** Durante la creación de una transacción, si la misma afecta un presupuesto asociado a una categoría de gasto, se envía una alerta por correo. El correo advierte al usuario si está próximo a alcanzar o ha superado el presupuesto. Estos correos se envían de manera asíncrona para no afectar el rendimiento del sistema.

---

## GitHub
### Uso de GitHub Projects:
[Describir el uso de GitHub projects.]

### Uso de GitHub Actions:
[Explicar flujo de trabajo implementado.]

---

## Conclusión
El proyecto ha sido un viaje significativo que ha permitido el desarrollo de un sistema robusto para la gestión de cuentas, categorías, subcategorías, y transacciones. A lo largo del proceso, hemos enfrentado desafíos que nos han llevado a mejorar nuestras habilidades técnicas y de colaboración. Este sistema no solo satisface las necesidades iniciales, sino que también abre nuevas posibilidades para el futuro.
### Logros del Proyecto:
- Desarrollo Completo de Funcionalidades: Se implementaron todas las funcionalidades clave, incluyendo la creación, obtención, actualización y eliminación (CRUD) de cuentas, categorías, subcategorías, y transacciones.
- Implementación de Seguridad: Se incorporó la autenticación JWT para la seguridad de las APIs, garantizando que solo los usuarios autorizados pudieran acceder a ciertos endpoints.

### Aprendizajes Clave:
- Trabajo en Equipo: La colaboración entre el grupo nos ha permitido aprender sobre diferentes enfoques, diferentes ideas y mejores prácticas en el desarrollo de esta aplicación.
- Mejora Continua: La comunicación del equipo constante durante el desarrollo ha sido crucial para identificar áreas de mejora y realizar ajustes necesarios.
- Gestión de Proyectos: Aprendimos a utilizar herramientas de gestión de proyectos como lo es Github, lo que ha sido fundamental para organizar las tareas y el progreso del proyecto.
- Resolución de Problemas: Enfrentar desafíos técnicos y encontrar soluciones efectivas ha sido una parte integral del aprendizaje, fortaleciendo nuestras habilidades de trabajar bajo un deadline.

### Trabajo Futuro:
- Optimización del Rendimiento: Se podrían implementar técnicas de optimización para mejorar la eficiencia de las consultas a la base de datos y el rendimiento general del sistema.
- Expansión de Funcionalidades: Evaluar la posibilidad de agregar nuevas funcionalidades, como la generación de informes detallados y análisis de datos, para proporcionar un mayor valor a los usuarios.
- Interfaz de Usuario: Desarrollar una interfaz de usuario atractiva para mejorar la experiencia del usuario final.

---

## Apéndices
### Licencia:
MIT License

Copyright (c) 2024 Adrian Urbina , Alejandro Ruiz, Adriana Sevillano , Carla Quispe


    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.

### Referencias:
Documentación de tecnologías utilizadas:

- **Spring Framework:** https://spring.io/projects/spring-framework
- **JWT (JSON Web Token):** https://jwt.io/
- **JPA (Java Persistence API):** https://www.ibm.com/docs/es/was-liberty/nd?topic=liberty-java-persistence-api-jpa
- **ModelMapper:** https://modelmapper.org/
- **Spring Security:** https://spring.io/projects/spring-security

---
