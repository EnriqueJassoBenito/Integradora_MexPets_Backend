package mx.edu.utez.mexprotec.models.animals.personality;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "personality")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Personality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "personality_pet", nullable = false)
    private String personalityPet;
}
