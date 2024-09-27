package desafio.urban_potato.controller.res;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ResSessaoVO (
		String id,
		String pauta,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
		LocalDateTime tsCriacao,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
		LocalDateTime tsFim,
		List<ResVotoVO> votos
		){
	
}
