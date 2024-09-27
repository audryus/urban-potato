package desafio.urban_potato.domain.voto;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VotoService {

	private final VotoRepo repo;
	private final CacheManager cacheManager;

	@Cacheable(value = "voto_sessao_associado", unless = "#result == null")
	public Optional<Voto> getBySessaoAndAssociado(String sessao, String associado) {
		return repo.findById(new VotoId(sessao, associado));
	}

	public Voto create(Voto voto) {
		voto.setTsCriacao(LocalDateTime.now());
		return clearCacheVotosCount(repo.save(voto));
	}

	@Cacheable(value = "votos_count_sessao_escolha")
	public long count(String sessao, Escolha e) {
		return repo.countBySessaoAndEscolha(sessao, e);
	}

	// Decorator
	private Voto clearCacheVotosCount(Voto voto) {
		try {
			var cache = cacheManager.getCache("votos_count_sessao_escolha");
			if (cache == null) {
				return voto;
			}

			cache.clear();
		} catch (Exception e) {
			// Erro pouco importa neste contexto;
		}

		return voto;
	}

}
