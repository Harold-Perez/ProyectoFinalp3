package com.proyecto.proyecto.controlles;

import com.proyecto.proyecto.dao.EstudianteDao;
import com.proyecto.proyecto.models.Estudiante;
import com.proyecto.proyecto.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private EstudianteDao estudianteDao;

    @Autowired
    private JWTUtil jwtutil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login (@RequestBody Estudiante estudiante){

        Estudiante loginEstudiante = estudianteDao.obtenerEstudiantePorCredenciales(estudiante);
        if (loginEstudiante != null) {

            String tokenJwt = jwtutil.create(String.valueOf(loginEstudiante.getId()), loginEstudiante.getEmail());
            return tokenJwt;
        }
        return "FAIL";
    }
}
