package desafio.urban_potato.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import desafio.urban_potato.controller.res.ResSessaoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JsonUtils {
	
	private final ObjectMapper om;
	
	public JsonUtils() {
		this.om = new ObjectMapper();
		this.om.registerModule(new JavaTimeModule());
	}

	public String toJson(ResSessaoVO resultado) {
		try {
			return om.writeValueAsString(resultado);
		} catch (Exception e) {
			log.error("[JSON] Error", e);
		}
		
		return StringUtils.EMPTY;
	}
	

}
