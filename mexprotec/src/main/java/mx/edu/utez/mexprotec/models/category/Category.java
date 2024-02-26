package mx.edu.utez.mexprotec.models.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "personality", nullable = false)
    private String personality;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "race", nullable = false)
    private String race;

    /*@Column(name = "weight", nullable = false)
    private String weight;*/

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "specie", nullable = false)
    private String specie;

    /*@Column(name = "age", nullable = false)
    private int age;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "sociability", nullable = false)
    private String sociability;*/

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean status;

}
