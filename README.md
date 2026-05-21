# penaranda-post2-u12

**Unidad 12 — Validacion Arquitectonica con ArchUnit y ADR**  
Post-Contenido 2 · Patrones de Diseño de Software · Ingeniería de Sistemas · UDES 2026

> Extiende el proyecto del Post-Contenido 1 con reglas ArchUnit ejecutables,
> pipeline de GitHub Actions y documentacion de decisiones en formato ADR.

---

## Estructura del proyecto

```
penaranda-post2-u12/
├── .github/workflows/
│   └── arquitectura.yml          ← pipeline CI con ArchUnit
├── docs/adr/
│   ├── ADR-001.md                ← Arquitectura Hexagonal
│   ├── ADR-002.md                ← Factory + Strategy
│   └── ADR-003.md                ← Spring Events (Observer)
├── src/
│   ├── main/java/com/empresa/pedidos/   ← codigo del Post-Contenido 1
│   └── test/java/com/empresa/pedidos/
│       ├── ReglasArquitectura.java      ← 5 reglas ArchUnit (@ArchTest)
│       ├── ArquitecturaTest.java        ← pruebas ArchUnit del PC1
│       ├── IntegracionPedidosTest.java
│       └── ProcesadorPedidoFactoryTest.java
├── capturas/
│   ├── pipeline-verde.png
│   └── pipeline-rojo.png
└── README.md
```

---

## Validacion Arquitectonica

Las siguientes 5 reglas ArchUnit codifican las restricciones de la arquitectura
hexagonal como pruebas ejecutables. Se ejecutan automaticamente en GitHub Actions
en cada push a `main` o `develop`.

### Regla 1 — dominioAislado
El dominio (`..dominio..`) no debe depender de infraestructura, adaptadores,
`javax.persistence` ni `org.springframework.mail`. Garantiza que el nucleo de
negocio es independiente de frameworks externos.

### Regla 2 — controladorSoloFacade
Los controladores REST (`..adaptadores.rest..`) solo pueden acceder a la Facade
y al dominio. El controlador no debe conocer Factory, procesadores ni repositorios.

### Regla 3 — puertosComoInterfaces
Todas las clases en `..dominio.puertos..` deben ser interfaces. Los puertos son
contratos, no implementaciones concretas.

### Regla 4 — procesadoresImplementanPuerto
Todas las clases en `..adaptadores.procesadores..` deben implementar
`ProcesadorPedido`. Evita adaptadores que no cumplan el contrato del puerto.

### Regla 5 — infraNoAccedeRest
La infraestructura (`..infraestructura..`) no debe acceder a los adaptadores REST.
Evita dependencias inversas entre capas.

### Ejecucion local

```bash
# Solo reglas de arquitectura
mvn test -Dtest=ReglasArquitectura

# Suite completa
mvn verify
```

---

## Decisiones de Diseno (ADR)

| ADR | Decision | Estado |
|---|---|---|
| [ADR-001](docs/adr/ADR-001.md) | Arquitectura Hexagonal para aislar el dominio | Aceptado |
| [ADR-002](docs/adr/ADR-002.md) | Factory + Strategy para seleccion de procesador | Aceptado |
| [ADR-003](docs/adr/ADR-003.md) | Spring Events (Observer) para notificaciones | Aceptado |

---

## Pipeline GitHub Actions

El workflow `.github/workflows/arquitectura.yml` se ejecuta en cada push a `main`
y `develop`. Primero ejecuta las 5 reglas ArchUnit y luego la suite completa.

- **Pipeline verde:** todas las reglas pasan → `capturas/pipeline-verde.png`
- **Pipeline rojo:** violacion intencional detectada → `capturas/pipeline-rojo.png`

---

## Metricas SonarQube

| Metrica | Antes (Legacy) | Despues |
|---|---|---|
| CC servicio principal | 4 | 1 |
| Cognitive Complexity | 6 | 0 |
| Cobertura | — | ≥ 80% |

---

## Ejecucion

```bash
mvn clean test
mvn verify
```

---

## Autor

Cristian Peñaranda · Ingeniería de Sistemas · UDES · 2026
