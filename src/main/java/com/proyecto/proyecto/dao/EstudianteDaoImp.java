package com.proyecto.proyecto.dao;

import com.proyecto.proyecto.models.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class EstudianteDaoImp implements EstudianteDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Estudiante> getEstudiantes() {
        String query = "FROM Estudiante";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Estudiante estudiante = entityManager.find(Estudiante.class, id);
        entityManager.remove(estudiante);
    }

    @Override
    public void registrar(Estudiante estudiante) {
        entityManager.merge(estudiante);
    }

    @Override
    public Estudiante obtenerEstudiantePorCredenciales(Estudiante estudiante){
        String query = "FROM Estudiante WHERE email = :email";
        List<Estudiante> lista = entityManager.createQuery(query, Estudiante.class)
                .setParameter("email", estudiante.getEmail())
                .getResultList();

        if (lista.isEmpty()) {
            return null;
        }

        return null;
    }

    @Override
    public Estudiante obtenerEstudiantePorId(Long id) {
        return entityManager.find(Estudiante.class, id);
    }

    @Override
    public Estudiante actualizar(Estudiante estudiante) {
        entityManager.merge(estudiante); // actualiza todo el objeto
        return estudiante;
    }

    @Override
    public void actualizarParcial(Long id, Map<String, Object> datos) {
        Estudiante existente = entityManager.find(Estudiante.class, id);
        if (existente == null) {
            throw new IllegalArgumentException("Estudiante no encontrado con ID: " + id);
        }

        if (datos.containsKey("nombre")) {
            existente.setNombre((String) datos.get("nombre"));
        }

        if (datos.containsKey("apellido")) {
            existente.setApellido((String) datos.get("apellido"));
        }

        if (datos.containsKey("email")) {
            existente.setEmail((String) datos.get("email"));
        }

        if (datos.containsKey("telefono")) {
            existente.setTelefono((String) datos.get("telefono"));
        }

        if (datos.containsKey("idioma")) {
            String idioma = ((String) datos.get("idioma")).toLowerCase();

            // Normalizar: quitar tilde si viene con ella
            idioma = idioma.replace("í", "i").replace("é", "e").replace("á", "a");

            if (idioma.equals("espanol") || idioma.equals("ingles") || idioma.equals("frances")) {
                existente.setIdioma(idioma);
            } else {
                throw new IllegalArgumentException("Idioma no válido. Debe ser 'español', 'ingles' o 'francés'.");
            }
        }

        entityManager.merge(existente);
    }

}  // <--- Esta llave cierra la clase y puede ser la que te falta



