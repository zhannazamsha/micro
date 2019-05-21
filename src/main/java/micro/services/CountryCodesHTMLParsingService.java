package micro.services;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface CountryCodesHTMLParsingService {

    void collectCountryCodes(String url, String countryCodesStartText) throws IOException;

    void collectCountryCodes(Document document, String countryCodesStartText);

}
