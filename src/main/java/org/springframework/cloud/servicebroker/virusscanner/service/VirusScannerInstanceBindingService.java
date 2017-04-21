package org.springframework.cloud.servicebroker.virusscanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.config.VirusScannerConfig;
import org.springframework.cloud.servicebroker.model.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

		Map<String, Object> credentials = new HashMap<>();
		credentials.put("servicePath", virusScannerConfig.getServicePath());
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
