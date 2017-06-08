package com.service.virusscanner;

import com.google.common.collect.ImmutableList;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.service.virusscanner.Status.FILE_CLEAN;
import static com.service.virusscanner.Status.VIRUS_FOUND;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(secure = false)
public class VirusScannerControllerTest {

    @Autowired
    private VirusScannerController virusScannerController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VirusScannerService virusScannerService;

    private static final MockMultipartFile FILE = new MockMultipartFile("file", "foo".getBytes());

    @Before
    public void setUp() throws Exception {
        virusScannerController = new VirusScannerController(virusScannerService);
    }

    @Test
    public void scan_respondsWithFalse_whenFileIsNotVirus() throws Exception {
        VirusScanningResponse virusNotPresent = VirusScanningResponse.builder().result(FILE_CLEAN)
                .uri("https://www.google.de").build();
        when(virusScannerService.isVirus(anyString())).thenReturn(virusNotPresent);

        mockMvc.perform(fileUpload("/scan").file(FILE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value("FILE_CLEAN"))
                .andExpect(jsonPath("messages").doesNotExist())
        .andExpect(jsonPath("uri").value("https://www.google.de"));
    }

    @Test
    public void scan_respondsWithMessages_whenFileIsVirus() throws Exception {
        VirusScanningResponse virusPresent = VirusScanningResponse.builder().result(VIRUS_FOUND)
                .messages(ImmutableList.of("M1", "M2")).build();
        when(virusScannerService.isVirus(anyString())).thenReturn(virusPresent);

        mockMvc.perform(fileUpload("/scan").file(FILE))
                .andExpect(status().isOk())
        .andExpect(jsonPath("result").value("VIRUS_FOUND"))
        .andExpect(jsonPath("messages").value(Matchers.containsInAnyOrder("M1", "M2")))
        .andExpect(jsonPath("url").doesNotExist());
    }

}