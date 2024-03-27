package mx.edu.utez.mexprotec.models.action;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.users.Users;

@Entity
@Table(name= "actions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Actions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users user;

    @Column(name = "register_ado")
    private Boolean registerAdo;

    @Column(name = "adoption")
    private Boolean adoption;

}
