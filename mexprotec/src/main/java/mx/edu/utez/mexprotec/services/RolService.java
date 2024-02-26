package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.models.rol.Rol;
import mx.edu.utez.mexprotec.models.rol.RolRepository;
import mx.edu.utez.mexprotec.models.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = false)
    public void createRol() {
        List<Rol> roles = this.rolRepository.findAll();
        if (roles.isEmpty()) {
            // Crear roles
            Rol rolAdmin = new Rol();
            rolAdmin.setStatus(true);
            rolAdmin.setRol("ADMINISTRADOR");
            this.rolRepository.save(rolAdmin);

            Rol rolModerador = new Rol();
            rolModerador.setStatus(true);
            rolModerador.setRol("MODERADOR");
            this.rolRepository.save(rolModerador);

            Rol rolCliente = new Rol();
            rolCliente.setStatus(true);
            rolCliente.setRol("CLIENTE");
            this.rolRepository.save(rolCliente);

            // Recuperar los roles guardados
            rolAdmin = this.rolRepository.findByRol("ADMINISTRADOR");
            rolModerador = this.rolRepository.findByRol("MODERADOR");
            rolCliente = this.rolRepository.findByRol("CLIENTE");

            // Crear usuario con los roles recuperados
            Users user = new Users();
            user.setNameUser("MichKwon");
            user.setName("Michelle");
            user.setLastname("Estrada");
            user.setLastnameMatern("Hernández");
            user.setEmail("20213tn011@utez.edu.mx");
            user.setPhoneNumber("7774857215");
            user.setLocalitation("Cuernavaca");
            user.setPassword("Quesadilla123#");
            user.setRol(rolAdmin);
            this.userService.insert(user);
        }
    }

}
