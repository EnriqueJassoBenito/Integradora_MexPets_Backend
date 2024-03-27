package mx.edu.utez.mexprotec.models.animals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.personality.Personality;
import mx.edu.utez.mexprotec.models.animals.race.Race;
import mx.edu.utez.mexprotec.models.animals.typePet.TypePet;
import mx.edu.utez.mexprotec.models.users.Users;

import java.util.List;

@Entity
@Table(name="animals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Animals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_pet", nullable = false)
    private String namePet;

    @ManyToOne
    @JoinColumn(name = "type_pet", referencedColumnName = "id")
    private TypePet typePet;

    @Column(name = "location", nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "race", referencedColumnName = "id")
    private Race race;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "personality", referencedColumnName = "id")
    private Personality personality;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "sterilized")
    private Boolean sterilized;

    @Column(name = "image")
    private String image;

    @Column(columnDefinition = "boolean default true")
    //@JsonIgnore
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "id_register")
    private Users register;

}
