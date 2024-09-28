package desafio.urban_potato.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {
	
	private static final String LOG = "[CACHE] Limpando cache {}";
	
	@Scheduled(cron = "${app.cron.votos-count}")
	@CacheEvict(value = {"votos_sessao"}, 
		allEntries = true)
	public void cacheEvict() {
		log.info(LOG, "votos_sessao");
	}
	
}
