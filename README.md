# Sistema de Gestión de Empleados con Herencia y Persistencia en Batch

**Trabajo Práctico - LP3 2025**  
**Autor: wisher0r**  
**Repositorio: https://github.com/wisher0r/df-taller-git-2025**

---

## Descripción General

Sistema REST API para gestionar diferentes tipos de empleados utilizando **herencia JPA**, **persistencia en batch**, **polimorfismo** y **validaciones específicas por tipo**.  

**Tipos de empleados implementados**:
- **EmpleadoTiempoCompleto**: Salario mensual fijo, departamento.
- **EmpleadoPorHora**: Tarifa por hora + horas extras (bonus 50% >40h).
- **Contratista**: Monto por proyecto, proyectos completados, fecha fin contrato.

**Funcionalidades clave**:
- CRUD por tipo de empleado.
- Batch para crear múltiples empleados (mezcla de tipos).
- Cálculo de nómina total por tipo.
- Reporte polimórfico con impuestos y validaciones.
- Validaciones específicas (ej: salario >0, fecha futura para contratistas).

---

## Descripción de la Arquitectura Implementada

El sistema sigue el patrón **MVC** con **Spring Boot**, usando **herencia JPA** para polimorfismo en la base de datos.

| Capa | Componentes | Tecnologías |
|------|-------------|-------------|
| **Presentación (Controladores)** | `EmpleadoTiempoCompletoController`, `EmpleadoPorHoraController`, `ContratistaController`, `EmpleadoBatchController` | Spring Web MVC, REST endpoints |
| **Lógica de Negocio (Services)** | `EmpleadoService` (batch, nómina, reporte), `EmpleadoTiempoCompletoService`, `PersonService` | Spring @Service, polimorfismo con métodos abstractos |
| **Dominio (Entidades)** | `Persona` (abstracta), `EmpleadoTiempoCompleto`, `EmpleadoPorHora`, `Contratista` | JPA @Entity, @Inheritance(JOINED), métodos template (calcularImpuestos) |
| **Persistencia (Repositorios)** | `EmpleadoTiempoCompletoRepository`, etc. | Spring Data JPA, métodos custom (findByDepartamento) |
| **DTOs y Validaciones** | `ImpuestoDTO`, `BatchResponseDTO`, `ErrorResponseDTO` | Bean Validation (@NotNull), Jackson para JSON polimórfico (@JsonTypeInfo) |
| **Configuración** | `GlobalExceptionHandler`, `JacksonConfig` | @ControllerAdvice, manejo de errores |

**Diagrama de Herencia**:
Persona (abstracta)
├── calcularSalario() (abstracto)
├── calcularImpuestos() (template method)
└── @Inheritance(JOINED)
├── EmpleadoTiempoCompleto (salarioMensual, departamento)
├── EmpleadoPorHora (tarifaPorHora, horasTrabajadas)
└── Contratista (montoPorProyecto, fechaFinContrato)

**Batch**: Chunks de 100 registros, validación polimórfica antes de guardar.

---

## Instrucciones de Ejecución

### Requisitos
- Java 21+
- Maven 3.6+
- IDE: IntelliJ IDEA o VS Code (opcional)

### Pasos
1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/wisher0r/df-taller-git-2025.git
   cd df-taller-git-2025

Ejecutar con Maven:
./mvnw clean spring-boot:run

La app inicia en: http://localhost:8080
H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:lp32025)

1. Operación Batch Exitosa (Mezcla de Tipos)
curl -X POST http://localhost:8080/api/empleados/batch \
  -H "Content-Type: application/json" \
  -d '[
    {
      "@class": "py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto",
      "nombre": "Ana López", "numeroDocumento": "1111111", "fechaNacimiento": "1990-01-01",
      "salarioMensual": 5000000, "departamento": "IT"
    },
    {
      "@class": "py.edu.uc.lp32025.domain.EmpleadoPorHora",
      "nombre": "Luis García", "numeroDocumento": "2222222",
      "tarifaPorHora": 50000, "horasTrabajadas": 45
    },
    {
      "@class": "py.edu.uc.lp32025.domain.Contratista",
      "nombre": "María Ruiz", "numeroDocumento": "3333333",
      "montoPorProyecto": 1000000, "proyectosCompletados": 2, "fechaFinContrato": "2026-12-31"
    }
  ]'

2. Operación Batch con Error de Validación
curl -X POST http://localhost:8080/api/empleados/batch \
  -H "Content-Type: application/json" \
  -d '[
    {
      "@class": "py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto",
      "nombre": "Error Test", "numeroDocumento": "9999999", "salarioMensual": 0
    }
  ]'
3. Consulta de Nómina Total por Tipo
curl http://localhost:8080/api/empleados/nomina

4. Reporte de Polimorfismo (Completo)
curl http://localhost:8080/api/empleados/reporte

5. Empleados por Tipo
curl http://localhost:8080/api/empleados/tipo/tiempocompleto

