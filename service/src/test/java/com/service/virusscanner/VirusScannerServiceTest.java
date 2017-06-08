package com.service.virusscanner;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class VirusScannerServiceTest {

    private VirusScannerService virusScannerService;

    @Before
    public void setUp() throws Exception {
        virusScannerService = new VirusScannerService();
    }

    @Test
    public void isVirus_returnsTrue_ifFileContainsVirus() {
        VirusScanningResponse response = virusScannerService.isVirus("foo.virus");
        assertThat(response.getResult()).isEqualTo(Status.VIRUS_FOUND);
        assertThat(response.getMessages()).isNotEmpty();
        assertThat(response.getUri()).isNull();
    }

    @Test
    public void isVirus_returnsFalse_ifFileNameDoesNotEndInVirus() {
        VirusScanningResponse response = virusScannerService.isVirus("foo");
        assertThat(response.getResult()).isEqualTo(Status.FILE_CLEAN);
        assertThat(response.getMessages()).isNull();
        assertThat(response.getUri()).isEqualTo("https://www.google.de");
    }
}