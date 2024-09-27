package desafio.urban_potato.domain.voto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "voto")
@IdClass(VotoId.class)
public class Voto {
	
	@Id
	@Column(name= "sessao")
	private String sessao; 
	
	@Id
	@Column(name= "associado")
	private String associado;

	@Column(name= "ts_criacao")
	private LocalDateTime tsCriacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name= "voto")
	private Escolha escolha;

}
