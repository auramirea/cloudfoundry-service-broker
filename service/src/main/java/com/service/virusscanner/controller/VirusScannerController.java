/*
 * Copyright 2012-2017 the original author or authors.
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

package com.service.virusscanner.controller;

import com.service.virusscanner.model.VirusScanningResponse;
import com.service.virusscanner.service.VirusScannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Api(description = "Determines if a file contains virus or not")
public class VirusScannerController {

    private final VirusScannerService virusScannerService;

    @Autowired
    public VirusScannerController(final VirusScannerService virusScannerService) {
        this.virusScannerService = virusScannerService;
    }


    @PostMapping("/scan")
    @ResponseBody
    public VirusScanningResponse scan(@RequestParam("file") MultipartFile file) {
        return virusScannerService.isVirus(file.getOriginalFilename());
    }
}
