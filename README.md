# Foro API REST

[Español](#español) | [English](#english)

---

## Español

### Descripción

API REST para un foro académico donde los estudiantes pueden crear, ver, actualizar y eliminar temas de discusión por curso.

### Tecnologías

- **Java 17+** con Spring Boot 3
- **Spring Security** con JWT para autenticación
- **Spring Data JPA** para acceso a datos
- **PostgreSQL** como base de datos
- **Flyway** para migraciones de base de datos
- **Docker Compose** para levantar la base de datos
- **MapStruct** para mapeo de objetos

### Requisitos Previos

- Java 17 o superior
- Maven 3.8+
- Docker y Docker Compose (opcional)

### Instalación y Ejecución

#### 1. Configuración del archivo .env

Copia el archivo de ejemplo y configúralo con tus valores:

```bash
cp src/main/resources/.env.example src/main/resources/.env
```

Edita el archivo `.env` con tus configuraciones:

```env
POSTGRES_PASSWORD=tu_CLAVE
POSTGRES_USER=tu_usuario
POSTGRES_DB=nombre_base_datos
PORT=8080
SECURITY_NAME=nombre_seguridad
SECURITY_PASSWORD=clave_seguridad
TOKEN_SECRET=tu_secreto_jwt
SEED_USER_EMAIL=email@ejemplo.com
SEED_USER_PASS=clave_usuario 
```

#### Con Docker Compose (Recomendado)

1. Clona el repositorio
2. Ejecuta el contenedor de PostgreSQL:

```bash
docker-compose up -d
```

1. Compila y ejecuta la aplicación:

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

#### Sin Docker

1. Crea una base de datos PostgreSQL llamada `mydatabase`
2. Configura las variables de entorno o modifica `application.yaml` con tus credenciales
3. Ejecuta:

```bash
./mvnw spring-boot:run
```

### Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/kdbf/forum/
│   │   ├── adapters/
│   │   │   ├── in/web/          # Controladores REST
│   │   │   └── out/persistence/ # Repositorios y adaptadores
│   │   ├── application/
│   │   │   ├── domain/          # Entidades y servicios
│   │   │   └── port/            # Interfaces de puertos
│   │   └── infraestructure/     # Configuración de seguridad
│   └── resources/
│       └── db/migration/        # Migraciones Flyway
└── test/                       # Pruebas unitarias e integradas
```

### Configuración

La configuración principal se encuentra en `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydatabase
    username: myuser
    password: secret
  jpa:
    hibernate:
      ddl-auto: validate
```

### Captura 
![Swagger UI Captura](./captures/capture-api.png)

---

[Subir](#foro-api-rest) | [English](#english)

---

## English

### Description

REST API for an academic forum where students can create, view, update, and delete discussion topics by course.

### Technologies

- **Java 17+** with Spring Boot 3
- **Spring Security** with JWT authentication
- **Spring Data JPA** for data access
- **PostgreSQL** as the database
- **Flyway** for database migrations
- **Docker Compose** to spin up the database
- **MapStruct** for object mapping

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- Docker and Docker Compose (optional)

### Installation and Setup

#### 1. Configure the .env file

Copy the example file and configure it with your values:

```bash
cp src/main/resources/.env.example src/main/resources/.env
```

Edit the `.env` file with your settings:

```env
POSTGRES_PASSWORD=your_password
POSTGRES_USER=your_user
POSTGRES_DB=database_name
PORT=8080
SECURITY_NAME=security_name
SECURITY_PASSWORD=security_password
TOKEN_SECRET=your_jwt_secret
SEED_USER_EMAIL=email@example.com
SEED_USER_PASS=user_password
```

#### With Docker Compose (Recommended)

1. Clone the repository
2. Run the PostgreSQL container:

```bash
docker-compose up -d
```

1. Build and run the application:

```bash
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8080`

#### Without Docker

1. Create a PostgreSQL database named `mydatabase`
2. Set environment variables or modify `application.yaml` with your credentials
3. Run:

```bash
./mvnw spring-boot:run
```

### Project Structure

```
src/
├── main/
│   ├── java/com/kdbf/forum/
│   │   ├── adapters/
│   │   │   ├── in/web/          # REST Controllers
│   │   │   └── out/persistence/ # Repositories and Adapters
│   │   ├── application/
│   │   │   ├── domain/          # Entities and Services
│   │   │   └── port/            # Port Interfaces
│   │   └── infraestructure/     # Security Configuration
│   └── resources/
│       └── db/migration/        # Flyway Migrations
└── test/                        # Unit and Integration Tests
```

### Configuration

Main configuration is in `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydatabase
    username: myuser
    password: secret
  jpa:
    hibernate:
      ddl-auto: validate
```

### Capture 
![Swagger UI Capture](./captures/capture-api.png)

### License

This project is for learning purposes.

---

[Subir](#foro-api-rest) | [Español](#español)
