package desafio.urban_potato.domain.voto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VotoService {

	private final VotoRepo repo;

	@Cacheable(value = "voto_sessao_associado", 
			unless = "#result == null")
	public Optional<Voto> getBySessaoAndAssociado(String sessao, String associado) {
		return repo.findById(new VotoId(sessao, associado));
	}

	public Voto create(Voto voto) {
		voto.setTsCriacao(LocalDateTime.now());
		return repo.save(voto);
	}

	@Cacheable(value = "votos_sessao")
	public List<Voto> getAllBySessaoCached(String sessao) {
		return getAllBySessao(sessao);
	}

	public List<Voto> getAllBySessao(String sessao) {
		return repo.findAllBySessao(sessao);
	}

}
