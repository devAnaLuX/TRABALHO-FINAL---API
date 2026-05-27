package PF.SerratecFlix.Domain;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Identificador único da avaliação", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @NotNull
    @DecimalMin(value = "0.0", message = "A nota mínima é 0")
    @DecimalMax(value = "10.0", message = "A nota máxima é 10")
    @Column(nullable = false)
    @Schema(description = "Nota atribuída ao filme", example = "9.0", minimum = "0.0", maximum = "10.0")
    private Double nota;

    @Column(nullable = false)
    @Schema(description = "Comentário sobre o filme", example = "Filme incrível, efeitos visuais impressionantes!")
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
    @JoinColumn(name = "filme_id", nullable = false)
    @Schema(description = "Filme que foi avaliado")
    private Filme filme;


}