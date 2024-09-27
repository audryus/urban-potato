package desafio.urban_potato.domain.pauta;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pauta")
public class Pauta {
	
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "ts_criacao")
	private LocalDateTime tsCriacao;

}
