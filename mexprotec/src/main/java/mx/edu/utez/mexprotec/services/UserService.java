package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.models.users.Users;
import mx.edu.utez.mexprotec.models.users.UsersRepository;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import mx.edu.utez.mexprotec.utils.Mailer;
import org.apache.catalina.User;
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
        Users usuario = this.usersRepository.findByIdAndActivo(id);
        if (usuario != null) {
            return new CustomResponse<>(
                    usuario, false, 200, "Ok"
            );
        } else {
            return new CustomResponse<>(
                    null, true, 400, "No se encontro el usuario"
            );
        }
    }

    //Crear
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Users> insert(Users usuario) {
        if (this.usersRepository.findByEmailAndActivo(usuario.getEmail()) == null) {
            if (usuario.getRol() != null) {
                String telefono = usuario.getPhoneNumber();
                if (telefono != null && telefono.length() < 12) {
                    String contrasena = usuario.getPassword();
                    if (contrasena != null && contrasena.length() >= 10 && contieneMayuscula(contrasena) && contieneCaracterEspecial(contrasena)) {
                        usuario.setPassword(
                                // Encriptar contraseña
                                this.encoder.encode(usuario.getPassword())
                        );
                        //Configuracion por defecto
                        usuario.setStatus(true);
                        Users usuarioSave =  this.usersRepository.save(usuario);
                        try {
                            //Enviar correo
                            this.mailer.EnviarMensajeBienvenida(usuario.getEmail(), usuario.getName(), "¡Te damos la bienvenida a la Biblioteca!");
                        } catch (Exception e) {
                            return new CustomResponse<>(
                                    null, true, 400, "Ocurrio un error al enviar el correo"
                            );
                        }
                        return new CustomResponse<>(
                                usuarioSave, false, 200, "Usuario registrado correctamente"
                        );
                    } else {
                        return new CustomResponse<>(null, true, 400, "La contraseña no cumple con los requisitos mínimos");
                    }

                } else {
                    return new CustomResponse<>(null, true, 400, "El teléfono no es válido");
                }
            } else {
                return new CustomResponse<>(
                        null, true, 400, "No se encontro el rol"
                );
            }
        } else {
            return new CustomResponse<>(
                    null, true, 400, "El correo ya esta registrado"
            );
        }

    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Users> update(Users usuario) {
        Users us = this.usersRepository.findById(usuario.getId()).orElse(null);
        if (us != null){
            usuario.setPassword(us.getPassword());
            return new CustomResponse<>(
                    this.usersRepository.saveAndFlush(usuario),
                    false,
                    200,
                    "Usuario actualizado correctamente"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No se encontro el usuario"
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> delete(Integer id) {
        if (this.usersRepository.existsById(id)) {
            Users usuario = this.usersRepository.findByIdAndActivo(id);
            usuario.setStatus(false);
            this.usersRepository.saveAndFlush(usuario);
            return new CustomResponse<>(
                    true, false, 200, "Usuario eliminado correctamente"
            );
        } else {
            return new CustomResponse<>(
                    null, true, 400, "No se encontro el usuario"
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> updatePassword(Users usuario) {
        Users temp = this.usersRepository.findByRolAndUserAndActivo(usuario.getId(), usuario.getRol().getIdRol());
        if (this.usersRepository.existsById(usuario.getId()) && usuario.getStatus() && temp != null) {
            String contrasena = usuario.getPassword();
            if (contrasena != null && contrasena.length() >= 10 && contieneMayuscula(contrasena) && contieneCaracterEspecial(contrasena)) {
                usuario.setPassword(
                        encoder.encode(usuario.getPassword())
                );
                this.usersRepository.saveAndFlush(usuario);
                return new CustomResponse<>(
                        true, false, 200, "Usuario actualizado correctamente"
                );
            } else {
                return new CustomResponse<>(null, true, 400, "La contraseña no cumple con los requisitos mínimos");
            }
        } else {
            return new CustomResponse<>(
                    null, true, 400, "No se encontro el usuario"
            );
        }
    }


    @Transactional(readOnly = true)
    public Users findByEmail(String correo) {
        return this.usersRepository.findByEmailAndActivo(correo);
    }

    private boolean contieneMayuscula(String contrasena) {
        return contrasena.matches(".*[A-Z].*");
    }

    private boolean contieneCaracterEspecial(String contrasena) {
        return contrasena.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

}
