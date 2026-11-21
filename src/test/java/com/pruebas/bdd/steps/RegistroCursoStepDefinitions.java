package com.pruebas.bdd.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * Pregunta 3 - Step definitions del feature RegistroCurso.feature
 * Requiere la aplicación levantada en http://localhost:8080
 * Alumno: JOSE ATUNCAR - Código: I202508267
 */
public class RegistroCursoStepDefinitions {

    private final Map<String, Object> curso = new HashMap<>();
    private Response response;

    @Dado("que el administrador ingresa un curso con nombre {string}, precio {string} y {int} vacantes")
    public void ingresaCurso(String nombre, String precio, Integer vacantes) {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        curso.put("nombre", nombre);
        curso.put("precio", Double.parseDouble(precio));
        curso.put("vacantes", vacantes);
    }

    @Cuando("envía la solicitud de registro del curso")
    public void enviaSolicitud() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(curso)
                .when()
                .post("/api/cursos");
    }

    @Entonces("el sistema responde con código {int}")
    public void validaCodigo(Integer codigo) {
        assertEquals(codigo.intValue(), response.statusCode());
    }

    @Y("el curso devuelto tiene el nombre {string}")
    public void validaNombre(String nombre) {
        assertEquals(nombre, response.jsonPath().getString("nombre"));
    }

    @Entonces("el sistema rechaza la solicitud con un código de error 4xx o 5xx")
    public void validaRechazo() {
        int code = response.statusCode();
        assertTrue(code >= 400 && code <= 599, "Se esperaba 4xx o 5xx pero fue " + code);
    }
}
