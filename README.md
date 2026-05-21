# penaranda-post1-u12

**Unidad 12 — Integración de Patrones y Arquitecturas**  
Post-Contenido 1 · Patrones de Diseño de Software · Ingeniería de Sistemas · UDES 2026

---

## Descripción del sistema

Sistema de gestión de pedidos construido con Spring Boot 3.x que integra cuatro
patrones de diseño sobre una arquitectura hexagonal. El punto de partida fue un
servicio monolítico (`ServicioPedidosLegacy`) con CC=4 y Cognitive Complexity=6;
el resultado final tiene CC=1 en `FachadaPedidos` y cero acoplamiento directo desde
la capa de aplicación a infraestructura.

---

## Arquitectura del sistema

```
com.empresa.pedidos/
├── dominio/              ← núcleo, sin dependencias externas
│   ├── Pedido.java
│   ├── TipoPedido.java
│   ├── EstadoPedido.java
│   └── puertos/          ← interfaces (contratos)
│       ├── RepositorioPedidos.java
│       ├── ProcesadorPedido.java   (Strategy interface)
│       └── ServicioNotificacion.java
├── aplicacion/           ← casos de uso
│   └── ProcesadorPedidoFactory.java  (Factory)
├── infraestructura/      ← implementaciones externas
│   ├── persistencia/
│   │   └── RepositorioPedidosJpa.java
│   └── notificaciones/
│       ├── PedidoProcesadoEvent.java
│       ├── NotificacionEmail.java    (Observer)
│       └── NotificacionLog.java     (Observer)
└── adaptadores/          ← entrada/salida
    ├── procesadores/     ← Strategy implementations
    ├── facade/
    │   └── FachadaPedidos.java      (Facade)
    └── rest/
        └── PedidoController.java
```

---

## Patrones implementados y justificación

| Patrón | Clase principal | Problema que resuelve |
|---|---|---|
| **Strategy** | `ProcesadorPedido` + 3 implementaciones | Elimina el switch sobre `TipoPedido`; cada algoritmo de cálculo está encapsulado en su propia clase |
| **Factory** | `ProcesadorPedidoFactory` | Centraliza la selección dinámica de Strategy sin que el servicio conozca las implementaciones concretas |
| **Observer** | `PedidoProcesadoEvent` + listeners | Desacopla la notificación del procesamiento; nuevos listeners no requieren modificar `FachadaPedidos` |
| **Facade** | `FachadaPedidos` | Simplifica la interfaz para el controlador REST; oculta la coordinación de Factory, Strategy, repositorio y publisher |

---

## Métricas comparativas (SonarQube)

| Métrica | Antes (Legacy) | Después (Refactorizado) |
|---|---|---|
| Cyclomatic Complexity (servicio principal) | 4 | 1 |
| Cognitive Complexity | 6 | 0 |
| Acoplamiento a JavaMailSender | Directo | Eliminado |
| Acoplamiento a JPA Repository desde aplicación | Directo | Via puerto |
| Cobertura de pruebas | — | ≥ 80% |

> 📸 Ver capturas en la carpeta `capturas/`

---

## Quality Gate: Passed

📸 `capturas/quality-gate-passed.png`

---

## Pruebas

| Clase de prueba | Tipo | Patron verificado |
|---|---|---|
| `ProcesadorPedidoFactoryTest` | Unitaria | Factory + Strategy |
| `IntegracionPedidosTest` | @SpringBootTest | Facade + Observer + flujo completo |
| `ArquitecturaTest` | ArchUnit | Desacoplamiento entre capas |

---

## Ejecución

```bash
# Compilar y probar
mvn clean test

# Análisis SonarQube
mvn clean verify sonar:sonar -Dsonar.token=TU_TOKEN -Dsonar.scm.disabled=true

# Ejecutar la aplicación
mvn spring-boot:run

# Probar el endpoint REST
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"descripcion":"Pedido test","subtotal":100.0,"tipo":"EXPRESS"}'
```

---

## Commits del repositorio

| # | Mensaje |
|---|---|
| 1 | `feat: Strategy + Factory para procesamiento de pedidos por tipo` |
| 2 | `feat: Observer con Spring Events para notificaciones desacopladas` |
| 3 | `feat: Facade REST y pruebas de integracion + ArchUnit` |

---

## Autor

Cristian Peñaranda · Ingeniería de Sistemas · UDES · 2026
