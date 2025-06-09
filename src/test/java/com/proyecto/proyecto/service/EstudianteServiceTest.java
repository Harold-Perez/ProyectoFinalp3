package com.proyecto.proyecto.service;

import com.proyecto.proyecto.dao.EstudianteDao;
import com.proyecto.proyecto.models.Estudiante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    EstudianteDao estudianteDao;

    @InjectMocks
    EstudianteServiceImpl estudianteService;

    @Test
    void testObtenerTodos() {
        when(estudianteDao.getEstudiantes()).thenReturn(Arrays.asList(
                new Estudiante(1L, "Benja", "benja@mail.com")
        ));

        var resultado = estudianteService.obtenerTodos();
        assertEquals(1, resultado.size());
        verify(estudianteDao).getEstudiantes();
    }

    @Test
    void testObtenerPorId_Existente() {
        Estudiante estudiante = new Estudiante(1L, "Benja", "benja@mail.com");
        when(estudianteDao.obtenerEstudiantePorId(1L)).thenReturn(estudiante);

        Estudiante resultado = estudianteService.obtenerPorId(1L);
        assertEquals("Benja", resultado.getNombre());
    }

    @Test
    void testObtenerPorId_NoExiste() {
        when(estudianteDao.obtenerEstudiantePorId(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> estudianteService.obtenerPorId(1L));
    }

    @Test
    void testCrear() {
        Estudiante nuevo = new Estudiante(null, "Ana", "ana@mail.com");

        // registrar() no retorna nada, así que solo verificamos que se llame
        estudianteService.crear(nuevo);

        verify(estudianteDao).registrar(nuevo);
    }

    @Test
    void testActualizar() {
        Estudiante actual = new Estudiante(3L, "Carlos", "carlos@mail.com");
        when(estudianteDao.actualizar(actual)).thenReturn(actual);

        Estudiante resultado = estudianteService.actualizar(3L, actual);
        assertEquals("Carlos", resultado.getNombre());
    }

    @Test
    void testActualizarParcial() {
        Estudiante parcial = new Estudiante();
        parcial.setNombre("Luis Eduardo");

        Estudiante actualizado = new Estudiante(4L, "Luis Eduardo", "luis@mail.com");

        // Simular llamada a actualizarParcial (void)
        doNothing().when(estudianteDao).actualizarParcial(eq(4L), anyMap());

        // Simular que luego devuelve el estudiante actualizado
        when(estudianteDao.obtenerEstudiantePorId(4L)).thenReturn(actualizado);

        Estudiante resultado = estudianteService.actualizarParcial(4L, parcial);
        assertEquals("Luis Eduardo", resultado.getNombre());
        assertEquals("luis@mail.com", resultado.getEmail());

        verify(estudianteDao).actualizarParcial(eq(4L), anyMap());
        verify(estudianteDao).obtenerEstudiantePorId(4L);
    }

    @Test
    void testEliminar() {
        estudianteService.eliminar(5L);
        verify(estudianteDao).eliminar(5L);
    }
}
