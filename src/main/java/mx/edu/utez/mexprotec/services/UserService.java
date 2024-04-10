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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    @Transactional(readOnly = true)
    public CustomResponse<Users> getOne(UUID id) {
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

    @Transactional(readOnly = true)
    public CustomResponse<List<Users>> getByRole(String roleName) {
        List<Users> users = this.usersRepository.findByRol_Nrol(roleName);
        if (!users.isEmpty()) {
            return new CustomResponse<>(users, false, 200, "Usuarios encontrados por rol");
        } else {
            return new CustomResponse<>(null, true, 404, "No se encontraron usuarios con ese rol");
        }
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Users>> getUsersByAdminAndModeratorRoles() {
        List<String> roles = Arrays.asList("ADMIN", "MODERADOR");
        List<Users> users = this.usersRepository.findAllByRol_NrolIn(roles);
        if (!users.isEmpty()) {
            return new CustomResponse<>(users, false, 200, "Usuarios encontrados por roles ADMIN y MODERADOR");
        } else {
            return new CustomResponse<>(null, true, 404, "No se encontraron usuarios con los roles especificados");
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Users> insert(Users user) {
        if (this.usersRepository.findByEmailAndActivo(user.getEmail()) == null) {
            if (user.getRol() != null) {
                if (user.getRol().getIdRol() == null) {
                    user.getRol().setStatus(true);
                    Rol persistedRol = rolRepository.save(user.getRol());
                    user.setRol(persistedRol);
                }
                String phoneNumber = user.getPhoneNumber();
                if (phoneNumber != null && phoneNumber.length() < 12) {
                    String password = user.getPassword();
                    if (password != null && password.length() >= 10 && containsUppercase(password) && containsSpecialCharacter(password)) {
                        user.setPassword(
                                this.encoder.encode(user.getPassword())
                        );
                        user.setStatus(true);
                        Users userSave = this.usersRepository.save(user);
                        try {
                            this.mailer.sendEmailWelcome(user.getEmail(), user.getName(), "¡Te damos la bienvenida MexPet!");
                        } catch (Exception e) {
                            return new CustomResponse<>(
                                    null, true, 400, "Ocurrió un error al enviar el correo"
                            );
                        }
                        return new CustomResponse<>(
                                userSave, false, 200, "Usuario registrado correctamente"
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

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> delete(UUID id) {
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
            if (password != null && password.length() >= 10 && containsUppercase(password) && containsSpecialCharacter(password)) {
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

    private boolean containsUppercase(String password) {
        return password.matches(".*[A-Z].*");
    }

    private boolean containsSpecialCharacter(String password) {
        return password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

}
