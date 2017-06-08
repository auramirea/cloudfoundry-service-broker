/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.service.virusscanner.service;

import com.google.common.collect.ImmutableList;
import com.service.virusscanner.model.VirusScanningResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.service.virusscanner.model.Status.FILE_CLEAN;
import static com.service.virusscanner.model.Status.VIRUS_FOUND;

@Service
public class VirusScannerService {

    @Autowired
    private Environment environment;

	private static final String VIRUS_SUFFIX = ".virus";
    public static final String PORT = "local.server.port";
    public static final String SWAGGER_ENDPOINT = "/swagger-ui.html";

	public VirusScanningResponse isVirus(final String filename) {
		VirusScanningResponse.VirusScanningResponseBuilder responseBuilder = VirusScanningResponse.builder();
		if(filename.endsWith(VIRUS_SUFFIX)) {
			return virusPresentResponse(responseBuilder).build();
		}
		return fileCleanResponse(responseBuilder).build();
	}

	private VirusScanningResponse.VirusScanningResponseBuilder fileCleanResponse(VirusScanningResponse.VirusScanningResponseBuilder responseBuilder) {
		return responseBuilder.result(FILE_CLEAN).uri("https://www.google.de")
				.apiDoc(buildSwaggerUrl());
	}

    private VirusScanningResponse.VirusScanningResponseBuilder virusPresentResponse(VirusScanningResponse.VirusScanningResponseBuilder responseBuilder) {
        return responseBuilder.result(VIRUS_FOUND).messages(ImmutableList.of("M1", "M2"))
        .apiDoc(buildSwaggerUrl());
    }

	private Link buildSwaggerUrl() {
		try {
			return new Link("http://"
                    .concat(InetAddress.getLocalHost().getCanonicalHostName())
                    .concat(":")
                    .concat(environment.getProperty(PORT))
                    .concat(SWAGGER_ENDPOINT));
		} catch (UnknownHostException e) {
			return new Link("localhost:8000/".concat(SWAGGER_ENDPOINT));
		}
	}
}
