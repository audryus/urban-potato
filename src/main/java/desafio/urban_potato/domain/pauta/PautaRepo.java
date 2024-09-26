package desafio.urban_potato.domain.pauta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepo 
	extends JpaRepository<Pauta, String> {
}
