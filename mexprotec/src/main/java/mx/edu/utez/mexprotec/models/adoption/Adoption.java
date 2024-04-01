package mx.edu.utez.mexprotec.models.adoption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.image.adoption.AdoptionImage;
import mx.edu.utez.mexprotec.models.users.Users;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="adoption")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_animals")
    @JsonIgnore
    private Animals animal;

    @ManyToOne
    @JoinColumn(name = "id_users")
    private Users cliente;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "boolean default false")
    //@JsonIgnore
    private Boolean status;

    @Column(name = "creation_date", columnDefinition = "DATE")
    private LocalDate creationDate = LocalDate.now();

    @OneToMany(mappedBy = "adoption", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<AdoptionImage> images;
}
