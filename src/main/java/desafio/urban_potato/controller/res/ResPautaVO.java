package desafio.urban_potato.controller.res;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ResPautaVO (
		String id,
		String pauta,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
		LocalDateTime tsCriacao,
		ResSessaoVO sessao
		){
	
}
