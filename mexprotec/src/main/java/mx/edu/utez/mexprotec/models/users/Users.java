package mx.edu.utez.mexprotec.models.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.rol.Rol;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name_user", nullable = false)
    private String nameUser;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "lastname_matern")
    private String lastnameMatern;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "localitation", nullable = false)
    private String localitation;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_rol")
    private Rol rol;

    @Column(nullable = false, columnDefinition = "boolean default true")
    //@JsonIgnore
    private Boolean status;
}
