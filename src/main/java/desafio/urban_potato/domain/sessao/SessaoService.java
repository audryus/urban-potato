package desafio.urban_potato.domain.sessao;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessaoService {
	
	private final SessaoRepo repo;

	public Sessao create(Sessao sessao) {
		sessao.setId(UUID.randomUUID().toString());
		sessao.setTsCriacao(LocalDateTime.now().withSecond(0));
		sessao.setTsFim(sessao.getTsFim().withSecond(0));
		return repo.save(sessao);
	}
	
	@Cacheable(value = "sessao_pauta", 
			unless = "#result == null")
	public Optional<Sessao> getByPauta(String pauta) {
		return repo.findByPauta(pauta);
	}
	
	@Cacheable(value = "sessao", 
			unless = "#result == null")
	public Optional<Sessao> get(String sessao) {
		return repo.findById(sessao);
	}

	public Slice<Sessao> getAllSemNotificacao(Pageable page) {
		return repo.findAllSemNotificacao(LocalDateTime.now(), page);
	}

	@Transactional
	public void notificada(Sessao sessao) {
		sessao.setNotificado(true);
		repo.save(sessao);
	}
	
}
