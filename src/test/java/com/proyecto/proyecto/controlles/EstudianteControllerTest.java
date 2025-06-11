package com.proyecto.proyecto.controlles;

import com.proyecto.proyecto.dao.EstudianteDao;
import com.proyecto.proyecto.models.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstudianteControllerTest {

    @InjectMocks
    EstudianteController controller;

    @Mock
    EstudianteDao estudianteDao;

    @BeforeEach
    void setup() { //MOCK FALSO
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEstudiante_Existente() {
        Estudiante e = new Estudiante(1L, "Benja", "benja@mail.com");
        when(estudianteDao.obtenerEstudiantePorId(1L)).thenReturn(e);

        Estudiante resultado = controller.getEstudiante(1L);
        assertEquals("Benja", resultado.getNombre());
        verify(estudianteDao).obtenerEstudiantePorId(1L);
    }

    @Test
    void testGetEstudiante_NoExiste() {
        when(estudianteDao.obtenerEstudiantePorId(1L)).thenReturn(null);

        Estudiante resultado = controller.getEstudiante(1L);
        assertNull(resultado);
    }


    @Test
    void testActualizarEstudiante_NoExiste() {
        when(estudianteDao.obtenerEstudiantePorId(1L)).thenReturn(null);

        Estudiante update = new Estudiante(null, "Benja Actualizado", null);
        ResponseEntity<String> response = controller.actualizarEstudiante(1L, update);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Estudiante no encontrado", response.getBody());
        verify(estudianteDao, never()).actualizar(any());
    }


    @Test
    void testActualizarParcial_NoExiste() {
        when(estudianteDao.obtenerEstudiantePorId(1L)).thenReturn(null);

        ResponseEntity<String> response = controller.actualizarParcialEstudiante(1L, Map.of());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Estudiante no encontrado", response.getBody());
        verify(estudianteDao, never()).actualizarParcial(anyLong(), anyMap());
    }

    @Test
    void testActualizarParcial_ErrorIdioma() {
        Map<String, Object> datos = new HashMap<>();
        datos.put("idioma", "ruso");  // idioma inválido

        Estudiante existente = new Estudiante(1L, "Benja", "benja@mail.com");
        when(estudianteDao.obtenerEstudiantePorId(1L)).thenReturn(existente);

        doThrow(new IllegalArgumentException("Idioma no válido. Debe ser 'español', 'ingles' o 'francés'."))
                .when(estudianteDao).actualizarParcial(1L, datos);

        ResponseEntity<String> response = controller.actualizarParcialEstudiante(1L, datos);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Idioma no válido. Debe ser 'español', 'ingles' o 'francés'.", response.getBody());
    }

}
