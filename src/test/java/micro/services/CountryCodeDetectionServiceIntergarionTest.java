package micro.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CountryCodeDetectionServiceIntergarionTest {

    public static final String COUNTRY_CODES_LINK = "https://en.wikipedia.org/wiki/List_of_country_calling_codes";
    public static final String COUNTRY_CODES_HTML_TABLE_HEADER = "Alphabetical_listing_by_country_or_region";


    @Autowired
    private CountryCodeDetectionServiceImpl countryCodesDetectionService;

    @Autowired
    private CountryCodesHTMLParsingServiceImpl countryCodesHTMLParsingService;

    @TestConfiguration
    static class CountryCodeDetectionServiceIntergarionTestContextConfiguration {

        @Bean
        public CountryCodesHTMLParsingServiceImpl countryCodesHTMLParsingService() {
            return new CountryCodesHTMLParsingServiceImpl();
        }

        @Bean
        public CountryCodeDetectionServiceImpl countryCodeDetectionService() {
            return new CountryCodeDetectionServiceImpl();
        }
    }


    @Test
    public void detectCountry_detectListOfValidCountryCodes() throws IOException {
        countryCodesHTMLParsingService.collectCountryCodes(COUNTRY_CODES_LINK, COUNTRY_CODES_HTML_TABLE_HEADER);
        String sweden = countryCodesDetectionService.detectCountry("+46 568940");
        assertThat(sweden)
                .isEqualTo("Sweden");
        String jordan = countryCodesDetectionService.detectCountry("+962 6 5200 180");
        assertThat(jordan)
                .isEqualTo("Jordan");
        String kazakhstan = countryCodesDetectionService.detectCountry("+7 7172 595 888");
        assertThat(kazakhstan)
                .isEqualTo("Kazakhstan");
        String serbia = countryCodesDetectionService.detectCountry("+381 38 502 456");
        assertThat(serbia)
                .isEqualTo("Serbia");
        String czechRepublic = countryCodesDetectionService.detectCountry("+420 227 231 231");
        assertThat(czechRepublic)
                .isEqualTo("Czech Republic");
        String turkey = countryCodesDetectionService.detectCountry("+90 2123548867");
        assertThat(turkey)
                .isEqualTo("Turkey");
        String us = countryCodesDetectionService.detectCountry("+1 800 843 0002");
        assertThat(us)
                .isIn("United States", "Canada");
    }
}
