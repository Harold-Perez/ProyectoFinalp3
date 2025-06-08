package com.proyecto.proyecto.service;

import com.proyecto.proyecto.dao.EstudianteDao;
import com.proyecto.proyecto.models.Estudiante;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteDao estudianteDao;

    public EstudianteServiceImpl(EstudianteDao estudianteDao) {
        this.estudianteDao = estudianteDao;
    }

    @Override
    public List<Estudiante> obtenerTodos() {
        return estudianteDao.getEstudiantes();
    }

    @Override
    public Estudiante obtenerPorId(Long id) {
        Estudiante estudiante = estudianteDao.obtenerEstudiantePorId(id);
        if (estudiante == null) {
            throw new RuntimeException("Estudiante no encontrado");
        }
        return estudiante;
    }

    @Override
    public Estudiante crear(Estudiante estudiante) {
        estudianteDao.registrar(estudiante);
        return estudiante;
    }

    @Override
    public Estudiante actualizar(Long id, Estudiante estudiante) {
        estudiante.setId(id);
        return estudianteDao.actualizar(estudiante);
    }

    @Override
    public Estudiante actualizarParcial(Long id, Estudiante estudiante) {
        Map<String, Object> datos = new HashMap<>();

        if (estudiante.getNombre() != null) {
            datos.put("nombre", estudiante.getNombre());
        }

        if (estudiante.getEmail() != null) {
            datos.put("email", estudiante.getEmail());
        }

        estudianteDao.actualizarParcial(id, datos);

        return estudianteDao.obtenerEstudiantePorId(id); // devuelve el estudiante actualizado
    }

    @Override
    public void eliminar(Long id) {
        estudianteDao.eliminar(id);
    }
}
