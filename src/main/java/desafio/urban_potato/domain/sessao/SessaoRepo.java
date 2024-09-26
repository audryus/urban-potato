package desafio.urban_potato.domain.sessao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoRepo 
	extends JpaRepository<Sessao, String> {
	
	Optional<Sessao> findByPauta(String pauta);
	List<Sessao> findAllByPauta(String pauta);
	
}
