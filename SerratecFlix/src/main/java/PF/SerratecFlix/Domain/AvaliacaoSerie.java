package PF.SerratecFlix.Domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "avaliacoes_serie")
public class AvaliacaoSerie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Identificador único da avaliação", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @NotNull
    @DecimalMin(value = "0.0", message = "A nota mínima é 0")
    @DecimalMax(value = "10.0", message = "A nota máxima é 10")
    @Column(nullable = false)
    @Schema(description = "Nota atribuída à série", example = "8.5", minimum = "0.0", maximum = "10.0")
    private Double nota;

    @Column(nullable = false)
    @Schema(description = "Comentário sobre a série", example = "Série incrível, roteiro muito bem elaborado!")
    private String comentario;

    @CreationTimestamp
    @Column(name = "data_avaliacao", nullable = false, updatable = false)
    @Schema(description = "Data e hora em que a avaliação foi realizada", example = "2026-05-27T15:30:00")
    private LocalDateTime dataAvaliacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "Usuário que realizou a avaliação")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_id", nullable = false)
    @Schema(description = "Série que foi avaliada")
    private Serie serie;

}
