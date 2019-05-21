package micro.services;

import micro.api.exceptions.CountryNotFound;
import micro.api.exceptions.WrongPhoneNumberFormat;
import micro.domain.CountryCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CountryCodesDetectionServiceTest {

    @Autowired
    private CountryCodeDetectionServiceImpl countryCodesDetectionService;

    @TestConfiguration
    static class CountryCodesHTMLParsingServiceTestContextConfiguration {

        @Bean
        public CountryCodeDetectionServiceImpl countryCodeDetectionService() {
            return new CountryCodeDetectionServiceImpl();
        }
    }

    @Before
    public void setUp() {
        CountryCodesHTMLParsingServiceImpl.CODE_MATRIX[4][0] = new ArrayList<CountryCode>();
        CountryCodesHTMLParsingServiceImpl.CODE_MATRIX[4][7] = new ArrayList<CountryCode>();
        CountryCodesHTMLParsingServiceImpl.CODE_MATRIX[4][4] = new ArrayList<CountryCode>() {{
            add(new CountryCode().builder().code("+44").countryName("UK").build());
        }};
        CountryCodesHTMLParsingServiceImpl.CODE_MATRIX[4][6] = new ArrayList<CountryCode>() {{
            add(new CountryCode().builder().code("+46").countryName("Sweden").build());
        }};
    }

    @Test
    public void detectCountry_searchForExistingCountry_CountryNameIsReturned() {
        String country = countryCodesDetectionService.detectCountry("+46 568940");
        assertThat(country)
                .isEqualTo("Sweden");

    }

    @Test(expected = CountryNotFound.class)
    public void detectCountry_searchForNotExistingCountry_NotFoundExceptionIsReturned() {
        String country = countryCodesDetectionService.detectCountry("+47 568940");
    }

    @Test(expected = WrongPhoneNumberFormat.class)
    public void detectCountry_wrongPhoneFormat_WrongPhoneNumberFormatExceptionIsReturned() {
        String country = countryCodesDetectionService.detectCountry("47 568940");
    }

}
