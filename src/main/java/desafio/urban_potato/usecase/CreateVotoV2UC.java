package desafio.urban_potato.usecase;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateVotoV2UC {
	
	private final CreateVotoUC usecase;
	
	@Async
	public void create(
			String sessaoID, 
			String cpf, 
			String escolha) {
		usecase.create(sessaoID, cpf, escolha);
	}

}
