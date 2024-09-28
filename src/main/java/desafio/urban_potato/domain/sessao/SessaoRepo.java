package desafio.urban_potato.domain.sessao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoRepo 
	extends JpaRepository<Sessao, String> {
	
	Optional<Sessao> findByPauta(String pauta);
	List<Sessao> findAllByPauta(String pauta);
	
	@Query("""
			SELECT s 
			FROM Sessao s
			WHERE s.tsFim < :now
			AND (s.notificado IS NULL 
			    OR s.notificado = false)
			""")
	Slice<Sessao> findAllSemNotificacao(
			LocalDateTime now, Pageable page);
	
}
