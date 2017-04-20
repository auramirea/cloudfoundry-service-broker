package org.springframework.cloud.servicebroker.virusscanner.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.config.VirusScannerConfig;
import org.springframework.cloud.servicebroker.model.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Mongo impl to bind services.  Binding a service does the following:
 * creates a new user in the database (currently uses a default pwd of "password"),
 * saves the ServiceInstanceBinding info to the Mongo repository.
 *  
 * @author sgreenberg@pivotal.io
 */
@Service
public class VirusScannerInstanceBindingService implements ServiceInstanceBindingService {

	private VirusScannerConfig virusScannerConfig;

	@Autowired
	public VirusScannerInstanceBindingService(VirusScannerConfig virusScannerConfig) {
		this.virusScannerConfig = virusScannerConfig;
	}
	
	@Override
	public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) {
		String bindingId = request.getBindingId();
		String serviceInstanceId = request.getServiceInstanceId();

		String database = serviceInstanceId;
		String username = bindingId;
		String password = RandomStringUtils.randomAlphanumeric(25);

		Map<String, Object> credentials = new HashMap<>();
		credentials.put("host", virusScannerConfig.getHost());
		credentials.put("port", virusScannerConfig.getPort());
		credentials.put("username", virusScannerConfig.getUsername());
		credentials.put("password", virusScannerConfig.getPassword());

		ServiceInstanceBinding binding =
				new ServiceInstanceBinding(bindingId, serviceInstanceId, credentials, null, request.getBoundAppGuid());

		return new CreateServiceInstanceAppBindingResponse().withCredentials(credentials);
	}

	@Override
	public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
	}
}
