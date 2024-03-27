package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.models.rol.Rol;
import mx.edu.utez.mexprotec.models.rol.RolRepository;
import mx.edu.utez.mexprotec.models.users.Users;
import mx.edu.utez.mexprotec.models.users.UsersRepository;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import mx.edu.utez.mexprotec.utils.Mailer;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    @Lazy
    private PasswordEncoder encoder;

    @Autowired
    private Mailer mailer;

    @Transactional(readOnly = true)
    public CustomResponse<List<Users>> getAll() {
        return new CustomResponse<>(
                this.usersRepository.findAllByStatus(true),
                false,
                200,
                "Ok"
        );
    }

    ///Buscar por id
    @Transactional(readOnly = true)
    public CustomResponse<Users> getOne(Integer id) {
        Users user = this.usersRepository.findByIdAndActivo(id);
        if (user != null) {
            return new CustomResponse<>(
                    user, false, 200, "Ok"
            );
        } else {
            return new CustomResponse<>(
                    null, true, 400, "No se encontro el user"
            );
        }
    }

    //Crear
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Users> insert(Users user) {
        if (this.usersRepository.findByEmailAndActivo(user.getEmail()) == null) {
            if (user.getRol() != null) {
                // Check if the role is already persisted in the database
                if (user.getRol().getIdRol() == null) {
                    // If the role is not persisted, save it first
                    // Ensure that the status is set to true or the default value
                    user.getRol().setStatus(true);
                    Rol persistedRol = rolRepository.save(user.getRol());
                    // Set the persisted role in the user entity
                    user.setRol(persistedRol);
                }

                String phoneNumber = user.getPhoneNumber();
                if (phoneNumber != null && phoneNumber.length() < 12) {
                    String password = user.getPassword();
                    if (password != null && password.length() >= 10 && contieneMayuscula(password) && contieneCaracterEspecial(password)) {
                        user.setPassword(
                                // Encrypt password
                                this.encoder.encode(user.getPassword())
                        );
                        // Set default configuration
                        user.setStatus(true);
                        Users userSave = this.usersRepository.save(user);
                        try {
                            // Send email
                            this.mailer.EnviarMensajeBienvenida(user.getEmail(), user.getName(), "¡Te damos la bienvenida a la Biblioteca!");
                        } catch (Exception e) {
                            return new CustomResponse<>(
                                    null, true, 400, "Ocurrió un error al enviar el correo"
                            );
                        }
                        return new CustomResponse<>(
                                userSave, false, 200, "user registrado correctamente"
                        );
                    } else {
                        return new CustomResponse<>(null, true, 400, "La contraseña no cumple con los requisitos mínimos");
                    }
                } else {
                    return new CustomResponse<>(null, true, 400, "El teléfono no es válido");
                }
            } else {
                return new CustomResponse<>(
                        null, true, 400, "No se encontró el rol"
                );
            }
        } else {
            return new CustomResponse<>(
                    null, true, 400, "El correo ya está registrado"
            );
        }
    }

    ///Actualizar
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Users> update(Users users){
        if(!this.usersRepository.existsById(users.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No encontrado"
            );
        return new CustomResponse<>(
                this.usersRepository.saveAndFlush(users),
                false,
                200,
                "Actualizado correctamente"
        );
    }

    /*@Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Users> update(Users user) {
        Users us = this.usersRepository.findById(user.getId()).orElse(null);
        if (us != null){
            user.setPassword(us.getPassword());
            return new CustomResponse<>(
                    this.usersRepository.saveAndFlush(user),
                    false,
                    200,
                    "user actualizado correctamente"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No se encontro el user"
            );
        }
    }*/


    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> delete(Integer id) {
        if (this.usersRepository.existsById(id)) {
            Users user = this.usersRepository.findByIdAndActivo(id);
            user.setStatus(false);
            this.usersRepository.saveAndFlush(user);
            return new CustomResponse<>(
                    true, false, 200, "user eliminado correctamente"
            );
        } else {
            return new CustomResponse<>(
                    null, true, 400, "No se encontro el user"
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> updatePassword(Users user) {
        Users temp = this.usersRepository.findByRolAndUserAndActivo(user.getId(), user.getRol().getIdRol());
        if (this.usersRepository.existsById(user.getId()) && user.getStatus() && temp != null) {
            String password = user.getPassword();
            if (password != null && password.length() >= 10 && contieneMayuscula(password) && contieneCaracterEspecial(password)) {
                user.setPassword(
                        encoder.encode(user.getPassword())
                );
                this.usersRepository.saveAndFlush(user);
                return new CustomResponse<>(
                        true, false, 200, "user actualizado correctamente"
                );
            } else {
                return new CustomResponse<>(null, true, 400, "La contraseña no cumple con los requisitos mínimos");
            }
        } else {
            return new CustomResponse<>(
                    null, true, 400, "No se encontro el user"
            );
        }
    }


    @Transactional(readOnly = true)
    public Users findByEmail(String correo) {
        return this.usersRepository.findByEmailAndActivo(correo);
    }

    private boolean contieneMayuscula(String password) {
        return password.matches(".*[A-Z].*");
    }

    private boolean contieneCaracterEspecial(String password) {
        return password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

}
