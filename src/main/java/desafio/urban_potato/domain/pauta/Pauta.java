package desafio.urban_potato.domain.pauta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "pauta")
public class Pauta {
	
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "nome")
	private String nome;

}
