package mx.edu.utez.mexprotec.models.processed;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.adoption.Adoption;
import mx.edu.utez.mexprotec.models.users.Users;

@Entity
@Table(name="adoption_processed")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Processed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_adoption")
    private Adoption adoption;

    @ManyToOne
    @JoinColumn(name = "id_users")
    private Users moderador;

    @Column(columnDefinition = "boolean default true")
    private Boolean status;

}
