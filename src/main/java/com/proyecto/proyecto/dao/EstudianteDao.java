package com.proyecto.proyecto.dao;

import com.proyecto.proyecto.models.Estudiante;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EstudianteDao {

    List<Estudiante> getEstudiantes();

    void eliminar(Long id);

    void registrar(Estudiante estudiante);

    Estudiante obtenerEstudiantePorCredenciales(Estudiante estudiante);

    Estudiante obtenerEstudiantePorId(Long id);

    Estudiante actualizar(Estudiante estudiante);

    void actualizarParcial(Long id, Map<String, Object> datos);



}
