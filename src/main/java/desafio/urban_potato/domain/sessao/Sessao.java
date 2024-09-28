package desafio.urban_potato.domain.sessao;

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
@Table(name = "sessao")
public class Sessao {
	
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "pauta")
	private String pauta;
	
	@Column(name = "ts_criacao")
	private LocalDateTime tsCriacao;

	@Column(name = "ts_fim")
	private LocalDateTime tsFim;

	@Column(name = "notificado")
	private boolean notificado;
	
}
