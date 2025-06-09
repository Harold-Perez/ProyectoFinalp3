package com.proyecto.proyecto.dao;

import com.proyecto.proyecto.models.Estudiante;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EstudianteDaoImpTest {

    @Autowired
    private EntityManager entityManager;

    private EstudianteDaoImp estudianteDao;

    @BeforeEach
    void setUp() {
        estudianteDao = new EstudianteDaoImp();
        estudianteDao.entityManager = this.entityManager;

        // Insertar un estudiante de prueba
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Benja");
        estudiante.setApellido("Buch");
        estudiante.setEmail("buch@example.com");
        estudiante.setTelefono("123456789");
        estudiante.setIdioma("espanol");

        entityManager.persist(estudiante);
        entityManager.flush();
    }

    @Test
    void testEmailExistente() {
        assertTrue(estudianteDao.emailExistente("buch@example.com"));
        assertFalse(estudianteDao.emailExistente("noexiste@example.com"));
    }
}
