package mx.edu.utez.mexprotec.models.action;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.users.Users;

@Entity
@Table(name = "adoption_registration_status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Actions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AdoptionRegistrationType status;

}

