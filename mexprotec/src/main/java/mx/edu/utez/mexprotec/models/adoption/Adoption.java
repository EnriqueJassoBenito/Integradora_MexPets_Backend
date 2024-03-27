package mx.edu.utez.mexprotec.models.adoption;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.users.Users;

@Entity
@Table(name="adoption")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_animals")
    private Animals animal;

    @ManyToOne
    @JoinColumn(name = "id_users")
    private Users cliente;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    /*@Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "photo_url")
    private String photoUrl;*/

    @Column(columnDefinition = "boolean default true")
    //@JsonIgnore
    private Boolean status;

}
