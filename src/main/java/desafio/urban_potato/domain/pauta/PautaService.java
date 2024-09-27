package desafio.urban_potato.domain.pauta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PautaService {

	private final PautaRepo repo;
	private final CacheManager cacheManager;

	public Pauta create(Pauta pauta) {
		pauta.setId(UUID.randomUUID().toString());
		pauta.setTsCriacao(LocalDateTime.now());
		return clearCachePautas(repo.save(pauta));
	}

	@Cacheable(value = "pauta")
	public Optional<Pauta> get(String id) {
		if (id == null) {
			return Optional.empty();
		}
		return repo.findById(id);
	}

	@Cacheable(value = "pautas", 
			unless = "#result.isEmpty()")
	public List<Pauta> getAll() {
		return repo.findAllOrdered();
	}

	// Decorator
	private Pauta clearCachePautas(Pauta pauta) {
		try {
			var cache = cacheManager.getCache("pautas");
			if (cache == null) {
				return pauta;
			}

			cache.clear();
		} catch (Exception e) {
			// Erro pouco importa neste contexto;
		}

		return pauta;
	}

}
