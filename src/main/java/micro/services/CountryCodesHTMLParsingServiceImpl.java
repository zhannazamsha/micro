package micro.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class CountryCodesHTMLParsingServiceImpl implements CountryCodesHTMLParsingService {

    public static final Map<String, String> COUNTRY_CODE_MAP = new HashMap<>();


    public void collectCountryCodes() throws IOException {
        Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_country_calling_codes").get();
        Element header = doc.getElementById("Alphabetical_listing_by_country_or_region");
        Element table = header.parent().nextElementSibling();
        Elements rows = table.select("tr");

        IntStream.range(1, rows.size()).forEach(i -> {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            System.out.println(cols.get(0).text() + " " + cols.get(1).text());
            COUNTRY_CODE_MAP.put(cols.get(0).text(), cols.get(1).text());
        });
    }
}

