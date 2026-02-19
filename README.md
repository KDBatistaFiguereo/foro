# Foro API REST

API REST para un foro académico donde los estudiantes pueden crear, ver, actualizar y eliminar temas de discusión por curso.

## Tecnologías

- **Java 17+** con Spring Boot 4
- **Spring Security** con JWT para autenticación
- **Spring Data JPA** para acceso a datos
- **PostgreSQL** como base de datos
- **Flyway** para migraciones de base de datos
- **Docker Compose** para levantar la base de datos
- **MapStruct** para mapeo de objetos

## Requisitos Previos

- Java 17 o superior
- Maven 3.8+
- Docker y Docker Compose (opcional)

## Instalación y Ejecución

### 1. Configuración del archivo .env

Copia el archivo de ejemplo y configúralo con tus valores:

```bash
cp src/main/resources/.env.example src/main/resources/.env
```

Edita el archivo `.env` con tus configuraciones:

```env
POSTGRES_PASSWORD=tu_password
POSTGRES_USER=tu_usuario
POSTGRES_DB=nombre_base_datos
PORT=8080
SECURITY_NAME=nombre_seguridad
SECURITY_PASSWORD=password_seguridad
TOKEN_SECRET=tu_secreto_jwt
SEED_USER_EMAIL=email@ejemplo.com
SEED_USER_PASS=password_usuario
```

### Con Docker Compose (Recomendado)

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

### Sin Docker

1. Crea una base de datos PostgreSQL llamada `mydatabase`
2. Configura las variables de entorno o modifica `application.yaml` con tus credenciales
3. Ejecuta:

```bash
./mvnw spring-boot:run
```

## Endpoints de la API

### Autenticación

| Método | Endpoint | Descripción    | Autenticación |
| ------ | -------- | -------------- | ------------- |
| POST   | `/login` | Iniciar sesión | No requerida  |

**Cuerpo de solicitud:**

```json
{
  "login": "email@ejemplo.com",
  "password": "tu_contraseña"
}
```

**Respuesta:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### Temas (Topics)

Todos los endpoints de temas requieren autenticación con JWT.

| Método | Endpoint              | Descripción                    |
| ------ | --------------------- | ------------------------------ |
| GET    | `/topicos`            | Listar todos los temas         |
| GET    | `/topicos/{publicId}` | Obtener un tema por su ID      |
| POST   | `/topicos`            | Crear un nuevo tema            |
| PUT    | `/topicos/{publicId}` | Actualizar un tema             |
| DELETE | `/topicos/{publicId}` | Eliminar un tema (soft delete) |

#### GET /topicos

Respuesta exitosa:

```json
[
  {
    "publicId": "uuid-del-tema",
    "title": "Título del tema",
    "body": "Contenido del tema",
    "author": {
      "username": "nombre_usuario"
    },
    "course": {
      "courseName": "nombre_curso"
    },
    "creationDate": "2026-02-19T10:30:00",
    "status": "DRAFT"
  }
]
```

#### POST /topicos

**Cuerpo de solicitud:**

```json
{
  "title": "Título del nuevo tema",
  "body": "Contenido del tema",
  "author": {
    "username": "nombre_usuario"
  },
  "course": {
    "courseName": "nombre_curso"
  }
}
```

#### PUT /topicos/{publicId}

**Cuerpo de solicitud:**

```json
{
  "title": "Título actualizado",
  "body": "Contenido actualizado"
}
```

#### DELETE /topicos/{publicId}

Retorna código `204 No Content` si la eliminación es exitosa.

## Uso de la API

### Autenticarse

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"login": "email@ejemplo.com", "password": "contraseña"}'
```

### Listar temas

```bash
curl -X GET http://localhost:8080/topicos \
  -H "Authorization: Bearer TU_TOKEN_JWT"
```

### Crear un tema

```bash
curl -X POST http://localhost:8080/topicos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TU_TOKEN_JWT" \
  -d '{
    "title": "Duda sobre Spring Security",
    "body": "¿Cómo funciona la autenticación JWT?",
    "author": {"username": "estudiante1"},
    "course": {"courseName": "Programación III"}
  }'
```

### Actualizar un tema

```bash
curl -X PUT http://localhost:8080/topicos/UUID_DEL_TEMA \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TU_TOKEN_JWT" \
  -d '{
    "title": "Título actualizado",
    "body": "Contenido actualizado"
  }'
```

### Eliminar un tema

```bash
curl -X DELETE http://localhost:8080/topicos/UUID_DEL_TEMA \
  -H "Authorization: Bearer TU_TOKEN_JWT"
```

## Estructura del Proyecto

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
└── test/                        # Pruebas unitarias e integradas
```

## Configuración

La configuración principal se encuentra en `src/main/resources/application.yaml`:

## Licencia

Este proyecto es para aprendizaje.
