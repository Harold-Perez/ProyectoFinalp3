package com.proyecto.proyecto.service;

import com.proyecto.proyecto.models.Estudiante;

import java.util.List;

public interface EstudianteService {
    List<Estudiante> obtenerTodos();
    Estudiante obtenerPorId(Long id);
    Estudiante crear(Estudiante estudiante);
    Estudiante actualizar(Long id, Estudiante estudiante);
    Estudiante actualizarParcial(Long id, Estudiante estudiante);
    void eliminar(Long id);
}


