package desafio.urban_potato.utils;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JsonUtils {
	
	private final ObjectMapper om;
	
	public JsonUtils() {
		this.om = new ObjectMapper();
		this.om.registerModule(new JavaTimeModule());
	}

	public String toJson(Object o) {
		try {
			return om.writeValueAsString(o);
		} catch (Exception e) {
			log.error("[JSON] Error", e);
		}
		
		return StringUtils.EMPTY;
	}

	public <T> Optional<T> toObject(String json, Class<T> clazz) {
		try {
			return Optional.ofNullable(om.readValue(json, clazz));
		} catch (Exception e) {
			log.error("[JSON] Error", e);
		}
		return Optional.empty();
	}
	

}
