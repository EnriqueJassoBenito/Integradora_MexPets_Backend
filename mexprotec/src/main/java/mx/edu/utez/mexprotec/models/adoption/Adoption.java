package mx.edu.utez.mexprotec.models.adoption;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "id_adoption")
    private Long idAdoption;

    @ManyToOne
    @JoinColumn(name = "id_animal")
    private Animals animal;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Users cliente;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(nullable = false, columnDefinition = "boolean default true")
    @JsonIgnore
    private Boolean status;

}
