package com.empresa.pedidos;

import com.empresa.pedidos.dominio.puertos.ProcesadorPedido;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.empresa.pedidos")
public class ReglasArquitectura {

    @ArchTest
    static final ArchRule dominioAislado = noClasses()
            .that().resideInAPackage("..dominio..")
            .should().dependOnClassesThat()
            .resideInAnyPackage(
                    "..infraestructura..", "..adaptadores..",
                    "javax.persistence..", "org.springframework.mail..");

    @ArchTest
    static final ArchRule controladorSoloFacade = classes()
            .that().resideInAPackage("..adaptadores.rest..")
            .should().onlyAccessClassesThat()
            .resideInAnyPackage(
                    "..adaptadores.facade..", "..dominio..",
                    "..adaptadores.rest..",
                    "org.springframework.web..", "org.springframework.http..",
                    "java..");

    @ArchTest
    static final ArchRule puertosComoInterfaces = classes()
            .that().resideInAPackage("..dominio.puertos..")
            .should().beInterfaces();

    @ArchTest
    static final ArchRule procesadoresImplementanPuerto = classes()
            .that().resideInAPackage("..adaptadores.procesadores..")
            .should().implement(ProcesadorPedido.class);

    @ArchTest
    static final ArchRule infraNoAccedeRest = noClasses()
            .that().resideInAPackage("..infraestructura..")
            .should().accessClassesThat()
            .resideInAPackage("..adaptadores.rest..");
}