package mx.edu.utez.mexprotec.models.animals.typePet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TypePet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

}
