package com.service.virusscanner.service;

import com.service.virusscanner.model.Status;
import com.service.virusscanner.model.VirusScanningResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import static com.service.virusscanner.service.VirusScannerService.SWAGGER_ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VirusScannerServiceTest {

    @Mock
    private Environment environment;

    @InjectMocks
    private VirusScannerService virusScannerService = new VirusScannerService();

    @Before
    public void setUp() throws Exception {
        when(environment.getProperty(VirusScannerService.PORT)).thenReturn("8000");
    }

    @Test
    public void isVirus_returnsTrue_ifFileContainsVirus() {
        VirusScanningResponse response = virusScannerService.isVirus("foo.virus");

        assertThat(response.getResult()).isEqualTo(Status.VIRUS_FOUND);
        assertThat(response.getMessages()).isNotEmpty();
        assertThat(response.getApiDoc().getHref()).contains(":8000"+ SWAGGER_ENDPOINT);
        assertThat(response.getUri()).isNull();

    }

    @Test
    public void isVirus_returnsFalse_ifFileNameDoesNotEndInVirus() {
        VirusScanningResponse response = virusScannerService.isVirus("foo");

        assertThat(response.getResult()).isEqualTo(Status.FILE_CLEAN);
        assertThat(response.getUri()).isEqualTo("https://www.google.de");
        assertThat(response.getApiDoc().getHref()).contains(":8000"+ SWAGGER_ENDPOINT);
        assertThat(response.getMessages()).isNull();
    }
}