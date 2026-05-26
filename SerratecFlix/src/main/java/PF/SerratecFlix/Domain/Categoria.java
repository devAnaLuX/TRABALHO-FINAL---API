package PF.SerratecFlix.Domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(nullable = true, length = 250)
    private String descricao;

    @ManyToMany(mappedBy = "categorias")
    private Set<Filme> filmes = new HashSet<>();

    @ManyToMany(mappedBy = "categorias")
    private Set<Serie> series = new HashSet<>();

}