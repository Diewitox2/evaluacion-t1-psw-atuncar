package com.pruebas.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

/**
 * Pregunta 2 - Pruebas de integración con Rest Assured.
 * Requiere la aplicación levantada en http://localhost:8080
 * Alumno: JOSE ATUNCAR - Código: I202508267
 */
class CursoApiTest {

    @BeforeAll
    static void configurar() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        System.out.println("Alumno: JOSE ATUNCAR | Código: I202508267");
    }

    // ---------------- Escenario 1 ----------------
    @Test
    @DisplayName("Escenario 1: consultar todos los cursos")
    void consultarTodosLosCursos() {
        given()
        .when()
            .get("/api/cursos")
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(3));
    }

    // ---------------- Escenario 2 ----------------
    // OJO: el DataLoader del proyecto base carga el curso 1 como "Pruebas de Software"
    // y el curso 2 como "Seguridad de Aplicaciones".
    @Test
    @DisplayName("Escenario 2: consultar el curso con id 1")
    void consultarCursoPorId1() {
        given()
        .when()
            .get("/api/cursos/{id}", 1)
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("nombre", equalTo("Pruebas de Software"))
            .body("vacantes", greaterThan(0));
    }

    @Test
    @DisplayName("Escenario 2 (según enunciado): curso \"Seguridad de Aplicaciones\" con vacantes > 0")
    void consultarCursoSeguridadDeAplicaciones() {
        given()
        .when()
            .get("/api/cursos/{id}", 2)
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("nombre", equalTo("Seguridad de Aplicaciones"))
            .body("vacantes", greaterThan(0));
    }
}
