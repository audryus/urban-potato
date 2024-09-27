package desafio.urban_potato.domain.associado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepo 
	extends JpaRepository<Associado, String> {
}
