package mx.edu.utez.mexprotec.models.rol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.users.Users;

import java.util.Set;

@Entity
@Table(name="rol")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;

    @Column(name = "nrol", nullable = false)
    private String nrol;

    @Column(nullable = false, columnDefinition = "boolean default true")
    //@JsonIgnore
    private Boolean status;

    @OneToMany(mappedBy = "rol")
    @JsonIgnore
    private Set<Users> usuario;
}
