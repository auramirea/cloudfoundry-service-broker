package org.springframework.cloud.servicebroker.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogConfig {
	
	@Bean
	public Catalog catalog() {
		return new Catalog(Collections.singletonList(
				new ServiceDefinition(
						"virusscanner-service-broker",
						"virusscanner",
						"A Virus Scanner that detects viruses in files",
						true,
						false,
						Collections.singletonList(
								new Plan("virusscanner-plan",
										"free",
										"A virus scanner for your test env. ATTENTION: It will tell you that files ending in .virus contain viruses, all other files do not.",
										getPlanMetadata(),
										true)),
						Arrays.asList("virusscanner"),
						getServiceDefinitionMetadata(),
						null,
						null)));
	}
	
	private Map<String, Object> getServiceDefinitionMetadata() {
		Map<String, Object> sdMetadata = new HashMap<>();
		sdMetadata.put("displayName", "VirusScanner");
		sdMetadata.put("imageUrl", "http://www.tricksforums.org/wp-content/uploads/2013/11/Online-virus-scanner.jpg");
		sdMetadata.put("longDescription", "VirusScanner Service");
		sdMetadata.put("providerDisplayName", "TBD");
		sdMetadata.put("documentationUrl", "https://example.com");
		sdMetadata.put("supportUrl", "https://example.com");
		return sdMetadata;
	}
	
	private Map<String,Object> getPlanMetadata() {
		Map<String,Object> planMetadata = new HashMap<>();
		planMetadata.put("bullets", getBullets());
		return planMetadata;
	}

	private List<String> getBullets() {
		return Arrays.asList("Test VirusScanner",
				"1MB file limit (not enforced)",
				"It will tell you that files ending in .virus contain viruses, all other files do not");
	}
	
}