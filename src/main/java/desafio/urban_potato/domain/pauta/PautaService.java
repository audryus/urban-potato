package desafio.urban_potato.domain.pauta;

import java.util.UUID;

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
	
}
