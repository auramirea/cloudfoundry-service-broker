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

package com.service.virusscanner;

import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

import static com.service.virusscanner.Status.FILE_CLEAN;
import static com.service.virusscanner.Status.VIRUS_FOUND;

@Service
public class VirusScannerService {
	private static final String VIRUS_SUFFIX = ".virus";

	public VirusScanningResponse isVirus(final String filename) {
		VirusScanningResponse.VirusScanningResponseBuilder responseBuilder = VirusScanningResponse.builder();
		if(filename.endsWith(VIRUS_SUFFIX)) {
			return responseBuilder.result(VIRUS_FOUND).messages(ImmutableList.of("M1", "M2")).build();
		}
		return responseBuilder.result(FILE_CLEAN).uri("https://www.google.de").build();
	}
}
