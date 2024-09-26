package desafio.urban_potato.domain.pauta;

import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PautaService {
	
	private final PautaRepo repo;

	public Pauta create(Pauta pauta) {
		pauta.setId(UUID.randomUUID().toString());
		return repo.save(pauta);
	}
	
	@Cacheable(value = "pauta")
	public Optional<Pauta> get(String id) {
		if (id == null) {
			return Optional.empty();
		}
		return repo.findById(id);
	}
	
}
