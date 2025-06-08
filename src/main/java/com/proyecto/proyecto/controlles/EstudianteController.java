package com.proyecto.proyecto.controlles;

import com.proyecto.proyecto.dao.EstudianteDao;
import com.proyecto.proyecto.models.Estudiante;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class EstudianteController {

    @Autowired
    private EstudianteDao estudianteDao;

    // Obtener estudiante por ID
    @RequestMapping(value = "api/estudiantes/{id}", method = RequestMethod.GET)
    public Estudiante getEstudiante(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {

        return estudianteDao.obtenerEstudiantePorId(id);
    }

    // Actualizar completamente un estudiante
    @RequestMapping(value = "api/estudiantes/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> actualizarEstudiante(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable Long id,
            @RequestBody Estudiante estudiante) {



        Estudiante existente = estudianteDao.obtenerEstudiantePorId(id);
        if (existente == null) {
            return new ResponseEntity<>("Estudiante no encontrado", HttpStatus.NOT_FOUND);
        }

        // Solo actualiza campos no nulos
        if (estudiante.getNombre() != null) existente.setNombre(estudiante.getNombre());
        if (estudiante.getApellido() != null) existente.setApellido(estudiante.getApellido());
        if (estudiante.getEmail() != null) existente.setEmail(estudiante.getEmail());
        if (estudiante.getTelefono() != null) existente.setTelefono(estudiante.getTelefono());
        if (estudiante.getIdioma() != null) existente.setIdioma(estudiante.getIdioma());

        estudianteDao.actualizar(existente);
        return new ResponseEntity<>("Estudiante actualizado correctamente", HttpStatus.OK);
    }

    // Actualización parcial con PATCH
    @PatchMapping("api/estudiantes/{id}")
    public ResponseEntity<String> actualizarParcialEstudiante(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable Long id,
            @RequestBody Map<String, Object> datos) {



        Estudiante existente = estudianteDao.obtenerEstudiantePorId(id);
        if (existente == null) {
            return new ResponseEntity<>("Estudiante no encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            estudianteDao.actualizarParcial(id, datos);
            return new ResponseEntity<>("Estudiante actualizado parcialmente", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Obtener todos los estudiantes
    @RequestMapping(value = "api/estudiantes", method = RequestMethod.GET)
    public List<Estudiante> getEstudiantes(@RequestHeader(value = "Authorization") String token) {
       
        return estudianteDao.getEstudiantes();
    }

    // Registrar nuevo estudiante
    @RequestMapping(value = "api/estudiantes", method = RequestMethod.POST)
    public ResponseEntity<String> registrarEstudiante(@RequestBody @Valid Estudiante estudiante) {


        estudianteDao.registrar(estudiante);
        return new ResponseEntity<>("Estudiante registrado exitosamente", HttpStatus.CREATED);
    }

    // Eliminar estudiante
    @RequestMapping(value = "api/estudiantes/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {

        estudianteDao.eliminar(id);
    }



}
