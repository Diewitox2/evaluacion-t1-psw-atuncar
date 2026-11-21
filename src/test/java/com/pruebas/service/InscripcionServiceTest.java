package com.pruebas.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pruebas.model.Inscripcion;
import com.pruebas.model.services.InscripcionService;
import com.pruebas.repository.InscripcionRepository;

/**
 * Pregunta 1 - Pruebas unitarias con JUnit 5 y Mockito.
 * Alumno: JOSE ATUNCAR - Código: I202508267
 */
@ExtendWith(MockitoExtension.class)
class InscripcionServiceTest {

    private static final String ALUMNO = "JOSE ATUNCAR";
    private static final String CODIGO_ALUMNO = "I202508267";

    @Mock
    private InscripcionRepository repository;

    @InjectMocks
    private InscripcionService service;

    @BeforeEach
    void identificarAlumno() {
        System.out.println("Alumno: " + ALUMNO + " | Código: " + CODIGO_ALUMNO);
    }

    // ---------------- Caso 1 ----------------
    @Test
    @DisplayName("Caso 1: inscribir a un estudiante correctamente")
    void inscribirEstudianteCorrectamente() {
        Inscripcion entrada = new Inscripcion();
        entrada.setCodigoEstudiante("I2024077");
        entrada.setCursoId(3L);

        Inscripcion guardada = new Inscripcion();
        guardada.setId(10L);
        guardada.setCodigoEstudiante("I2024077");
        guardada.setCursoId(3L);

        when(repository.existsByCodigoEstudianteAndCursoId("I2024077", 3L)).thenReturn(false);
        when(repository.save(any(Inscripcion.class))).thenReturn(guardada);

        Inscripcion resultado = service.inscribir(entrada);

        assertNotNull(resultado);                                   // resultado no nulo
        assertEquals(10L, resultado.getId());                       // inscripción registrada
        assertEquals("I2024077", resultado.getCodigoEstudiante());
        assertEquals(3L, resultado.getCursoId());
        verify(repository, times(1)).save(entrada);                 // se invoca save()
    }

    // ---------------- Caso 2 ----------------
    @Test
    @DisplayName("Caso 2: no permite inscribir a un estudiante ya inscrito")
    void inscribirEstudianteYaInscrito() {
        Inscripcion entrada = new Inscripcion();
        entrada.setCodigoEstudiante("I2024001");
        entrada.setCursoId(1L);

        when(repository.existsByCodigoEstudianteAndCursoId("I2024001", 1L)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.inscribir(entrada));

        assertEquals("Estudiante ya inscrito", ex.getMessage());
        verify(repository, never()).save(any(Inscripcion.class));   // nunca se invoca save()
    }

    // ---------------- Caso 3 ----------------
    @Test
    @DisplayName("Caso 3: cancelar una inscripción existente")
    void cancelarInscripcionExistente() {
        Inscripcion existente = new Inscripcion();
        existente.setId(1L);
        existente.setCodigoEstudiante("I2024001");
        existente.setCursoId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));

        assertDoesNotThrow(() -> service.cancelar(1L));             // no lanza excepción
        verify(repository, times(1)).delete(existente);             // delete una sola vez
    }
}
