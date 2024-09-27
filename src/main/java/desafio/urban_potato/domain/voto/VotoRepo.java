package desafio.urban_potato.domain.voto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepo 
	extends JpaRepository<Voto, VotoId> {
	
	List<Voto> findAllBySessao(String sessao);
	
	long countBySessaoAndEscolha(
			String sessao, 
			Escolha escolha);
}
