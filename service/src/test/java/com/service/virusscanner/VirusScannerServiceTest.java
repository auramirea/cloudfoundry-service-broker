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
    public void isVirus_returnsTrue_ifFileNameEndsInVirus() {
        assertThat(virusScannerService.isVirus("foo.virus")).isTrue();
    }

    @Test
    public void isVirus_returnsFalse_ifFileNameDoesNotEndInVirus() {
        assertThat(virusScannerService.isVirus("foo")).isFalse();
    }
}