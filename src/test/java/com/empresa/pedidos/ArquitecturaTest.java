package com.empresa.pedidos;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Pruebas de arquitectura con ArchUnit.
 * Verifica que las capas respetan las reglas de dependencia
 * definidas por la arquitectura hexagonal.
 */
class ArquitecturaTest {

    private final JavaClasses clases = new ClassFileImporter()
            .importPackages("com.empresa.pedidos");

    @Test
    @DisplayName("El dominio no debe depender de infraestructura")
    void dominio_noDebe_dependerDeInfraestructura() {
        ArchRule regla = noClasses()
                .that().resideInAPackage("..dominio..")
                .should().dependOnClassesThat()
                .resideInAPackage("..infraestructura..");
        regla.check(clases);
    }

    @Test
    @DisplayName("El dominio no debe depender de adaptadores")
    void dominio_noDebe_dependerDeAdaptadores() {
        ArchRule regla = noClasses()
                .that().resideInAPackage("..dominio..")
                .should().dependOnClassesThat()
                .resideInAPackage("..adaptadores..");
        regla.check(clases);
    }

    @Test
    @DisplayName("El controlador REST no debe depender directamente de infraestructura")
    void controladorRest_noDebe_dependerDeInfraestructura() {
        ArchRule regla = noClasses()
                .that().resideInAPackage("..adaptadores.rest..")
                .should().dependOnClassesThat()
                .resideInAPackage("..infraestructura..");
        regla.check(clases);
    }

    @Test
    @DisplayName("El controlador REST no debe depender de aplicacion directamente")
    void controladorRest_noDebe_dependerDeAplicacion() {
        ArchRule regla = noClasses()
                .that().resideInAPackage("..adaptadores.rest..")
                .should().dependOnClassesThat()
                .resideInAPackage("..aplicacion..");
        regla.check(clases);
    }
}
