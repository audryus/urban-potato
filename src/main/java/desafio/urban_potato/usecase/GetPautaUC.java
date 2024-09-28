package desafio.urban_potato.usecase;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import desafio.urban_potato.controller.res.ResPautaVO;
import desafio.urban_potato.controller.res.ResSessaoVO;
import desafio.urban_potato.controller.res.ResVotoVO;
import desafio.urban_potato.domain.pauta.Pauta;
import desafio.urban_potato.domain.pauta.PautaService;
import desafio.urban_potato.domain.sessao.SessaoService;
import desafio.urban_potato.domain.voto.Escolha;
import desafio.urban_potato.domain.voto.VotoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPautaUC {
	
	private final PautaService pautaService;
	private final SessaoService sessaoService;
	private final VotoService votoService;
	
	public List<ResPautaVO> getAll() {
		return pautaService.getAll()
				.stream()
				.map(this::toDTO)
				.toList();
	}
	
	private ResPautaVO toDTO(Pauta pauta) {
		var sessao = sessaoService.getByPauta(pauta.getId())
			.map(s -> {
				var votos = getVotos(s.getId());
				
				return new ResSessaoVO(
						s.getId(),
						pauta.getId(),
						s.getTsCriacao(),
						s.getTsFim(), 
						votos); 
			}).orElse(null);
		
		return new ResPautaVO(
				pauta.getId(), 
				pauta.getNome(), 
				pauta.getTsCriacao(),
				sessao);
	}
	
	private List<ResVotoVO> getVotos(String sessao) {
		var votos = votoService.getAllBySessaoCached(sessao);
		
		return Stream.of(Escolha.values())
		.map(e -> {
			var count = votos.stream()
			.filter(v -> v.getEscolha().equals(e))
			.count();
			
			return new ResVotoVO(e.toString(), count);
		}).toList();
	}

}
