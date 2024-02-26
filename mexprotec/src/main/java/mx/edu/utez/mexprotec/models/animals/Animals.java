package mx.edu.utez.mexprotec.models.animals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.users.Users;

@Entity
@Table(name="animals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Animals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_animal")
    private Long idAnimal;

    @Column(name = "type_pet", nullable = false)
    private String typePet;

    @Column(name = "name_pet", nullable = false)
    private String namePet;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "race", nullable = false)
    private String race;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "personality", nullable = false)
    private String personality;

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

    @Column(name = "sociable")
    private Boolean sociable;

    @Column(name = "sterilized")
    private Boolean sterilized;

    @Column(name = "image")
    private String image;

    @Column(nullable = false, columnDefinition = "boolean default true")
    @JsonIgnore
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "id_registrador")
    private Users registrador;

}
