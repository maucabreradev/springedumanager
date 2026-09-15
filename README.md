# SpringEduManager

Plataforma de gestion educativa para bootcamps de programacion. Permite a coordinadores gestionar cursos, practicas y evaluaciones, y a estudiantes inscribirse, consultar sus practicas y evaluaciones.

---

## Stack tecnologico

| Capa | Tecnologia |
|------|-----------|
| Runtime | Java 25 |
| Framework | Spring Boot 3.5.0 |
| Seguridad | Spring Security (BCrypt, roles ESTUDIANTE / COORDINADOR) |
| Persistencia | Spring Data JPA + Hibernate |
| Base de datos | MySQL 8 (produccion) / H2 (tests) |
| Migraciones | Flyway |
| Template engine | Thymeleaf |
| API REST | Spring MVC @RestController |
| Validacion | Jakarta Validation (@Valid) |
| Build | Maven |
| Contenedor | Docker + docker-compose |

---

## Arquitectura

```
springedumanager/
├── src/main/java/com/maurocabrera/springedumanager/
│   ├── config/           # SecurityConfig, WebMvcConfig
│   ├── controller/
│   │   ├── mvc/          # Auth, Admin, Student, Course, Evaluation, Practice controllers
│   │   └── rest/         # CourseRestController, StudentRestController, EvaluationRestController
│   ├── dto/
│   │   ├── request/      # CursoRequest, EvaluacionRequest (@Valid)
│   │   └── response/     # CursoResponse, EvaluacionResponse, MatriculaResponse, PracticaResponse
│   ├── entity/           # Curso, Estudiante, Evaluacion, Matricula, Practica, Role
│   ├── exception/        # GlobalExceptionHandler, ResourceNotFoundException
│   ├── repository/       # Spring Data JPA repositories
│   ├── security/         # CustomUserDetailsService
│   └── service/          # Service interfaces + implementations
├── src/main/resources/
│   ├── application.yml           # Config por defecto (MySQL)
│   ├── application-dev.yml       # Config desarrollo
│   ├── application-prod.yml      # Config produccion (Docker MySQL)
│   ├── db/migration/V1__initial_schema.sql
│   ├── static/css/style.css
│   └── templates/                # Thymeleaf templates
├── src/test/                     # 24 tests (H2 in-memory)
├── docker-compose.yml            # MySQL + phpMyAdmin
└── Dockerfile                    # eclipse-temurin:25-jdk-alpine
```

### Entidades

| Entidad | Descripcion |
|---------|-------------|
| `Role` | Roles del sistema: ESTUDIANTE, COORDINADOR |
| `Estudiante` | Usuarios del sistema (implementa `UserDetails`) |
| `Curso` | Cursos del bootcamp con practicas y evaluaciones |
| `Practica` | Practicas asignadas a un curso |
| `Evaluacion` | Evaluaciones (examenes, parciales, etc.) de un curso |
| `Matricula` | Inscripcion de un estudiante a un curso |

---

## Requisitos previos

- Java 25 (JDK)
- Maven 3.9+
- Docker y docker-compose (para MySQL)
- Git

---

## Clonar y levantar localmente

### 1. Clonar el repositorio

```bash
git clone https://github.com/maurocabrera/springedumanager.git
cd springedumanager
```

### 2. Levantar MySQL con Docker

```bash
docker-compose up -d
```

Esto levanta:

| Servicio | Puerto | URL |
|----------|--------|-----|
| MySQL 8 | `3306` | `localhost:3306` |
| phpMyAdmin | `8081` | `http://localhost:8081` |

Credenciales de la base de datos:
- **Usuario:** `springedu`
- **Password:** `springedupass`
- **Database:** `springedumanager`

### 3. Compilar y ejecutar

```bash
mvn clean package -DskipTests
java -jar target/springedumanager-1.0.0-SNAPSHOT.jar
```

O directamente con Maven:

```bash
mvn spring-boot:run
```

La aplicacion estara disponible en: **http://localhost:8080**

### 4. Profiles

```bash
# Desarrollo (show-sql: true, logging DEBUG)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Produccion (usa Docker MySQL internamente)
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## Uso de la aplicacion

### Registro y login

1. Ir a `http://localhost:8080`
2. Registrarse en `/register`
3. Iniciar sesion en `/login`

### Roles

| Rol | Acceso |
|-----|--------|
| **ESTUDIANTE** | `/estudiante/**` - Ver cursos, inscribirse, ver practicas y evaluaciones |
| **COORDINADOR** | `/admin/**` - CRUD de cursos, practicas y evaluaciones |

---

## API REST

Todos los endpoints REST requieren autenticacion basica. CSRF deshabilitado para `/api/**`.

### Cursos (`/api/courses`)

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| `GET` | `/api/courses` | Listar todos los cursos activos |
| `GET` | `/api/courses/{id}` | Obtener curso por ID |
| `POST` | `/api/courses` | Crear curso (body: `CursoRequest`) |
| `PUT` | `/api/courses/{id}` | Actualizar curso (body: `CursoRequest`) |
| `DELETE` | `/api/courses/{id}` | Eliminar curso |
| `GET` | `/api/courses/{id}/practices` | Practicas de un curso |
| `GET` | `/api/courses/{id}/evaluations` | Evaluaciones de un curso |

### Estudiantes (`/api/students`)

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| `GET` | `/api/students/{id}` | Obtener estudiante por ID |
| `POST` | `/api/students/{id}/enroll/{courseId}` | Inscribir estudiante en curso |
| `DELETE` | `/api/students/{id}/enroll/{courseId}` | Desinscribir estudiante |

### Evaluaciones (`/api/evaluations`)

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| `GET` | `/api/evaluations` | Listar todas las evaluaciones |
| `GET` | `/api/evaluations/{id}` | Obtener evaluacion por ID |
| `POST` | `/api/evaluations` | Crear evaluacion (body: `EvaluacionRequest`) |
| `PUT` | `/api/evaluations/{id}` | Actualizar evaluacion |
| `DELETE` | `/api/evaluations/{id}` | Eliminar evaluacion |
| `GET` | `/api/evaluations/{id}/results` | Resultados de una evaluacion |

### Ejemplo con curl

```bash
# Listar cursos (autenticacion basica)
curl -u springedu:springedupass http://localhost:8080/api/courses

# Crear curso
curl -u springedu:springedupass -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Spring Boot","descripcion":"Curso intensivo","creditos":6}'

# Inscribir estudiante
curl -u springedu:springedupass -X POST http://localhost:8080/api/students/1/enroll/1
```

---

## Tests

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar un test especifico
mvn test -Dtest=CursoRepositoryTest
```

Los tests usan **H2 in-memory** (sin necesidad de MySQL). Cubren:

| Capa | Tests | Archivo |
|------|-------|---------|
| Controller MVC | 3 + 3 | `CourseControllerTest`, `StudentControllerTest` |
| Controller REST | 5 | `CourseRestControllerTest` |
| Repository | 2 | `CursoRepositoryTest` |
| Service | 4 + 2 | `CursoServiceTest`, `UserServiceTest` |
| Security | 5 | `SecurityConfigTest` |
| **Total** | **24** | |

---

## Docker

### Dockerfile

```dockerfile
FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
COPY target/springedumanager-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

### docker-compose.yml

```yaml
services:
  mysql:
    image: mysql:8
    ports: ["3306:3306"]
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: springedumanager
      MYSQL_USER: springedu
      MYSQL_PASSWORD: springedupass

  phpmyadmin:
    image: phpmyadmin/phpmyadmin
    ports: ["8081:80"]
    depends_on: [mysql]
```

### Build y ejecutar con Docker

```bash
mvn clean package -DskipTests
docker build -t springedumanager .
docker run -p 8080:8080 springedumanager
```

---

## Estructura de templates

```
templates/
├── layout/layout.html          # Layout compartido (navbar, footer, flash messages)
├── auth/
│   ├── login.html              # Formulario de login
│   ├── register.html           # Formulario de registro
│   └── error.html              # Error de autenticacion
├── admin/
│   └── dashboard.html          # Dashboard del coordinador
├── course/
│   ├── list.html               # Listar cursos (admin)
│   ├── form.html               # Crear/editar curso
│   └── detail.html             # Detalle del curso
├── evaluation/
│   ├── list.html               # Listar evaluaciones (admin)
│   ├── form.html               # Crear/editar evaluacion
│   └── detail.html             # Detalle de evaluacion
├── practice/
│   ├── list.html               # Listar practicas (admin)
│   ├── form.html               # Crear/editar practica
│   └── detail.html             # Detalle de practica
└── student/
    ├── dashboard.html          # Dashboard del estudiante
    ├── courses.html            # Cursos disponibles
    ├── course-detail.html      # Detalle del curso (estudiante)
    ├── practices.html          # Practicas del estudiante
    └── evaluations.html        # Evaluaciones del estudiante
```

---

## Licencia

Proyecto de estudio - Mauro Cabrera
