# 🏦 Bank Application - Spring Boot Microservices

Este proyecto es una solución backend basada en una arquitectura de microservicios para la gestión bancaria. Está dividido en dos servicios independientes que se comunican de forma asíncrona para gestionar clientes, cuentas y transacciones.

## 🏗️ Arquitectura y Tecnologías

El proyecto está construido con las siguientes tecnologías y patrones de diseño:

*   *Java 17+* / *Spring Boot*
*   *Spring Data JPA* (Persistencia de datos)
*   *Spring Web* (API REST)
*   *Base de Datos Relacional* (Configurable en application.properties)
*   *Lombok* (Reducción de código repetitivo - Opcional según configuración)
*   *JUnit 5 & Mockito* (Pruebas unitarias y de integración)
*   *Maven* (Gestión de dependencias)

### Microservicios

1.  *Client Service (client)*: Gestiona la información de las personas y clientes.
    *   *Puerto:* 8001
    *   *Entidades:* Person, Client
2.  *Account Service (account)*: Gestiona las cuentas bancarias y las transacciones.
    *   *Puerto:* 8000
    *   *Entidades:* Account, Transaction

## 🚀 Funcionalidades Implementadas

*   *F1: CRUDs Completos*: Operaciones de Crear, Leer, Actualizar y Eliminar para Clientes, Cuentas y Transacciones.
*   *F2: Registro de Transacciones*: Actualización automática del saldo disponible al realizar un movimiento (positivo o negativo).
*   *F3: Validación de Saldo*: Bloqueo de transacciones si el saldo resultante es menor a cero, devolviendo el mensaje exacto "Saldo no disponible" con un estado HTTP 400.
*   *F4: Reporte de Estado de Cuenta*: Endpoint que genera un reporte en formato JSON con las cuentas asociadas y el detalle de movimientos en un rango de fechas.
*   *F5: Pruebas Unitarias*: Implementación de pruebas unitarias para la lógica de negocio y entidades.
*   *F6: Pruebas de Integración*: Pruebas de los endpoints REST utilizando MockMvc.

## ⚙️ Configuración y Ejecución

### Prerrequisitos
*   JDK 17 o superior.
*   Maven instalado.
*   Una base de datos relacional (MySQL, PostgreSQL, etc.) o H2 para pruebas en memoria.

### Configuración de Puertos y Base de Datos
Asegúrate de que los archivos application.properties en cada módulo tengan las siguientes configuraciones:

*En client/src/main/resources/application.properties*:
```properties
server.port=8001
# Configuración de tu base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/client_db
spring.datasource.username=root
spring.datasource.password=tu_password
 
En account/src/main/resources/application.properties:
server.port=8000
# Configuración de tu base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/account_db
spring.datasource.username=root
spring.datasource.password=tu_password
 
Compilación y Ejecución
 
Abre una terminal en la raíz del proyecto (Microservices/BankApplication/) y ejecuta:
1. Compilar todo el proyecto:
mvn clean install -DskipTests
2. Ejecutar el microservicio de Cuentas (Account):
mvn -f account/pom.xml spring-boot:run
3. Ejecutar el microservicio de Clientes (Client):
mvn -f client/pom.xml spring-boot:run
 
📡 Endpoints Principales
 
🧑‍💼 Client API (Puerto 8001)
* GET /api/clients - Obtener todos los clientes.
* GET /api/clients/{id} - Obtener cliente por ID.
* POST /api/clients - Crear un nuevo cliente.
* PUT /api/clients/{id} - Actualizar cliente.
* PATCH /api/clients/{id} - Actualización parcial (estado).
* DELETE /api/clients/{id} - Eliminar cliente. 
💳 Account API (Puerto 8000)
* GET /api/accounts - Obtener todas las cuentas.
* GET /api/accounts/{id} - Obtener cuenta por ID.
* POST /api/accounts - Crear una nueva cuenta.
* PUT /api/accounts/{id} - Actualizar cuenta.
* PATCH /api/accounts/{id} - Actualización parcial.
* DELETE /api/accounts/{id} - Eliminar cuenta. 
💸 Transaction API (Puerto 8000)
* GET /api/transactions - Obtener todas las transacciones.
* POST /api/transactions - Registrar un movimiento (valida saldo).
* GET /api/transactions/clients/{clientId}/report?dateTransactionStart=YYYY-MM-DD&dateTransactionEnd=YYYY-MM-DD - Reporte de Estado de Cuenta (F4). 
🧪 Pruebas
 
El proyecto incluye pruebas unitarias (F5) y de integración (F6). Para ejecutarlas, usa:
# Ejecutar pruebas del módulo Client
mvn -f client/pom.xml test

# Ejecutar pruebas del módulo Account
mvn -f account/pom.xml test
 
📬 Colección de Postman
 
Se incluye un archivo llamado collection_bank_postman.json en la raíz del proyecto. Este archivo contiene todas las peticiones preconfiguradas para probar los endpoints de ambos microservicios. Puedes importarlo directamente en Postman (File -> Import). 
 
Desarrollado por: Xavier9610 - Xavier Garcia