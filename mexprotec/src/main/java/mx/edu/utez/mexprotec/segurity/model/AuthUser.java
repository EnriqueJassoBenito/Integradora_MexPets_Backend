package mx.edu.utez.mexprotec.segurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.mexprotec.models.users.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AuthUser implements UserDetails {
    private String correo;
    @JsonIgnore
    private String contrasena;

    private Users usuario;
    public Collection<? extends GrantedAuthority> authorities;

    public AuthUser(String correo, String contrasena, Users usuario, Collection<? extends GrantedAuthority> authorities) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.usuario = usuario;
        this.authorities = authorities;
    }

    public static AuthUser build(Users usuario){
        GrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRol().getRol());
        return new AuthUser(usuario.getEmail(), usuario.getPassword(), usuario, List.of(authority));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Users getUsuario(){
        return usuario;
    }

    public void setUsuario(Users usuario){
        this.usuario = usuario;
    }

}
