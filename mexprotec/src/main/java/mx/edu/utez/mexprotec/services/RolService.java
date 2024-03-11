package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.models.rol.Rol;
import mx.edu.utez.mexprotec.models.rol.RolRepository;
import mx.edu.utez.mexprotec.models.users.Users;
import mx.edu.utez.mexprotec.utils.CustomResponse;
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
    public void createRoles() {
        try {
            List<Rol> roles = this.rolRepository.findAll();
            if (roles.isEmpty()) {

                // Crear roles
                Rol rolAdmin = new Rol();
                rolAdmin.setStatus(true);
                rolAdmin.setNrol("ADMIN");
                this.rolRepository.save(rolAdmin);

                ///Crea rol MODERADOR
                Rol rolModerador = new Rol();
                rolModerador.setStatus(true);
                rolModerador.setNrol("MODE");
                this.rolRepository.save(rolModerador);

                ///Crea rol CLIENTE
                Rol rolCliente = new Rol();
                rolCliente.setStatus(true);
                rolCliente.setNrol("CLIENTE");
                this.rolRepository.save(rolCliente);

                // Recuperar los roles guardados
                rolAdmin = this.rolRepository.findByNrol("ADMIN");
                rolModerador = this.rolRepository.findByNrol("MODE");
                rolCliente = this.rolRepository.findByNrol("CLIENTE");

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Rol>> getAllRoles() {
        try {
            List<Rol> roles = this.rolRepository.findAll();
            return new CustomResponse<>(
                    roles,
                    false,
                    200,
                    "OK"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error al obtener roles"
            );
        }
    }
}
