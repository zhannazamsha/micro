package micro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import micro.api.exceptions.CountryNotFound;
import micro.services.CountryCodeDetectionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PhoneNumberParseController.class)
public class PhoneNumberParseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private CountryCodeDetectionService countryCodeDetectionService;

    @Test
    public void submitPhoneNumber_countryIsFound_ReturnsHttpStatusOk() throws Exception {
        String number = "+3711234567";
        String country = "Latvia";
        given(countryCodeDetectionService.detectCountry(number)).willReturn(country);
        mockMvc.perform(
                post("/phone").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(country)))
                .andExpect(status().isOk());
    }

    @Test
    public void submitPhoneNumber_countryIsNotFound_ReturnsHttpStatusOk() throws Exception {
        String number = "+9876543";
        given(countryCodeDetectionService.detectCountry(number)).willThrow(new CountryNotFound("Country not found"));
        mockMvc.perform(
                post("/phone").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
