package mx.edu.utez.mexprotec.models.animals.race;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.Animals;

import java.util.List;

@Entity
@Table(name= "race")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "race_pet", nullable = false)
    private String racePet;

    @OneToMany(mappedBy = "race")
    @JsonIgnore
    private List<Animals> animals;

}
