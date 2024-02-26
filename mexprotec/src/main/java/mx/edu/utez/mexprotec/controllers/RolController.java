package mx.edu.utez.mexprotec.controllers;

import mx.edu.utez.mexprotec.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rol/")
@CrossOrigin(origins = {"*"})
public class RolController {

    @Autowired
    private RolService rolService;

    public void createRolIfNotExist(){
        this.rolService.createRol();
    }
}
