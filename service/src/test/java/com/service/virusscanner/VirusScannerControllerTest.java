package com.service.virusscanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VirusScannerControllerTest {

    @Mock
    private VirusScannerService virusScannerService;
    private VirusScannerController virusScannerController;
    private static final MockMultipartFile FILE = new MockMultipartFile("foo", "foo", "text/plain", "foo".getBytes());

    @Before
    public void setUp() throws Exception {
        virusScannerController = new VirusScannerController(virusScannerService);
    }

    @Test
    public void scan_respondsWithFalse_whenFileIsNotVirus() throws Exception {
        when(virusScannerService.isVirus(anyString())).thenReturn(false);

        assertThat(virusScannerController.scan(FILE)).isFalse();
    }

    @Test
    public void scan_respondsWithTrue_whenFileIsVirus() throws Exception {
        when(virusScannerService.isVirus(anyString())).thenReturn(true);

        assertThat(virusScannerController.scan(FILE)).isTrue();
    }

}