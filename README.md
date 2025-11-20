# Sistema de Gestión de Empleados con Herencia, Interfaces y Spring Boot REST
**Trabajo Práctico Integrador – LP3 2025**

**Autor:** wisher0r  
**Repositorio:** https://github.com/wisher0r/df-taller-git-2025  
**Fecha:** 20 de noviembre de 2025

---
## Descripción General
Sistema REST API completo para gestionar empleados con **herencia JPA**, **polimorfismo**, **excepciones propias**, **jerarquía de controladores y mappers**, **utilidades** y **reglas de negocio**.

**Tipos de empleados implementados**:
- **EmpleadoTiempoCompleto** – salario mensual fijo + departamento
- **EmpleadoPorHora** – tarifa por hora + horas extras (bonus 50% >40h)
- **Contratista** – monto por proyecto + fecha fin contrato
- **Gerente** – hereda de EmpleadoTiempoCompleto + método exclusivo + días adicionales

**Funcionalidades clave**:
- CRUD por tipo de empleado
- Persistencia en batch (mezcla de tipos) con chunks de 100
- Cálculo de nómina total por tipo
- Reporte polimórfico con impuestos y validaciones
- Solicitud de vacaciones/permisos (solo gerentes pueden >20 días)
- Excepciones propias manejadas globalmente
- Utilidad `NominaUtils` (total días + reporte JSON)
- Jerarquía de mappers y controladores

---
## Arquitectura Implementada
| Capa                  | Componentes                                                                 | Tecnologías                              |
|-----------------------|-----------------------------------------------------------------------------|------------------------------------------|
| **Presentación**      | `BaseController` → `EmpleadoTiempoCompletoController`, `EmpleadoPorHoraController`, `ContratistaController`, `GerenteController` | Spring Web MVC, REST                     |
| **Lógica de Negocio** | `EmpleadoService` (batch, nómina, reporte), `GerenteService`, `NominaUtils` | @Service, polimorfismo, excepciones      |
| **Dominio**           | `Persona` (abstracta) → `EmpleadoTiempoCompleto` → `Gerente`, `EmpleadoPorHora`, `Contratista` | JPA @Entity, @Inheritance(JOINED)       |
| **Interfaces**        | `Permisionable`, `GerentePermisionable`                                     | Métodos exclusivos para gerentes         |
| **Persistencia**      | Repositorios específicos + `PersonaRepository`                             | Spring Data JPA                          |
| **DTOs**              | `ImpuestoDTO`, `BatchResponseDTO`, `ErrorResponseDTO`                       | Lombok, Jackson                          |
| **Mappers**           | `MapperBase` → `ImpuestoDetalleMapper`                                      | Conversión entidad → DTO                 |
| **Excepciones**       | `DiasInsuficientesException` (checked), `EmpleadoNoEncontradoException` (runtime), `FechaNacimientoInvalidaException` | Manejo global con @ControllerAdvice      |
| **Utilidades**        | `MapeableFactory`, `MapeablePrinter`, `NominaUtils`                        | Logs con SLF4J, creación de datos demo   |

**Diagrama de Herencia**:
Persona (abstracta)
├── calcularSalario() (abstracto)
├── calcularImpuestos() (template)
└── @Inheritance(JOINED)
├── EmpleadoTiempoCompleto
│   └── Gerente (implementa GerentePermisionable)
├── EmpleadoPorHora
└── Contratista

## Instrucciones de Ejecución
```bash
git clone https://github.com/wisher0r/df-taller-git-2025.git
cd df-taller-git-2025
./mvnw spring-boot:run

App: http://localhost:8080
H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:lp32025)

1. Batch exitoso (mezcla de tipos)
curl -X POST http://localhost:8080/api/empleados/batch \
  -H "Content-Type: application/json" \
  -d '[
    {"@class":"py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto","nombre":"Ana López","numeroDocumento":"1111111","fechaNacimiento":"1990-01-01","salarioMensual":6000000,"departamento":"IT"},
    {"@class":"py.edu.uc.lp32025.domain.EmpleadoPorHora","nombre":"Luis García","numeroDocumento":"2222222","tarifaPorHora":50000,"horasTrabajadas":45},
    {"@class":"py.edu.uc.lp32025.domain.Contratista","nombre":"Pedro Ruiz","numeroDocumento":"3333333","montoPorProyecto":1200000,"proyectosCompletados":3,"fechaFinContrato":"2026-12-31"},
    {"@class":"py.edu.uc.lp32025.domain.Gerente","nombre":"Laura Gómez","numeroDocumento":"4444444","salarioMensual":25000000,"departamento":"Dirección"}
  ]'

2. Batch con error de validación
curl -X POST http://localhost:8080/api/empleados/batch \
  -H "Content-Type: application/json" \
  -d '[{"@class":"py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto","nombre":"Error","numeroDocumento":"999","salarioMensual":0,"departamento":"IT"}]'

3. Nómina total
curl http://localhost:8080/api/empleados/nomina

4. Reporte polimórfico
curl http://localhost:8080/api/empleados/reporte

5. Solicitar días (válido – gerente)
curl -X POST "http://localhost:8080/api/empleados/4/solicitar-dias?dias=30&tipo=vacaciones"

6. Solicitar días (inválido – no gerente >20 días)
curl -X POST "http://localhost:8080/api/empleados/1/solicitar-dias?dias=25&tipo=vacaciones"

7. Fecha de nacimiento futura (excepción personalizada)
curl -X POST http://localhost:8080/api/personas \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan","fechaNacimiento":"2030-01-01","numeroDocumento":"9999999"}'


8. Reporte empleados que superan umbral de días
curl "http://localhost:8080/api/empleados/reporte-dias-superados?maxDias=20"

