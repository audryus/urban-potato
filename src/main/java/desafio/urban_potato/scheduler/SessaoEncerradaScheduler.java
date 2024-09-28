package desafio.urban_potato.scheduler;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import desafio.urban_potato.amqp.PautaResultadoAmqp;
import desafio.urban_potato.controller.res.ResSessaoVO;
import desafio.urban_potato.controller.res.ResVotoVO;
import desafio.urban_potato.domain.sessao.Sessao;
import desafio.urban_potato.domain.sessao.SessaoService;
import desafio.urban_potato.domain.voto.Escolha;
import desafio.urban_potato.domain.voto.VotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessaoEncerradaScheduler {
	
	private final SessaoService sessaoService;
	private final PautaResultadoAmqp pautaResultado;
	private final VotoService votoService;
	
	@Value("${query.limit.encerrar-sessao}")
	int queryLimit;

	@Scheduled(cron = "${app.cron.encerrar-sessao}")
	public void run() {
		log.info("[SCHEDULER Encerrar Sessao] Inicio ...");
		var page = PageRequest.of(0, queryLimit);
		Slice<Sessao> slice = null;
		List<Sessao> content = null;
		
		while((slice = sessaoService.getAllSemNotificacao(page)).hasContent()) {
			content = slice.getContent();
			log.info("[SCHEDULER Encerrar Sessao] Processando {} Sessoes.", 
					content.size());
			
			content.forEach(sessao -> {
				try {
					pautaResultado.send(getResultado(sessao));
					log.info("[SCHEDULER Encerrar Sessao] {} enviado com sucesso.", 
							sessao);
					sessaoService.notificada(sessao);
				} catch (Exception e) {
					log.error("[SCHEDULER Encerrar Sessao] Erro ao enviar {}", 
							sessao, e);
				}
			});
			
			page = page.next();
		}
		
		log.info("[SCHEDULER Encerrar Sessao] Fim ...");
	}
	
	private ResSessaoVO getResultado(Sessao s) {
		var votos = getVotos(s.getId());
		return new ResSessaoVO(
				s.getId(),
				s.getPauta(),
				s.getTsCriacao(),
				s.getTsFim(), 
				votos);
	}
	
	private List<ResVotoVO> getVotos(String sessao) {
		var votos = votoService.getAllBySessao(sessao);
		
		return Stream.of(Escolha.values())
		.map(e -> {
			var count = votos.stream()
			.filter(v -> v.getEscolha().equals(e))
			.count();
			
			return new ResVotoVO(e.toString(), count);
		}).toList();
	}
	
}
