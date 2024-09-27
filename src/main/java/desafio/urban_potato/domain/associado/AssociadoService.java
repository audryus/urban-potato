package desafio.urban_potato.domain.associado;

import java.util.Optional;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AssociadoService {
	
	private final AssociadoRepo repo;
	private final CacheManager cacheManager;
	
	public AssociadoService(
			AssociadoRepo repo, 
			CacheManager cacheManager) {
		this.repo = repo;
		this.cacheManager = cacheManager;
	}
	
	public Associado create(Associado associado) {
		return withCacheAssociado(repo.save(associado));
	}
	
	// Decorator
	private Associado withCacheAssociado(Associado associado) {
		try {
			var cache = cacheManager.getCache("associado");
			if (cache == null) {
				return associado;
			}
			cache.put(associado.getCpf(), associado);
		} catch (Exception e) {
			// Erro pouco importa neste contexto;
		}
		
		return associado;
	}

	@Cacheable(value = "associado")
	public Optional<Associado> get(String associado) {
		return repo.findById(associado);
	}
	
}
