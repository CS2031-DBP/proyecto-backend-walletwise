[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/42utwHoA)

---

# Proyecto: WalletWise 💳

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

---

### **8. Reporte**

**Descripción:**  
Genera informes financieros basados en las transacciones, presupuestos y otros datos relevantes del usuario.

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
[Describir niveles de pruebas.]

### Resultados:
[Resumir resultados de pruebas.]

### Manejo de Errores:
[Explicar excepciones globales.]

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
[Detallar eventos y asincronía implementados.]

---

## GitHub
### Uso de GitHub Projects:
[Describir el uso de GitHub projects.]

### Uso de GitHub Actions:
[Explicar flujo de trabajo implementado.]

---

## Conclusión
### Logros del Proyecto:
[Resumir logros alcanzados.]

### Aprendizajes Clave:
[Reflexionar sobre aprendizajes.]

### Trabajo Futuro:
[Proponer mejoras.]

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
[Incluir referencias.]

---
