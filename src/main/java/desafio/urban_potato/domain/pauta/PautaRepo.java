package desafio.urban_potato.domain.pauta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepo 
	extends JpaRepository<Pauta, String> {
	
	@Query("""
			SELECT p 
			FROM Pauta p
			ORDER BY p.tsCriacao desc
			""")
	List<Pauta> findAllOrdered();
}
