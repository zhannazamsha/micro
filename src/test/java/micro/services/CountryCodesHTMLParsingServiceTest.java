package micro.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CountryCodesHTMLParsingServiceTest {


    public static final String HTML_TABLE_MOCK = "<h2><span class=\"mw-headline\" id=\"Alphabetical_listing_by_country_or_region\">Alphabetical listing by country or region</span></h2>\n" +
            "<table class=\"wikitable sortable\">\n" +
            "<tbody><tr>\n" +
            "<th>Country, Territory or Service\n" +
            "</th>\n" +
            "<th>Code\n" +
            "</th>\n" +
            "<th>Time Zone\n" +
            "</th>\n" +
            "<th><a href=\"/wiki/Daylight_saving_time\" title=\"Daylight saving time\">DST</a>\n" +
            "</th></tr>\n" +
            "<tr>\n" +
            "<td>Afghanistan\n" +
            "</td>\n" +
            "<td align=\"right\"><span data-sort-value=\"7004930000000000000♠\" style=\"display:none\"></span><a href=\"/wiki/%2B93\" class=\"mw-redirect\" title=\"+93\">+93</a>\n" +
            "</td>\n" +
            "<td>UTC+04:30\n" +
            "</td>\n" +
            "<td>\n" +
            "</td></tr>\n" +
            "<tr>\n" +
            "<td>Åland Islands\n" +
            "</td>\n" +
            "<td align=\"right\"><span data-sort-value=\"7004358180000000000♠\" style=\"display:none\"></span><a href=\"/wiki/%2B358_18\" class=\"mw-redirect\" title=\"+358 18\">+358 18</a>\n" +
            "</td>\n" +
            "<td>UTC+02:00\n" +
            "</td>\n" +
            "<td>UTC+03:00\n" +
            "</td></tr></table>";

    @Autowired
    private CountryCodesHTMLParsingServiceImpl countryCodesHTMLParsingService;

    @TestConfiguration
    static class CountryCodesHTMLParsingServiceTestContextConfiguration {

        @Bean
        public CountryCodesHTMLParsingServiceImpl countryCodesHTMLParsingService() {
            return new CountryCodesHTMLParsingServiceImpl();
        }
    }



    @Test
    public void prseHTML_Tree() {
        Document doc = Jsoup.parse(HTML_TABLE_MOCK);
        countryCodesHTMLParsingService.collectCountryCodes(doc, "Alphabetical_listing_by_country_or_region");
        assertThat(CountryCodesHTMLParsingServiceImpl.CODE_MATRIX[9][3].size())
                .isEqualTo(1);
        assertThat(CountryCodesHTMLParsingServiceImpl.CODE_MATRIX[9][3].get(0).getCode())
                .isEqualTo("+93");

    }

}
