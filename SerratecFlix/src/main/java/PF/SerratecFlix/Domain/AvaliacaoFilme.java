package PF.SerratecFlix.Domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliacoes_filme")
public class AvaliacaoFilme {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @DecimalMin(value = "0.0", message = "A nota mínima é 0")
    @DecimalMax(value = "10.0", message = "A nota máxima é 10")
    @Column(nullable = false)
    private Double nota;

    @Column(nullable = false)
    private String comentario;

    @CreationTimestamp
    @Column(name = "data_avaliacao", nullable = false, updatable = false)
    private LocalDateTime dataAvaliacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;


}