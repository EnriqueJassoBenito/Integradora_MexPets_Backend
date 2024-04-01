package mx.edu.utez.mexprotec.models.animals.personality;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.Animals;

import java.util.List;

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

    @OneToMany(mappedBy = "personality")
    @JsonIgnore
    private List<Animals> animals;

}
